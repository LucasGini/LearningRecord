# Django ORM方法

## 查询
1、all()

查询所有的数据
```python
users = UserInfo.objects.all()
```

2、get()

查询一条数据，一般用于主键查询，否则可能会导致查出多条数据导致报错
```python
user = UserInfo.objects.get()

```

3、filter()

根据条件查询符合条件数据
```python
users = UserInfo.objects.filter(age__lt=32)
```

4、exclude()

根据条件排除符合条件的数据，查询不符合条件的数据
```python
users = UserInfo.objects.exclude(age__lt=32)
```

5、value()

提取需要的字段，除了指定的字段，其他字段不显示
```python
users = UserInfo.objects.values('id', 'username', 'truename')
```

6、distinct()

取出重复的数据
```python
users = UserInfo.objects.values('username').distinct()
```

## 新增
1、save()

新增数据
```python
depart = DepartInfo()
depart.department = '技术部'
depart.createdate = timezone.now()
depart.save()
# 或
depart = DepartInfo(department = '技术部'， createdate = timezone.now())
depart.save
```


2、create()

新增一条数据
```python
users = UserBaseInfo.objects.create(username='马六', password='123456', status=1, createdate=timezone.now())
```

## 更新
1、save()

更新数据
```python
user=Userinfo.objects.get(id=1)
user.username='lisi'
user.save()
```

2、update()

更新数据
```python
user = Userinfo.objects.fliter(id=1).update(username='lidongqing')
```

## 删除数据

1、delete()

（1）删除单行数据
```python
oneuser = UserInfo。objects.get(id=1)
oneuser.delete()
```
（2）删除多行数据
```python
oneuser = UserInfo。objects.filter(status=1).delete()
```
（3）删除所有数据
```python
alluser = UserInfo。objects.all().delete()

```

## 关联表操作

1、一对一关联表操作

（1）新增
```python
d = dict(username='lisi',password='123456', status=1, createdate=timezone.now())
one_user = UserBaseInfo.objects.create(**d)
depart = DepartInfo.objects.get(department='技术部')
d1 = dict(username='lisi', truename='李小四',sex=0,salary=6555.68,age=35,status=0,createdate=timezone.now(), memo='',user=one_user, depart=depart)
extrainfo = UserExtraInfo.objects.create(**d1)
```

（2）查询

通过用户基本表访问用户扩展表
```python
user = UserBaseInfo.objects.get(id=1)
user.userextrainfo.username
```

通过外键访问用户基础表
```python
result = UserExtraInfo.objects.get(id=1)
result.user.username
```

2、一对多关联表操作

（1）新增
```python
user = UserBaseInfo.objects.get(id=1)
card = CardInfo(cardno='11111111111111', bank='工商银行', user=user,createdate=timezone.now())
card.save()
```

（2）查询

从一查多

一对多关系中，使用“<模型名消息>_set.all()”的方式查询关联数据，返回QuerySet类型
```python
user = UserBaseInfo.objects.get(id=1)
user.cardinfo_set.all()
```

从多查一
```python
card = CardInfo.objects.get(id=1)
card.user.username   # 使用外键操作
```

3、多对多关联表操作

（1）新增
```python
user = UserExtraInfo.objects.all()
skill = SkillInfo.objjects.get(id=1)
result = skill.user.add(*user) # 给所用用户扩展信息增加技能
```

（2）查询

通过用户扩展表访问技能表
```python
user = UserExtraInfo.objects.get(id=1)
user.skillinfo_set.all()
```

通过外键访问用户扩展表
```python
skill = SkillInfo.objects.get(id=1)
skill.user.all().get(id=2)
```

（3）修改关联数据

使用set()
```python
# 获取用户扩展表信息
user  = UserExtraInfo.objects.all()
# 获取id=1的技能
skill = SkillInfo.objects.get(id=1)
# 修改全部用户中技能id为1的数据
result = skill.user.set(user)
# 修改指定用户中技能id为1的数据
user = [1, 2]
result = skill.user.set(user)
```

（4）删除关联数据

使用remover()方法和clear()方法来删除关联数据
```python
# 获取id=1的用户扩展信息
user = UserExtraInfo.objects.get(id=1)
# 获取id=1的技能
skill = SkillInfo.objects.get(id=1)
# 删除指定用户实例
result = skill.user.remove(user)
# 删除指定用户id
result = skill.user.remove(2)
# 删除skill_id=1 的全部用户
result = skill.user.clear()
```

4、select_related()方法

该方法只能用在“一对多”关系，且设置了外键的模型中

在访问某个模型数据是，可以将关联的模型数据提取出来，可以减少查询数据库的次数
```python
cards = CardInfo.objects.select_related('user')
for card in cards:
    print(card.user)
```


5、prefetch_related()方法

与selsct_related()方法类似，用于解决多对一和多对多关系查询问题。在访问多个表中的数据时，使用它可以减少查询次数
```python
skills = SkillInfo.objects.prefetch_related('user')
for skill in skills:
    print(skill.skillname)
    users = skill.user.all()
    for user in users:
        print(user.username)
```





