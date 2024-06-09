# 使用Django内置模块实现缓存

## 基于数据库方式实现缓存

（1）配置settings.py文件

```Python
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
## 缓存视图函数和视图类

```Python
from django.utils.decorators import method_decorator
from django.views.decorators.cache import cache_page
from rest_framework.decorators import api_view
from apps.goods.models import Goods
from apps.goods.customresponse import CustomResponse
from rest_framework import views
from rest_framework import status
from apps.goods.serializers import GoodsSerializer

# 缓存视图函数
@api_view(['GET'])
@cache_page(60 * 1)  # 缓存一分钟
def index(request):
    goods_list = Goods.objects.all()
    serializer = GoodsSerializer(instance=goods_list, many=True)
    return CustomResponse(data=serializer.data, code='200', msg='OK', status=status.HTTP_200_OK)


# 缓存视图类
# 装饰器cache_page不能直接装饰到视图类上，因此需要使用@method_decorator装饰器对cache_page进行装饰。
@method_decorator(cache_page(60 * 1), name='get')  # 使用@method_decorator装饰器将@cache_page装饰一下，name指视图类中的方法名称
class IndexClass(views.APIView):

    # 直接对视图类中的函数进行缓存处理
    @method_decorator(cache_page(60 * 1))  # 直接怼视图类中的get()方法进行缓存
    def get(self, request, *args, **kwargs):
        if kwargs.get('id'):
            id = kwargs.get('id')
            goods = Goods.objects.filter(id=id)
        else:
            goods = Goods.objects.all()[:10]
        # 开始序列化多条数据
        goods_json = GoodsSerializer(instance=goods, many=True)
        print(goods_json.data)
        return CustomResponse(data=goods_json.data, code='200', msg='OK', status=status.HTTP_200_OK)
```
