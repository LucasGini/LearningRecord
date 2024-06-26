# 10行代码自动注册所有Model到管理后台

## 场景/解决问题

实际的业务场景中，往往Model多达几十个

一个个写Admin，再写Register，效率低

## 实现方法一

主应用中新增apps.py

```python
from django.contrib import admin
from django.apps import apps, AppConfig


class AdminClass(admin.ModelAdmin):
    def __init__(self, model, admin_site):
        # 列表页自动显示所有的字段：
        self.list_display = [field.name for field in model._meta.fields]
        super(AdminClass, self).__init__(model, admin_site)

# automatically register all models
class UniversalManagerApp(AppConfig):
    """
    应用配置在 所有应用的 Admin 都加载完之后执行
    """
    # the name of the AppConfig must be the same as current application
    name = 'recruitment'

    def ready(self):
        models = apps.get_app_config('running').get_models() 
        for model in models:
            try:
                admin.site.register(model, AdminClass)
            except admin.sites.AlreadyRegistered:
                pass
```

## 实现方法二

* Python的动态model
* python中类也是对象
* 使用Python的动态特性
* 使用type()函数类动态定义一个类
* model = type(name, (models.Moesl,), attrs)

普通的类定义：

```python
class Person(model.Model):
    first_name = models.CharField(max_length=255)
    lasr_name = models.CharField(max_length=255)
```

等同于如下的动态类定义

```python
class type('Person', (models.Moesl,), {
    "first_name": models.CharField(max_length=255),
    "lasr_name": models.CharField(max_length=255)
})
```

对已有数据库提供增删改查功能和Rest API

安装 snadman2

```shell
pip install sandman2
```

以 SQLite数据库为例子，启动sandman2

```shell
sandman2ctl sqlite+pysqlite:///db.sqlte3
```

访问 restapi和管理后台

http://127.0.0.1:5000/

http://127.0.0.1:5000/admin/