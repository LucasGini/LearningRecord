# Django 基于后端技术的跨域解决方案

在浏览器中，只要发送请求的URL的协议、域名、端口号这三者中的任意一者与当前页面的地址不同，则称为跨域，

## 浏览器的同源策略

同源策略是一直安全约定，是所有主流浏览器最核心，也是最基本的安全功能之一。同源策略归队：在没有明确授权的情况下，不同域的客户端脚本不能请求对方的资源、同源指：协议、域名、端口号都要相同，只要有一个不同，则是非同源
  
浏览器在执行所有脚本时，都会检查该脚本属于哪个页面：只有同源的脚步才会被执行，如果是非同源的脚本，则浏览器会报异常并拒绝访问
  
如果采用前后端分离的方式开发，则由于前后端应用基本上不会使用同一个协议、域名、端口，所以浏览器的同源策略会导致在前端应用访问后端接口时出现跨域错误


## Django中解决跨域的方法

（1）安装django-cors-headers

```
pip install django-cors-headers
```

（2）设置全局文件

settings.py文件编辑如下

```Python
MIDDLEWARE = [
    'corsheaders.middleware.CorsMiddleware',  # 需要添加在中间件的首位
    'django.middleware.security.SecurityMiddleware',
    ...
]


CORS_ALLOW_CREDENTIALS = True  # 允许跨域时携带Cookie，默认为false
CORS_ORIGIN_ALLOW_ALL = True  # 指定所有域名都可以访问后端接口，默认为false
```

