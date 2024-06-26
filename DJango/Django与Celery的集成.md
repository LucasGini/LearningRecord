# Django与Celery的集成

## Celery的使用

1.什么是Celery？

一个分布式的任务队列 
* 简单：几行代码可以创建一个简单的Celery任务 
* 高可用：工作机会自动重试 
* 快速：可以执行一分钟上百万任务 
* 灵活：每一块都可以扩展
        
示例：

```python
from celery import Celery

app Celery('hello', broker='amqp://guest@localhost//')
    
@app.task
def hello()
    return 'hello world'
```
    
        
2.Celery使用场景

大量需要使用异步任务的场景
* 发送电子邮件，发送IM消息通知
* 爬取网页，数据分析
* 图像、视频处理
* 生成报告，深度学习
        
3.搭建Celery环境

(1)下载Celery包

```
pip install -U Celery
```
(2)安装依赖包
```
pip install "celery[redis,auth,msgpack]"
```    
4.启动Redis服务
```
Redis-server.exe redis.windows.conf
```
5.在项目的根目录下创建一个celery文件夹

6.添加一个tasks.py文件

```python
from celery import Celery
# 第一个参数 是当前脚本的名称， 第二个参数是broker服务地址
app = Celery('tasks', backend='redis://127.0.0.1', broker='redis://127.0.0.1')


@app.task
def add(x, y):
return x + y
```

7.celery目录下启动命令
```
celery -A tasks worker --loglevel=info
```    
8.celery文件下添加一个run_task.py

```python
from tasks import add

result = add.delay(4, 4)
print('Is task ready: %s' % result.ready())

run_result = result.get(timeout=1)
print('task result: %s' % run_result)
```


9.celery目录下运行run_task.py

## 使用flower监控任务执行情况

1.安装flower

```
pip install flower
```
2.启动服务

```
celery -A tasks flower --broker=redis://@localhost:6379/0
```

## Django与Celery集成

1.在项目的主应用下创建celery.py

```python
from __future__ import absolute_import, unicode_literals
import os

from celery import Celery
# 定义配置文件
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'settings.base')
# 直接使用项目名称作为celery的名称
app = Celery('recruitment')
# 指定从django的配置文件中读取配置文件
app.config_from_object('django.conf:settings', namespace='CELERY')

# 自动从加载的各个app目录下寻找tasks.py的脚本，然后把tasks.py里面所有的任务
# 都加载到系统里面，让我们的系统可以运行他
app.autodiscover_tasks()

# task示例
@app.task(bind=True)
def debug_task(self):
    print('Request: {0!r}'.format(self.request))
```

        
2.在项目主应用的_init__.py文件输入：# 作用：应用启动的时候能够加载Celery的环境

```python
from __future__ import absolute_import, unicode_literals  # 防止包有命名冲突

from .celery import app as celery_app # 导入APP

__all__ = ('celery_app',) #把app暴露出去
```

    

3.在django.setting文件进行配置

```python
# Celery application definition
CELERY_BROKER_URL = 'redis://localhost:6379/0'
CELERY_RESULT_BACKEND = 'redis://localhost:6379/1'
CELERY_ACCEPT_CONTENT = ['application/json']
CELERY_RESULT_SERIALIZER = 'json'
CELERY_TASK_SERIALIZER = 'json'
CELERY_TIMEZONE = 'Asia/Shanghai'
CELERYD_MAX_TASK_PER_CHILD = 10
CELERYD_LOG_FILE = os.path.join(BASE_DIR, 'logs', 'celery_work.log')
CELERYBEAT_LOG_FILE = os.path.join(BASE_DIR, 'logs', 'celery_beat.log')
```

    
4.启动Redis

5.启动Celery

linux系统: 
```
DJANGO_SETTINGS_MODULE=settings.local celery --app recruitment worker -l info -P eventlet
```
windows系统：
```
set  DJANGO_SETTINGS_MODULE=settings.local
celery --app recruitment worker -l info -P eventlet
```

6.启动flower监控服务

linux系统：
```
DJANGO_SETTINGS_MODULE=settings.local celery --app recruitment flower --broker=redis://localhost:6379/0
```    
windows系统：
```
set  DJANGO_SETTINGS_MODULE=settings.local
                
celery --app recruitment flower --broker=redis://localhost:6379/0
```
                
## 异步任务

1.在应用目录下创建一个tasks.py

```python
from __future__ import absolute_import, unicode_literals
from celery import shared_task
from .dingtalk import send


# 异步任务
@shared_task
def sen_dingtalk_message(message):
    send(message)
```

        
2.在代码中调用任务

```python
from .tasks import sen_dingtalk_message
def notify_interviewer(modelamin, request, queryset):
candidates = ""
interviewer = ""
for obj in queryset:
    candidates = obj.username + ";" + candidates
    interviewer = obj.first_interviewer_user.useraname + ";" + interviewer
sen_dingtalk_message.delay("候选人%s 进入面试环节，亲爱的面试官，请准备好面试： %s" % (candidates, interviewer)) # delay()延迟调用
messages.add_message(request, messages.INFO, '已经成功发送面试通知')
```


## 定时任务
* 任务心跳管理进程-Beat
* 任务调度器.
    * PersistentScheduler
    * DatabaseScheduler
* 任务存储
    * File Configuration
    * Database
        
1.安装beat：

```
pip install django-celery-beat
```    

2.数据库变更

3.使用DatabaseScheduler启动beat或者在配置中设置beat_scheduler

4.启动beat进程

linux系统：
```
DJANGO_SETTINGS_MODULE=settings.local celery --app recruitment beat --scheduler django_celery_beat.schedulers:DatabaseScheduler
```
windows系统：

```
set  DJANGO_SETTINGS_MODULE=settings.local
                
celery --app recruitment beat --scheduler django_celery_beat.schedulers:DatabaseScheduler
```

5.管理定时任务的方法

在Admin后台添加管理定时任务
   * 登录django管理后台
   * Periodic Tasks 管理配置任务
    
系统启动时自动注册定时任务

在主应用下的celery.py文件添加代码
```python
from celery.schedules import crontab
        @app.on_after_configure.connect # 系统启动完成后再去执行这个方法
        def setup_periodic_tasks(sender, **kwargs):
            # 添加定时任务 每10秒运行一次
            sender.add_periodic_task(10.0, test.s('hello'), neme='hello every 10')
            # 添加定时任务 每30秒运行一次
            sender.add_periodic_task(30.0, test.s('hello'), expires=10)
            # 添加定时任务 每周一7点30分运行一次
            sender.add_periodic_task(
                crontab(hour=7, minute=20, day_of_week=1),  # crontab表达式
                test.s('Happy Mondays!'),
            )
        @app.task
        def test(arg):
            print(arg)
```

直接设置应用的beat_schedule

在主应用下的celery.py文件添加代码

```python
# 注意下面引用的tasks.add的方法，必须显示import，才能正确注册
from recruitment.tasks import add
app.conf.beat_schedule = {
    'add-every-10-seconds': {
        'task': 'recruitment.tasks,add',
        'schedule': 10.0,
        'args': (16, 4,)
    }
}
```
运行时添加定时任务

```python
import json
from django_celery_beat.models import PeriodicTask, IntervalSchedule
# 创建定时策略
schedule, created = IntervalSchedule.objects.get_or_create(every=10, period=IntervalSchedule.SECONDS)
# 再创建任务
task = PeriodicTask.objects.create(interval=schedule, name='say welcome', task='recruitment.celery.test', args=json.dumps(['welcome']))
```


windows系统遇到的问题

(1)执行任务队列报错
```
Traceback (most recent call last):
  File "c:\users\circle\appdata\local\programs\python\python37-32\lib\site-packages\billiard\pool.py", line 358, in workloop
    result = (True, prepare_result(fun(*args, **kwargs)))
  File "c:\users\circle\appdata\local\programs\python\python37-32\lib\site-packages\celery\app\trace.py", line 546, in _fast_trace_task
    tasks, accept, hostname = _loc
ValueError: not enough values to unpack (expected 3, got 0)
```


解决方法：

安装eventlet：

```
pip install eventlet
```

再次启动celery执行任务：

```
celery -A celery_tasks.tasks worker -l info -P eventlet 
```

