# Django 数据库事务处理

事务是指具有原子性的一系列数据库操作，即使程序崩溃了，数据库也会确保这些操作要么全部执行，要么全部未执行

事务具有ACID特性：
* 原子性（Atomicity）:事务作为一个整体被执行，其中对数据库的操作要么全部被执行，要么全部不被执行。
* 一致性（Consistency）：事务应确保数据库的状态从一个一致状态转变为另一个一致状态。一致的含义是，数据库中的数据应满足完整约束。
* 隔离性（Isolation）：在多个事务并发执行时，一个事务的执行不应影响其他事务的执行。
* 持久性（Durability）：已被提交的事务对数据库的修改应该被永久保存在数据库中。
* 
在Django中，通过django.db.transaction模块处理事务

## 装饰器方式

```python
form django.db import transaction
@transaction.atomic
def trans(requset):
  # 开启事务
  save_id = transaction.savepoint()
  try:
      # 代码操作1
      # 代码操作2
      # 提交从保存点到当前状态的所有数据库事务操作
      transaction.savepiont_commit(save_id)
  except:
      # 事务回滚,回滚到保存点
      transaction.savepiont_rollback(save_id)
```

## with语句方式

```python
form django.db import transaction
def trans(request):
    with transaction.atomic():
        # 开启事务
        save_id = transaction.savepoint()
        try:
            # 代码操作1
            # 代码操作2
            # 提交从保存点到当前状态的所有数据库事务操作
            transaction.savepiont_commit(save_id)
        except:
            # 事务回滚,回滚到保存点
            transaction.savepiont_rollback(save_id)
```

## 实例
```python
@transaction.atomic
def userinfo_trans(request):
    # 开启事务
    save_id = transaction.savepoint()
    try:
        # 保存用户基本信息
        d = dict(username='测试1', password='123456', status=1, createdate=timezone.now())
        userbaseinfo = UserBaseInfo.objects.create(**d)
        depart = DepartInfo.objects.get(id=1)
        raise  # 抛异常
        # 保存用户扩展信息
        d = dict(username='测试1', truename='测试1', sex=0, salary=6555.88, age=35, status=0, createdate=timezone.now(),
                 memo='', user=userbaseinfo, depart=depart, department='开发部')
        userextrainfo = UserExtraInfo.objects.create(**d)
        transaction.savepoint_commit(save_id)
        msg = '新增数据成功'
        print(msg)
    except Exception as e:
        print(e)
        transaction.savepoint_rollback(save_id)
        msg = '新增数据失败'
        print(msg)
    return HttpResponse(msg)
```

