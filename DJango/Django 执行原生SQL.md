# Django 执行原生SQL

在实际项目中可能会遇到一些复杂的SQL语句，此时难以使用ORM中提供的API来完成，Django提供了直接执行SQL语句的方法

## raw()方法

（1）基本使用

```Python
users = UserExtraInfo.objects.raw('select * from userbaseinfo4')
for user in users:
    print(type(user), user)
```

（2）条件查询

```Python
username='lisi'
sql = 'select * from UserExtraInfo4 where username=%s'
users = UserExtraInfo.objects.raw(sql, [username])
for user in users:
    print(user.id)
```

（3）多表查询

```Python
sql = 'select a.*, b.* from UserBaseInfo4 a , UserExtraInfo4 b where a.id=b.user_id'
users = UserBaseInfo.objects.raw(sql)
for user in users:
    print(user.id)
```
Django使用主键来标识模型实例，因此主键必须始终包含在原始查询中

## 游标方法

Django用django.db.connection封装了数据库的连接对象，通过连接对象来获取游标（cursor）。

（1）插入数据
```Python
from django.db import connection
from django.utils import timezone

 #  获取游标
cursor = connection.cursor()  
insertsql = 'insert into DepartInfo4(department, createdate) values(%s, %s)'
data = ('总经办', timezone.now())
# 使用游标的execute方法执行sql语句
cursor.execute(insertsql, data) 
# 关闭游标
cursor.close()
```

（2）查询数据

游标对象提供了fetchall()方法获取所有数据和fetchone()方法获取其中一个结果，返回一个元组

```Python
from django.db import connection
cursor = connection.cursor()
cursor.execute('select * from userextrainfo4')
# 使用fetchone()方法以元组方式返回一条记录
row = cursor.fetchone()
print(row)
# 使用fetchall()方法获取所有数据
rows = cursor.fetchall()
print(rows)
cursor.close()
```

（3）更新数据

```Python
cursor = connection.cursor()
try:
    updatesql = 'update departinfo4 set department=%s where id=%s'
    data = ('销售部', 6)
    cursor.execute(updatesql, data)
    # 获取影响的条数
    rowcount = cursor.rowcount
    print(rowcount)
    # 提交事务
    connection.commit()  
except:
    # 有错误，事务回滚
    connection.rollback()
```


（4）删除数据

```Python
cursor = connection.cursor()
sql = 'delete from departinfo4 where department=%s'
data = ['销售部']
cursor.execute(sql,data)
cursor.close()
```




