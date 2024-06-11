# Django F()函数和Q()函数

## F()函数
用于实现数据表中字段的各种运算操作
```python
from django.db.models import F
user = UserExtraInfo.objects.all()
for user in users:
    user.salary=F('salary') + 1000
    user.save()
```

使用F()函数对某个字段进行更新，需要使用refresh_from_db()方法才能获取最新字段的值信息
```python
for user in users:
    user.refresh_from_db()
    print(user.salary)
```


## Q()函数
用于对象进行多条件查询，支持&(and)、|(or)、~(not)操作符
```python
from django.db.models import Q
user = UserExtraInfo.objects.filter(Q(age__gt=30)& Q(salary__lt=15000))
```


