# Django Rest Framework APIView视图函数

## 创建Serialiser
```python
from rest_framework import serializers
from apps.goods.models import Goods


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
from django.http import Http404
from rest_framework import views
from apps.goods.models import Goods
from rest_framework.response import Response
from rest_framework.status import *
from apps.goods.serializers import GoodsSerializer


class GoodsView(views.APIView):

    def get(self, request, *args, **kwargs):
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

    def post(self, request, *args, **kwargs):
        data = request.data  # 接收前端请求的各种数据
        ser_data = GoodsSerializer(data=data, many=False)
        if ser_data.is_valid():  # 判断数据合法性
            goods = ser_data.save()  # 保存数据，实际上调用了create()方法
            return Response(ser_data.data, status=HTTP_201_CREATED)
        else:
            return Response(ser_data.errors)

    def put(self, request, *args, **kwargs):
        data = request.data  # 接收前端请求的各种数据
        try:
            goods = Goods.objects.get(id=kwargs.get('id'))
        except Goods.DoesNotExist:
            raise Http404
        ser_data = GoodsSerializer(goods, data=data, context={'request': request})
        if ser_data.is_valid():  # 校验数据的合法性
            goods = ser_data.save()  # 保存数据，实际上调用create()方法
            return Response(ser_data.data, HTTP_200_OK)
        else:
            return Response(ser_data.errors, status=HTTP_404_NOT_FOUND)

    def delete(self, request, *args, **kwargs):
        goods = Goods.objects.filter(id=kwargs.get('id'))
        goods.delete()
        return Response(status=HTTP_204_NO_CONTENT)
```

## 路由

```python
from django.urls import path
from apps.goods import views_apiview

urlpatterns = [
    path('good_apiview/', views_apiview.GoodsView.as_view(), name='good_apiview'),
    path('good_apiview/<int:id>/', views_apiview.GoodsView.as_view(), name='good_apiview'),
]
```

