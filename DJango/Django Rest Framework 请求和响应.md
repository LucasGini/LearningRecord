# Django Rest Framework 请求和响应

`DRF的request.query_params`用于获取查询字符串的参数，返回QueryDict类型，与Django中的request.GET属性类似

`DRF的request.data`用于获取前端传递过来的所有数据内容，与Django中的request.POST和request.FILES属性类似

封装了状态码
```python
from rest_framework.status import HTTP_200_OK
```
