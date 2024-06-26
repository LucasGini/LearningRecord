# 优雅的架构设计-CSR结构总结Celery

## Celery架构

解决的问题：异步任务调度，定时任务调度
    
## Celery架构之美

清晰得定义了几个基础概念
* API使用起来清晰、简洁
* 关键设计可以扩展，具备高可用性
* 定义了一套协议/API，跨平台（python/Node/PHP客户端，python/Go/Rust服务端）
    
    
3行代码定义一个异步任务tasks.py

```python
from celery import Celery
app = Celery('hello', broker='redis://localhost:6379/0')

@app.task
def add(x, y):
    return x + y
```

调用异步任务&获取执行结果run_task.py

```python
from tasks import add
result = add.delay(4, 4)
run_result = result.get(timeout=1)
print("task result" %s" % run_result)
```

        
## Celery中核心概念
* `Task`：一个需要执行的任务，任务通常异步执行
* `Period Task`：需要定时执行的任务，定时一定间隔执行，也可以使用crontab表达式设定执行周期和时间点
* `Message Broker`：消息代理，临时存储，传输任务到工作节点的消息队列，可以用Redis，RabbitMQ，Amazon SQS作为消息代理。消息代理可以有多个，以保障系统的高可用
* `Worker`：工作节点，执行任务的进程，worker可以有多个，保障系统的高可用和扩展性
* `Result Store`：结果存储
* `Scheduler/Beat`：调度器进程，Beat是定时任务调度器