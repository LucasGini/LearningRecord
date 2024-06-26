# 用DRF框架实现缓存

## 基于数据库方式实现缓存

（1）配置settings.py文件

```python
# 配置基于数据库方式实现缓存
CACHES = {
    'default': {
        'BACKEND': 'django.core.cache.backends.db.DatabaseCache',
        'LOCATION': 'cache_table'
    }
}
```


（2）终端使用如下命令创建缓存数据表

```
python3 manage.py createcachetable
```

## 用装饰器完成缓存
（1）安装drf-extensions包

```
pip install drf-extensions 
```
(2)修改视图类
```python
from rest_framework import views
from apps.goods.models import Goods
from rest_framework.response import Response
from rest_framework.status import *
from apps.goods.serializers import GoodsSerializer
from rest_framework_extensions.cache.decorators import cache_response


class GoodsView(views.APIView):

    @cache_response(timeout=60 * 1, cache='default')   # timeout：缓存时间，单位为秒； cache：缓存使用的配置，即setting.py文件中CACHES配置中的键名称
    def get(self, request, *args, **kwargs):           # 如在装饰器@cache_response中未设置参数，则使用setting.py文件中的默认配置
        if kwargs.get('id'):
            id = kwargs.get('id')
            goods = Goods.objects.filter(id=id)
        else:
            goods = Goods.objects.all()[:10]
        # 开始序列化多条数据
        goods_json = GoodsSerializer(data=goods, many=True)
        goods_json.is_valid()
        print(goods_json.data)
        return Response(goods_json.data, status=HTTP_200_OK)

如在装饰器@cache_response中未设置参数，则使用setting.py文件中的默认配置
REST_FRAMEWORK_EXTENSIONS = {
    # 缓存时间，单位为秒
    'DEFAULT_CACHE_RESPONSE_TIMEOUT': 60,
    # 缓存存储，与配置文件中CACHES的键对应
    'DEFAULT_USE_CACHE': 'default',
}
```

## 用CacheResponseMixin类完成缓存

```python
from rest_framework import viewsets
from apps.goods.models import Goods
from apps.goods.serializers import GoodsSerializer
from apps.goods.mypage import MyPage
from rest_framework import filters
from django_filters.rest_framework import DjangoFilterBackend
from apps.goods.myfilter import GoodsFilter
from apps.goods.custommodelviewset import CustomModelViewSet
from rest_framework import permissions
from rest_framework_extensions.cache.mixins import CacheResponseMixin
from common.permissions import IsOwnerOrReadOnly

# 将CacheResponseMixin作为第一个继承类
class GoodsView(CacheResponseMixin, viewsets.ModelViewSet):
    queryset = Goods.objects.all()
    serializer_class = GoodsSerializer
    pagination_class = MyPage
    filter_backends = (DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter)  # 指定过滤器的配置类
    # filter_fields = ('name', 'price')  # 过滤字段为商品名称和价格
    filterset_class = GoodsFilter
    search_fields = ('name', 'price')
    ordering_fields = ('id', 'name', 'price')
    # permission_classes = (permissions.IsAuthenticated, IsOwnerOrReadOnly)  # 局部配置


class GoodsView_Cusome(CacheResponseMixin, CustomModelViewSet):
    queryset = Goods.objects.all()
    serializer_class = GoodsSerializer
    pagination_class = MyPage
```


