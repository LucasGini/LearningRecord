# Django Rest Framework 序列化

## 使用Serializer类进行序列化

（1）创建序列化文件
```python
from rest_framework import serializers
class GoodsSerializer(serializers.Serializer):
    name = serializers.CharField(required=True, max_length=100)
    market_price = serializers.DecimalField(max_digits=8, decimal_places=2)
    price = serializers.DecimalField(max_digits=8, decimal_places=2)
```

（2）创建视图类
```python
class GoodsApiView(APIView):

    def get(self, request, *args, **kwargs):
        print(request.query_params)  # DRF的request.query_params用于获取查询字符串的参数，返回QueryDict类型，与Django中的request.GET属性类似
        print(request.data)  # DRF的request.data用于获取前端传递过来的所有数据内容，与Django中的request.POST和request.FILES属性类似
        # 获取queryset
        goods = Goods.objects.all()[:10]
        # 开始序列化多条数据，加上many=True
        goods_json = GoodsSerializer(goods, many=True)
        # 返回序列化对象。goods_json.data是序列化后的值
        print(goods_json.data)
        print(Response(goods_json.data).data)
        return Response(goods_json.data, HTTP_200_OK)
```

## 使用ModelSerializer类进行序列化

（1）创建序列化文件
```python
from rest_framework import serializers
class GoodsModelSerializer(serializers.ModelSerializer):

    class Meta:
        model = Goods  # 关联的模型
        fields = '__all__'  # 显示所有字段
```

（2）创建视图类

```python
class GoodsApiView(APIView):

    def get(self, request, *args, **kwargs):
        print(request.query_params)  # DRF的request.query_params用于获取查询字符串的参数，返回QueryDict类型，与Django中的request.GET属性类似
        print(request.data)  # DRF的request.data用于获取前端传递过来的所有数据内容，与Django中的request.POST和request.FILES属性类似
        # 获取queryset
        goods = Goods.objects.all()[:10]
        # 开始序列化多条数据，加上many=True
        goods_json = GoodsModelSerializer(goods, many=True)
        # 返回序列化对象。goods_json.data是序列化后的值
        print(goods_json.data)
        print(Response(goods_json.data).data)
        return Response(goods_json.data, HTTP_200_OK)
```


## 序列化嵌套

```python
class GoodsModelSerializer(serializers.ModelSerializer):

    category = GoodsCategorySerializer(required=False, read_only=True)  # 序列化嵌套

    class Meta:
        model = Goods  # 关联的模型
        fields = '__all__'  # 显示所有字段

class GoodsModelSerializer(serializers.ModelSerializer):

    category = GoodsCategorySerializer(required=False, read_only=True)  # 序列化嵌套

    class Meta:
        model = Goods  # 关联的模型
        fields = '__all__'  # 显示所有字段
```



