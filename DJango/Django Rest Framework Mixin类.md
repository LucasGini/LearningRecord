# Django Rest Framework Mixin类

## 创建序列化器

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

## 创建视图

```python
from rest_framework import mixins, generics
from apps.goods.models import Goods
from apps.goods.serializers import GoodsSerializer


# ListModelMixin和CreateModelMixin往往配合使用，实现数据显示和数据新增
class GoodsView(mixins.ListModelMixin, mixins.CreateModelMixin, generics.GenericAPIView):
    queryset = Goods.objects.all()  # queryset和serializer_class都是GenericAPIView的属性，不能更改名称
    serializer_class = GoodsSerializer

    # 实现获取列表功能
    def get(self, request, *args, **kwargs):
        return self.list(request, *args, **kwargs)

    # 实现新增功能
    def post(self, request, *args, **kwargs):
        return self.create(request, *args, **kwargs)


# RetrieveModelMixin、UpdateModelMixin和DestroyModelMixin类，都是针对具体实例进行操作
class GoodsDetailView(mixins.RetrieveModelMixin, mixins.UpdateModelMixin, mixins.DestroyModelMixin, generics.GenericAPIView):
    queryset = Goods.objects.all()
    serializer_class = GoodsSerializer

    # 实现获取单个数据功能
    def get(self, request, *args, **kwargs):
        return self.retrieve(request, *args, **kwargs)

    # 实现更新功能
    def put(self, request, *args, **kwargs):
        return self.update(request, *args, **kwargs)

    # 实现删除功能
    def delete(self, request, *args, **kwargs):
        return self.delete(request, *args, **kwargs)
```


## 配置路由
```python
from django.urls import path
from apps.goods import views_mixins

urlpatterns = [

    path('good_mixin/', views_mixins.GoodsView.as_view(), name='good_mixin'),
    path('good_mixin/<int:pk>/', views_mixins.GoodsDetailView.as_view(), name='goods_detail_view'),
]
```

