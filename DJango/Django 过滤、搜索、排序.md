# Django 过滤、搜索、排序

## 过滤
（1）安装及配置
```
pip install django-filter
```
（2）创建自定义过滤器

新建文件myfilter.py

```python
from django_filters import rest_framework as filters
from apps.goods.models import *

class GoodsFilter(filters.FilterSet):
    # 根据名称进行模糊pip
    name = filters.CharFilter(field_name='name', lookup_expr='icontains')  
    # field_name过滤字段名称，对应于模型中字段名称
    # lookup_expr查询是所要进行的动作，如包含、大于、小于等，与ORM中的运算符保持一致
    # 价格区间
    max_price = filters.NumberFilter(field_name='price', lookup_expr='lte')
    min_price = filters.NumberFilter(field_name='price', lookup_expr='gte')

    class Meta:
        model = Goods
        fields = ('name', 'min_price', 'max_price')

```

（3）修改视图类

```python
from rest_framework import viewsets
from apps.goods.models import Goods
from apps.goods.serializers import GoodsSerializer
from apps.goods.mypage import MyPage
from django_filters.rest_framework import DjangoFilterBackend
from apps.goods.myfilter import GoodsFilter


class GoodsView(viewsets.ModelViewSet):
    queryset = Goods.objects.all()
    serializer_class = GoodsSerializer
    pagination_class = MyPage
    filter_backends = (DjangoFilterBackend, )
```


## 搜索

修改视图类

```python
from rest_framework import viewsets
from apps.goods.models import Goods
from apps.goods.serializers import GoodsSerializer
from apps.goods.mypage import MyPage
from rest_framework import filters
from django_filters.rest_framework import DjangoFilterBackend
from apps.goods.myfilter import GoodsFilter


class GoodsView(viewsets.ModelViewSet):
    queryset = Goods.objects.all()
    serializer_class = GoodsSerializer
    pagination_class = MyPage
    filter_backends = (DjangoFilterBackend, filters.SearchFilter)  # 指定过滤器的配置类
    # filter_fields = ('name', 'price')  # 过滤字段为商品名称和价格
    filterset_class = GoodsFilter
    search_fields = ('name', 'price')
```


## 排序

修改视图类

```python
from rest_framework import viewsets
from apps.goods.models import Goods
from apps.goods.serializers import GoodsSerializer
from apps.goods.mypage import MyPage
from rest_framework import filters
from django_filters.rest_framework import DjangoFilterBackend
from apps.goods.myfilter import GoodsFilter


class GoodsView(viewsets.ModelViewSet):
    queryset = Goods.objects.all()
    serializer_class = GoodsSerializer
    pagination_class = MyPage
    filter_backends = (DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter)  # 指定过滤器的配置类
    # filter_fields = ('name', 'price')  # 过滤字段为商品名称和价格
    filterset_class = GoodsFilter
    search_fields = ('name', 'price')
    ordering_fields = ('id', 'name', 'price')
```

