# 用Gunicoen进行部署

Gunicorn是一个在Unix上被广泛使用的、高性能的Python WSGI UNIX HTTP Server。它和大多数Web框架兼容，并具有实现简单、轻量级、高性能等特点

## 安装Gunicorn
安装Gunicorn
```
pip install gunicorn
```

查看版本
```
gunicorn -version
```


## 启动服务并测试
```
Gunicorn autotest_backend.wsgi:application --bind 0.0.0.0:8000 --workers 2
```
其中autotest是保安wsgi.py的目录，监听主机8000端口，开启两个工作进程

浏览器访问http://localhost:8000/进行测试

## 编写配置文件

Gunicorn提供了通过配置文件来启动服务。安装Gunicorn后默认没有配置文件，可以创建一个gunicorn.py文件
```Python
import multiprocessing
preload_app = True                   # 通过预加载应用程序，可以加快服务器的启动速度
workers = 5                          # 并行工作进程数，推荐数量为”（当前的cpu个数*2）+ 1“
threads = 4                          # 每个工作者的线程数
bind = '0.0.0.0:8002'                # 监听IP地址和端口
daemon = 'false'                     # 应用是否以daemon方式运行，默认False
worker_class = 'gevent'              # worker进程的工作方式，有sync、eventlet、gevent、tornado、gthread，默认为sync
worker_connections = 2000            # 设置最大并发量
proc_name = 'test'                   # 设置进程名
pidfile = '/home/web/gunicorn.pid'   # 设置进程文件路径
accesslog = "/home/web/access.log"   # 设置访问日志路径
errorlog = "/home/web/error.log"     # 设置错误信息日志路径
loglevel = "debug"                   # 错误日志输出等级，分为debug、info、warning、error和critical
```


计算当前CPU个数的方法
```Python
import multiprocessing
print(multiprocessing.cpu_count())
```

