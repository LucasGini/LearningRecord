# Django Rest Framework 实现Token认证

DRF框架提供一下几种认证方案

`BasicAuthentication`:基本的用户密码认证

`SessionAuthentication`:Session会话认证，

`TokenAuthentcation`：使用Token令牌的http身份认证

## 基于DRF框架实现Token认证
（1）配置应用

编辑settings.py文件
```python
INSTALLED_APPS = [
    ...
    'rest_framework.authtoken',
]
REST_FRAMEWORK = {
    ...
    'DEFAULT_AUTHENTICATION_CLASSES': ('rest_framework.authentication.TokenAuthentication', ),
    ...
}
```
执行数据库迁移：python manage.py migrate

（2）配置路由并获取Token

```python
from django.urls import path, re_path, include
from rest_framework.authtoken.views import obtain_auth_token
from rest_framework.documentation import include_docs_urls

urlpatterns = [
    path('api-token-auth/', obtain_auth_token),
    path('docs/', include_docs_urls(title='我的商城接口文档'))
]
```

（3）使用Token

新建permissions.py文件，编辑代码如下
```python
from rest_framework.permissions import BasePermission
from rest_framework.authtoken.models import Token


class IsOwnerOrReadOnly(BasePermission):
    def has_object_permission(self, request, view, obj):
        print(request.headers['Authorization'])

        # 校验前端传过来的token是否在数据库中存在(这里只是粗略校验，不考虑失效的情况)
        for j in Token.objects.all():
            if j.key == request.headers['Authorization'].split(' ')[-1]:
                print(j.key)
                return True
        return False
```

修改视图类
```python
from rest_framework import viewsets
from apps.goods.models import Goods
from apps.goods.serializers import GoodsSerializer
from rest_framework.authentication import TokenAuthentication
from common.permissions import IsOwnerOrReadOnly


class GoodsView(viewsets.ModelViewSet):
    queryset = Goods.objects.all()
    serializer_class = GoodsSerializer
    permission_classes = (permissions.IsAuthenticated, IsOwnerOrReadOnly)
```


（4）postman发起请求

headers入参Authorization: Token 401f7ac837da42b97f613d789819ff93537bee6a

然后发起请求
