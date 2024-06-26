# Django Rest Framework 实现JWT认证

# 认识JWT

JWT（Json Web Token）是一种为了在网络应用环境传递声明而执行的、基于JSON的开放标准。由头部（Header）、负载（Payload）、签名（Signature）3部分构成，其中每一部分都使用Base64编码处理
  
`Header`：头部信息。主要包含两部分信息：①类型，通常为“JWT”，②算法名称，比如HSHA256、RSA等
  
`Payload`：具体用户的信息。需要注意的是，该部分内容只经过Base64编码，所以不要在其中放置敏感信息
  
`Signature`：签名信息。签名用于验证消息在传递过程中是否被更改

JWT信息由3段构成，之间用圆点连接： aaaa.bbbb.cccc

## 使用JWT--基于djangorestframework-jwt库

（1）安装

```
pip install djangorestframework-jwt
```
（2）配置应用

settings.py文件编辑如下：

```python
INSTALLED_APPS = [
    ...
    'rest_framework_jwt',
]

REST_FRAMEWORK = {
    ...
    # 配置验证方式为JWT验证
    'DEFAULT_AUTHENTICATION_CLASSES': ('rest_framework_jwt.authentication.JSONWebTokenAuthentication',),                          ),
}

JWT_AUTH = {
    'JWT_EXPIRATION_DELTA': datetime.timedelta(days=3),  # 过期时间为3天
    'JWT_AUTH_HEADER_PREFIX': 'JWT',  # Token 的头为 JWT xxxxxxxxxxxx
    'JWT_ALLOW_REFRESH': False,  # 不允许刷新
}
```

（3）配置路由

```python
from django.urls import path, re_path, include
from rest_framework_jwt.views import obtain_jwt_token

urlpatterns = [
    ...
    path('api-jwt-token-auth/', obtain_jwt_token),
]
```

## 自定义返回认证信息

（1）新建文件'common/jwt_utils.py'添加如下代码

```python
def jwt_response_payload_handler(token, user=None, request=None):
    return {
        'token': token,
        'id': user.id,
        'username': user.username,
        'email': user.email,
        'is_active': user.is_active
    }
```

（2）settings.py文件编辑如下：

```python
JWT_AUTH = {
    'JWT_EXPIRATION_DELTA': datetime.timedelta(days=3),  # 过期时间为3天
    'JWT_AUTH_HEADER_PREFIX': 'JWT',  # Token 的头为 JWT xxxxxxxxxxxx
    'JWT_ALLOW_REFRESH': False,  # 不允许刷新
    # 自定义返回认证信息
    'JWT_RESPONSE_PAYLOAD_HANDLER': 'common.jwt_utils.jwt_response_payload_handler'
}
```

## 权限认证

DRF框架内置的权限组件配置信息有以下四种

`rest_framework.permissions.IsAuthenticated`：通过认证的用户才可以访问接口

`rest_framework.permissions.IsAdminUser`：仅管理员可以访问

`rest_framework.permissions.IsAuthenticatedOrReadOnly`：未认证的用户只有查询权限，经过认证的用户有增加、删除、修改、查询权限
  
`rest_framework.permissions.AllowAny`：默认对所有的接口都有操作权限，即不做权限限制

（1）全局配置

settings.py文件编辑如下：
```python
REST_FRAMEWORK = {
    ...
    'EXCEPTION_HANDLER': 'apps.goods.customexception.custom_exception_handler',
    # 配置验证方式为JWT验证
    'DEFAULT_AUTHENTICATION_CLASSES': ('rest_framework_jwt.authentication.JSONWebTokenAuthentication',),
    # 'DEFAULT_PERMISSION_CLASSES': ('rest_framework.permissions.IsAuthenticated',),  # 全局配置 通过认证的用户才可以访问接口
    # 'DEFAULT_PERMISSION_CLASSES': ('rest_framework.permissions.IsAdminUser',),  # 全局配置 仅管理员可以访问
    # 'DEFAULT_PERMISSION_CLASSES': ('rest_framework.permissions.IsAuthenticatedOrReadOnly',),  # 全局配置 未认证的用户只有查询权限，经过认证的用户有增加、删除、修改、查询权限
    # 'DEFAULT_PERMISSION_CLASSES': ('rest_framework.permissions.AllowAny',),  # 全局配置 默认对所有的接口都有操作权限，即不做权限限制
    ...
}
```

（2）局部配置

视图类中进行配置
```python
from rest_framework import viewsets
from apps.goods.models import Goods
from apps.goods.serializers import GoodsSerializer
from rest_framework import permissions
from rest_framework.authentication import TokenAuthentication


class GoodsView(viewsets.ModelViewSet):
    queryset = Goods.objects.all()
    serializer_class = GoodsSerializer
    permission_classes = (permissions.IsAuthenticated, )  # 局部配置
```


## 自定义权限

DRF框架支持自定义权限，只需要继承rest_framework.permissions.BasePermission并实现以下两个方法

`has_permission(self, request, view)`方法：是否可以访问视图。view表示当前视图对象

`has_object_permission(self, request, view, obj)`方法：是否可以访问数据对象。view表示当前视图对象，obj为数据对象。该方法在发起PUT请求进行修改数据时触发

（1）新建文件‘common/permission.py’，添加如下代码
```python
from rest_framework.permissions import BasePermission
from rest_framework_jwt.authentication import jwt_decode_handler


class IsOwnerOrReadOnly(BasePermission):

    def has_permission(self, request, view):
        if request.user.username == 'admin':
            return True

    def has_object_permission(self, request, view, obj):
        token = request.META['HTTP_AUTHORIZATION'][5:]
        token_user = jwt_decode_handler(token)  # 解析Token
        if token_user:
            return obj.user.id == token_user['user_id']  # 如果相同则返回True
```

（2）视图类中进行配置

```python
from rest_framework import viewsets
from apps.goods.models import Goods
from apps.goods.serializers import GoodsSerializer
from rest_framework import permissions
from rest_framework.authentication import TokenAuthentication
from common.permissions import IsOwnerOrReadOnly


class GoodsView(viewsets.ModelViewSet):
    queryset = Goods.objects.all()
    serializer_class = GoodsSerializer
    permission_classes = (permissions.IsAuthenticated, IsOwnerOrReadOnly)  # 局部配置
```

## 使用JWT
（1）postman发起请求

（2）headers入参Authorization: JWT 401f7ac837da42b97f613d789819ff93537bee6a。。。

（3）然后发起请求

## Token认证和JWT认证的区别

Token认证依靠数据库进行存储和查询，因此在大用户、高并发情况下性能较差

JWT验证客户端发来的Token，不进行数据库查询，直接在服务端使用密钥进行验证

JWT最大的缺点：服务器只负责签发JWT，不负责保存，一但JWT签发，则在有效期内一直有效，无法中途废弃，因此JWT是一次性的，要修改其中信息，必须重新签发一个新的JWT
