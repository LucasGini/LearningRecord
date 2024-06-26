# Django Rest Framework GenericsAPIView类

## GenericsAPIView类是APIView的子类
GenericsAPIView类的子类有：
 * CreateAPIView：继承了GenericsAPIView类和mixins.CreateModelMixin,其中实现了将post方法与mixin类的create()绑定
 * ListAPIView：继承了GenericsAPIView类和mixins.ListModelMixin,其中实现了将get方法与mixin类的list()绑定
 * RetrieveAPIView：继承了GenericsAPIView类和mixins.RetrieveModelMixin,其中实现了将get方法与mixin类的retrieve()绑定
 * DestroyAPIView：继承了GenericsAPIView类和mixins.DestroyModelMixin,其中实现了将delete方法与mixin类的delete()绑定
 * UpdateAPIView：继承了GenericsAPIView类和mixins.UpdateModelMixin,其中实现了将put方法与mixin类的update()绑定，将patch方法与mixin类的partial_update()绑定， 
 * 等等。

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
from rest_framework import generics
from apps.goods.models import Goods
from apps.goods.serializers import GoodsSerializer


# ListCreateAPIView类继承了ListModelMixin类,CreateModelMixin类,GenericAPIView类，并实现了get方法和post方法
class GoodsView(generics.ListCreateAPIView):
    queryset = Goods.objects.all()
    serializer_class = GoodsSerializer


# RetrieveUpdateDestroyAPIView类继承了RetrieveModelMixin类,UpdateModelMixin类,DestroyModelMixin,GenericAPIView类，并实现了get方法、put方法和delete方法
class GoodsDetailView(generics.RetrieveUpdateDestroyAPIView):
    queryset = Goods.objects.all()
    serializer_class = GoodsSerializer
```


## 定义路由
```python
from django.urls import path, include
from apps.goods import views_generics


urlpatterns = [
    path('good_generics/', views_generics.GoodsView.as_view(), name='good_generics'),
    path('good_generics/<int:pk>', views_generics.GoodsDetailView.as_view(), name='good_generics_detail'),
```
