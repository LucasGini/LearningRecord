# Django Rest Framework自定义消息格式

## 改造rest_framework.response的Response类

新建customresponse.py文件，编辑如下
```python
from rest_framework.response import Response
from rest_framework.serializers import Serializer

class CustomResponse(Response):

    def __init__(self, data=None, code=None, msg=None,
                 status=None,
                 template_name=None, headers=None,
                 exception=False, context_type=None, **kwargs):
        super().__init__(None, status=status)
        if isinstance(data, Serializer):
            msg = (
                'You passed a Serializer instance as data, but '
                'probably meant to pass serialized ‘.data’ or '
                '‘.error’. representation'
            )
            raise AssertionError(msg)
        self.data = {'code': code, 'msg': msg, 'data': data}
        self.data.update(kwargs)
        self.template_name = template_name
        self.exception = exception
        self.content_type = context_type
        if headers:
            for name, value in headers.items():
                self[name] = value
```


## 改造ModelViewSet视图集

新建custommodelviewset.py文件，编辑如下

```python
from rest_framework import status
from rest_framework import viewsets
from apps.goods.customresponse import CustomResponse


class CustomModelViewSet(viewsets.ModelViewSet):

    # 重写CreateModelMixin类中的create()方法
    def create(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        self.perform_create(serializer)
        headers = self.get_success_headers(serializer.data)
        return CustomResponse(data=serializer.data, code=201, msg='OK', status=status.HTTP_201_CREATED, headers=headers)

    # 重写ListModelMixin类中的list()方法
    def list(self, request, *args, **kwargs):
        queryset = self.filter_queryset(self.get_queryset())

        page = self.paginate_queryset(queryset)
        if page is not None:
            serializer = self.get_serializer(page, many=True)
            return self.get_paginated_response(serializer.data)

        serializer = self.get_serializer(queryset, many=True)
        return CustomResponse(data=serializer.data, code=200, msg='OK', status=status.HTTP_200_OK)

    # 重写RetrieveModelMixin类中的retrieve()方法
    def retrieve(self, request, *args, **kwargs):
        instance = self.get_object()
        serializer = self.get_serializer(instance)
        return CustomResponse(data=serializer.data, code=200, msg='OK', status=status.HTTP_200_OK)

    # 重写UpdateModelMixin类中的update()方法
    def update(self, request, *args, **kwargs):
        partial = kwargs.pop('partial', False)
        instance = self.get_object()
        serializer = self.get_serializer(instance, data=request.data, partial=partial)
        serializer.is_valid(raise_exception=True)
        self.perform_update(serializer)

        if getattr(instance, '_prefetched_objects_cache', None):
            # If 'prefetch_related' has been applied to a queryset, we need to
            # forcibly invalidate the prefetch cache on the instance.
            instance._prefetched_objects_cache = {}

        return CustomResponse(data=serializer.data, code=200, msg='OK', status=status.HTTP_200_OK)

    # 重写DestroyModelMixin类中的destroy()方法
    def destroy(self, request, *args, **kwargs):
        instance = self.get_object()
        self.perform_destroy(instance)
        return CustomResponse(data=[], code=204, msg='OK', status=status.HTTP_204_NO_CONTENT)
```

## 改造分页类

由于分页时list()方法会return self.get_paginated_response(serializer.data)，而该方法存在于rest_framework.pagination 的 PageNumberPagination中，所以需要改造分页类

```python
from rest_framework.pagination import PageNumberPagination
from apps.goods.customresponse import CustomResponse
from rest_framework import status


class MyPage(PageNumberPagination):
    page_size = 1  # 每页显示的数量
    max_page_size = 5  # 最多能设置的每页显示数量
    page_size_query_param = 'size'  # 每页显示数量的参数名称
    page_query_param = 'page'  # 页码的参数名称

    def get_paginated_response(self, data):
        return CustomResponse(data=data, code=200, msg='OK', status=status.HTTP_200_OK, count=self.page.paginator.count,
                              next=self.get_next_link(), previous=self.get_previous_link())
```

## 编辑视图

```python
from apps.goods.models import Goods
from apps.goods.serializers import GoodsSerializer
from apps.goods.custommodelviewset import CustomModelViewSet

class GoodsView_Cusome(CustomModelViewSet):
    queryset = Goods.objects.all()
    serializer_class = GoodsSerializer
    pagination_class = MyPage
```


