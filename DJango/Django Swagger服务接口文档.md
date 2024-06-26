# Django Swagger服务接口文档

Swagger是一个用于生成、描述和调用RESTful接口的web服务。通俗来讲，Swagger服务就是将项目中所有对外提供的接口展现在页面上，并且可以在线进行接口的调用和测试

## 使用步骤

（1）安装配置django-rest-swagger

```
pip install django-rest-swagger
```

（2）修改settings.py文件

```python
INSTALLED_APPS = [
    ...
    'rest_framework_swagger',
    ...
]
```

（3）配置视图类

```python
class GoodsView_Custom(CustomModelViewSet):
"""
list:
    返回所有数据
retrieve：
    返回单条数据实例
create：
    新增数据
update：
    修改数据
partial_update:
    修改部分数据
delete：
    删除数据
"""
```

（4）配置路由

```python
from rest_framework_swagger.renderers import SwaggerUIRenderer, OpenAPICodec
from rest_framework.schemas import get_schema_view

schema_view = get_schema_view(title='我的商城接口文档', renderer_classes=[SwaggerUIRenderer, OpenAPICodec])

urlpatterns = [
    ...
    path('docs2/', schema_view, name='docs'),
]
```


## 错误处理

错误1：'AutoSchema' object has no attribute 'get_link'

解决：REST_FRAMEWORK中添加'DEFAULT_SCHEMA_CLASS': 'rest_framework.schemas.coreapi.AutoSchema',  # rest_framework_swagger的配置

```python
REST_FRAMEWORK = {
　　'DEFAULT_SCHEMA_CLASS': 'rest_framework.schemas.coreapi.AutoSchema', # rest_framework_swagger的配置}

```



错误2：'staticfiles' is not a registered tag library. Must be one of:

解决：找到虚拟环境文件夹env/lib/python3.10/site-packages/rest_framework_swagger/templates/rest_framework_swagger/index.html,将第二行的{% load staticfiles %} 修改为：{% load static %}
