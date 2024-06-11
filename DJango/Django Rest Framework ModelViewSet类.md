# Django Rest Framework ModelViewSet类

ModelViewSet继承了mixins.CreateModelMixin,mixins.RetrieveModelMixin,mixins.UpdateModelMixin,mixins.DestroyModelMixin,mixins.ListModelMixin,GenericViewSet等类，拥有他们的所有特性

## 定义序列化器

```python
from rest_framework import serializers
from apps.goods.models import Goods


class GoodsCategorySerializer(serializers.Serializer):
    id = serializers.IntegerField(read_only=True)
    name = serializers.CharField()


class GoodsSerializer(serializers.Serializer):
    name = serializers.CharField(required=True, max_length=100)
    category = GoodsCategorySerializer(required=False, read_only=True)  # 序列化嵌套
    market_price = serializers.DecimalField(max_digits=8, decimal_places=2)
    price = serializers.DecimalField(max_digits=8, decimal_places=2)

    def create(self, validated_data):  # create()方法对应POST请求，相当于新增数据。前端提交的数据全部存储在字典validated_data中
        print(type(validated_data), validated_data)
        return Goods.objects.create(**validated_data)

    def update(self, instance, validated_data):  # update()方法对应PUT请求，相当于修改数据，instance代表当前修改的实例对象
        print(type(validated_data), validated_data)
        instance.name = validated_data.get('name')
        instance.market_price = validated_data.get('market_price')
        instance.price = validated_data.get('price')
        instance.save()
        return instance
```

## 定义视图

```python
from rest_framework import viewsets
from apps.goods.models import Goods
from apps.goods.serializers import GoodsSerializer


class GoodsView(viewsets.ModelViewSet):
    queryset = Goods.objects.all()
    serializer_class = GoodsSerializer
```


## 定义路由

```python
from django.urls import path, include
from apps.goods import views_viewset
from rest_framework.routers import DefaultRouter

router = DefaultRouter()
router.register('good_all', views_viewset.GoodsView)

urlpatterns = [
    path('', include(router.urls)),
]
```

