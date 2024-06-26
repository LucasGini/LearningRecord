# Django：signals信号及其使用场景

## 什么是Signals？
Django的信号
* Django框架内置的信号发送器，这个信号发送器在框架里面
* 有动作发生的时候，帮助解耦的应用接受到消息通知
* 当动作发生时，允许特定的信号发送者发送消息到一系列的消息接收者
* Signals是同步调用
    
## 信号的应用场景
* 系统解耦；代码复用：实现统一处理逻辑的框架中间件；-->可维护性提升
* 记录操作日志，增加/清除缓存，数据变化接入审批流程；评论通知；
* 关联业务变化通知， 例：通讯录变化的异步事件处理，比如员工入职时发送消息通知团队新人入职，员工离职时异步清理员工的权限等等；
    
## Signals类的子类（django内置的常用信号）
* `django.db.models.signals.pre_init` 模型实例初始化前
* `django.db.models.signals.post_init` 模型实例初始化后
* `django.db.models.signals.pre_save` 模型保存前
* `django.db.models.signals.post_save` 模型保存后
* `django.db.models.signals.pre_delete` 模型删除前
* `django.db.models.signals.post_delete` 模型删除后
* `django.db.models.signals.m2m_changed` 多对多字段被修改
* `django.core.signals.request_started` 接收到HTTP请求
* `django.core.signals.request_finished` HTTP请求处理完毕

所有的Signals都是django.dispatch.Signal的实例/子类
                    

## 如何注册信号处理器/接收器

调用signals任意一个子类的connect方法
```
Signal.connect(receiver, sender=None, weak=True, dispatch_uid=None)
```    
`receiver`：信号接收器，一个回调函数，即处理信号的函数
    
`sender`：信号的发送源，哪个发送方发出的信号
    
`weak`：是否弱引用，默认是弱引用；当receiver位局部变量时。接收器可能会被回收
    
`dispatch_uid`：信号接收器的唯一标识符，用来避免接收器被重复注册
    
    
除了使用Signal.connect()方法注册处理器外，也可以使用@receiver的装饰器类注册
    
示例：使用装饰器来注册，修改数据时，发送消息通知到钉钉

在apps的reday()函数中加载数据处理器

```python
class JobsConfig(AppConfig):
    name = 'jobs'

    def ready(self):
        logger.info("JobConfig ready")
        from jobs.signal_processor import post_save_callback
```

settings中使用完成的名称注册AppConfig，去掉原先注册的jobs应用

```python
INSTALLED_APPS = [
    'bootstrap4',
    ...
    'jobs.apps.JobConfig',
]
```

示例: 使用@receiver装饰器类注册信号处理器：删除投递简历时发送通知

新建 signal_processor.py文件

```python
from django.db.models.signals import post_save, post_delete
from django.dispatch import receiver

from .models import Job, Resume
from interview.dingtalk import send

import json, logging
logger = logging.getLogger(__name__)

# 使用decorator来注册信号处理器
@receiver(signal=post_save, send=Resume, dispatch_uid='resume_post_save_dispatcher')
@receiver(signal=post_save, send=Job, dispatch_uid='job_post_save_dispatcher')
def post_save_callback(sender, instance=None, created=False, **kwargs):
    message = ""
    if instance(instance, Job):
        message = 'Job for %s has been saved' % instance.job_name
    else:
        message = 'Resume for %s %s has been saved' % (instance.username, instance)
    logger.info(message)
    send(message)
```
 
示例：  手工注册信号处理器

```python
from django.forms.models import model_to_dict

def post_delete_callback(sender, instance=None, using=None, **kwarg):
    dict_obj = model_to_dict( instance, exclude=("picture","attachment", "created_date", "modified_date") )
    message = "Instance of %s has been deleted: %s" % (type(instance), json.dumps(dict_obj, ensure_ascii=False))
    logger.info(message)
    send(message)


# 手工注册信号处理器
post_delete.connect(post_delete_callback, sender=Resume, dispatch_uid="resume_post_delete_dispatcher")
```
     
## 自定义信号

1)定义信号：在项目根目录新增文件self_signal.py

```python
import django.dispatch
my_signal = django.dispatch.Signals(providing_args=["argument1", "argument2"])
```

    
2)触发信号：业务逻辑中触发信息

```python
from self_signal import my_signal
my_signal.send(sender="Recruitment", argument1=111, argument2=2)
```

    
3)注册信号处理器/接收器

```python
from self_signal import my_signal
my_signal.connect(callback_of_my_signal)
```

                
