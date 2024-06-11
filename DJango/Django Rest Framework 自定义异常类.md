# Django Rest Framework 自定义异常类

（1）新建文件customexception.py，创建视图函数custom_exception_handler()
```python
from rest_framework.views import exception_handler
def custom_exception_handler(exc, context):
    response = exception_handler(exc, context)
    if response is not None:
        response.data.clear()
        # 组装code、 msg 和data
        response.data['code'] = response.status_code
        if response.status_code == 405:
            response.data['msg'] = '请求不允许'
        elif response.status_code == 401:
            response.data['msg'] = '认证未通过'
        elif response.status_code == 403:
            response.data['msg'] = '禁止访问'
        elif response.status_code == 404:
            response.data['msg'] = '未找到文件'
        elif response.status_code >= 500:
            response.data['msg'] = '服务器异常'
        else:
            response.data['msg'] = '其他位置错误'
        response.data['data'] = []
    return response
```

（2）全局配置

```python
REST_FRAMEWORK = {
    ...
    'EXCEPTION_HANDLER': 'apps.goods.customexception.custom_exception_handler'
    ...
}
```

