# Django Rest Framework 分页

## 方法一：
（1）新建一个自定义分页类

新建文件mypage.py
```Python
from rest_framework.pagination import PageNumberPagination
class MyPage(PageNumberPagination):
    page_size = 1  # 每页显示的数量
    max_page_size = 5  # 最多能设置的每页显示数量
    page_size_query_param = 'size'  # 每页显示数量的参数名称
    page_query_param = 'page'  # 页码的参数名称
```

（2）编辑视图类

```Python
from rest_framework import viewsets
from apps.goods.models import Goods
from apps.goods.serializers import GoodsSerializer
from apps.goods.mypage import MyPage

class GoodsView(viewsets.ModelViewSet):
    queryset = Goods.objects.all()
    serializer_class = GoodsSerializer
    pagination_class = MyPage
```


## 方法二：

全局配置分页选项

编辑settings.py文件
```Python
REST_FRAMEWORK = {
    'DEFAULT_PAGINATION_CLASS': 'rest_framework.pagination.PageNumberPagination',
    'PAGE_SIZE': 1,
}
```

