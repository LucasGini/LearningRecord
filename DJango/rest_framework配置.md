# rest_framework配置

## Rest Framework作用
* 快速将Rest API开放出去
* 配置合适的权限控制、

## 安装
1.安装依赖
```
pip install djangorestframework
pip install markdown
pip install django-filter
```

2.配置django.settings文件

```python
INSTALLED_APPS = [
    ...
    'rest_framework',
]

REST_FRAMEWORK = {
    'DEFAULT_PERMISSION_CLASSES': [
    // 允许自带用户进行增删改查操作或者允许匿名用户进行查询操作
        'rest_framework.permissions.DjangoModelPermissionsOrAnonReadOnly'
    ]
}
```

3.django.url配置路由

```python
urlpatterns = [
    ...
    url(r'^api-auth/', include('rest_framework.urls'))
]
```


4.django.url配置序列序化类

```python
from django.contrib.auth.models import User
from jobs.models import Job
from rest_framework import routers, serializers, viewsets


class UserSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = User
        fields = ['url', 'username', 'email', 'in_staff']


class UserViewSet(viewsets.ModelViewSet):
    queryset = User.objects.all()
    serializer_class = UserSerializer


class JobSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Job
        fields = '__all__'


class JobViewSet(viewsets.ModelViewSet):
    queryset = Job.objects.all()
    serializer_class = JobSerializer
    

router = routers.DefaultRouter()
router.register(r'users', UserViewSet)
router.register(r'jobs', JobViewSet)

urlpatterns = [
    path('api/', include(router.urls)),
    ]
```



