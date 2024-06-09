# Django 中间件

## 中间件是什么

中间件实际上就是AOP（面向切面编程）。AOP的注意作用是，把一些跟核心业务逻辑模块无关的功能抽离出来。如日志统计、安全控制、异常处理等。把这些功能抽离出来之后，在通过“动态切入”的方式将这些功能融入业务逻辑模块中。
  
使用AOP的好处：
* 可以保存业务逻辑模块的“纯净”和高内聚性，
* 可以很方便地复用日志统计、安全控制等功能

## Django中间件

setting.py文件中

```Python
MIDDLEWARE = [
    'django.middleware.security.SecurityMiddleware',  # 内置的安全中间件
    'django.contrib.sessions.middleware.SessionMiddleware',  # 会话中间件
    'django.middleware.common.CommonMiddleware',  # 通用中间件，用来规范请求内容
    'django.middleware.csrf.CsrfViewMiddleware',  # 开启CSRF防护功能
    'django.contrib.auth.middleware.AuthenticationMiddleware',  # 内置用户人群
    'django.contrib.messages.middleware.MessageMiddleware',  # 开启信息提示
    'django.middleware.clickjacking.XFrameOptionsMiddleware',  # 防止恶意程序点击劫持
]
```
Django会在MIDDLEWARE列表中按照从上到下的顺序执行中间件的process_request()方法，安装从下到上的顺序执行中间件的process_response()方法


## Django中间件的方法

* `process_request(self, request)` 在处理请求前在每个请求上调用，返回None或者HttpResponse对象
* `process_view(self, request, callback, callback_args, callback_kwargs)` 在处理视图前每个请求上调用，返回None或HttpResponse对象
* `process_template_response(self, request, response)` 如果views()函数中返回的对象具有render()方法，则直接执行该方法。在每个请求上调用，返回实现了render()方法的响应对象
* `process_exception(self,request,exception)` 在视图抛出异常时调用，在每个请求上调用，返回一个HttpResponse对象
* `process_response(self,request,response)`  在处理响应后，所有响应返回浏览器之前被调用，在每一个请求上调用，返回HttpResponse对象

## 自定义中间件

（1）在APP目录下新建一个middle目录，在middle目录下创建一个mymiddle.py文件

```Python
# 中间件必须继承MiddlewareMixin类
from django.utils.deprecation import MiddlewareMixin
from django.http import HttpResponse


class AuthMiddleware1(MiddlewareMixin):

    def process_request(self, request):
        print('process_request1()方法执行')
        # return HttpResponse('返回')  # 当中间件调用return HttpResponse()语句时，会直接执行该类下的process_response()方法返回，后面的中间件2及视图函数不会被执行

    def process_view(self, request, callback, callback_args, callback_kwargs):
        print('process_view1()方法执行')

    def process_template_response(self, request, response):
        print('process_template_response1()方法执行')
        return response

    def process_exception(self, request, exception):
        print('process_exception1()方法执行')

    def process_response(self, request, response):
        print('process_response1()方法执行, 状态为', response.reason_phrase)
        return response


class AuthMiddleware2(MiddlewareMixin):

    def process_request(self, request):
        print('process_request2()方法执行')

    def process_view(self, request, callback, callback_args, callback_kwargs):
        print('process_view2()方法执行')

    def process_template_response(self, request, response):
        print('process_template_response2()方法执行,用得很少，不做测试')
        return response

    def process_exception(self, request, exception):
        print('process_exception2()方法执行')

    def process_response(self, request, response):
        print('process_response2()方法执行, 状态为', response.reason_phrase)
        return response
```

（2）配置中间件

打开setting.py文件，在MIDDLEWARE列表最后配置自定义的中间件

```Python
MIDDLEWARE = [
    ...
    'app6.middle.mymiddle.AuthMiddleware1',
    'app6.middle.mymiddle.AuthMiddleware2',
]
```

## 使用中间件简化权限认证

（1）在middle目录下创建一个permmiddleware.py文件  

```Python
from django.utils.deprecation import MiddlewareMixin
from django.shortcuts import redirect, render, HttpResponse
import re


class PermissionMiddleware(MiddlewareMixin):

    def process_request(self, request):
        # 获取当前路径
        curr_path = request.path
        print(curr_path)
        # 白名单处理
        white_list = ['/myuser_login/', '/myuser_reg/']
        for w in white_list:
            if re.search(w, curr_path):
                return None  # 当前路径在白名单内，则不进行登录验证
            # 验证是否登录
            print(request.user.is_authenticated)
            if not request.user.is_authenticated:
                return redirect('/app6/myuser_login/')
```

（2）配置中间件

打开setting.py文件，在MIDDLEWARE列表最后配置自定义的中间件

```Python
MIDDLEWARE = [
    ...
    'app6.middle.mymiddle.AuthMiddleware1',
    'app6.middle.mymiddle.AuthMiddleware2',
    'app6.middle.permmiddleware.PermissionMiddleware',
]
```


