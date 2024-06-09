# Django执行migrate时表已存在的处理方法

使用Django开发web项目，在执行数据迁移时遇到以下错误

```
django.db.utils.OperationalError: table "xxx" already exists
或

django.db.utils.ProgrammingError: relation "xxx" already exists
```
原因是相关数据表已经存在了

在执行迁移时加上--fake-initial参数

```
python manage.py migrate --fake-initial
```

如果外键存在，需要初始化多张表，且部分数据表已经创建，部分未创建，则可以使用--fake <appname>参数来处理，<appname>为对应app的名字

```
python manage.py migrate --fake <appname>
```

详细步骤请参考官方文档

注：如果执行完以上命令依然报错，请确认app下migrations目录的文件与django_migrations表中的记录是否一致，不一致则需要删除对应的迁移文件和django_migrations表中对应的记录数据

Django在执行migrate迁移时会依次执行以下四件事情：

* 判定迁移：对项目中改动过且未迁移的文件进行迁移（Django通过对比app的migrations目录下的文件和django_migrations表中的数据来判定是否有新的迁移变动）

* 新增迁移映射关系：在django_content_type表中新增映射关系（app与model关系）

* 新增迁移权限：在auth_permission表中新增权限

* 执行迁移：生成新的数据表或对已有数据表进行改动

其中，

`migrate --fake`只执行了第一步，并生成迁移记录

`migrate --fake-initial`执行了前三步，不实际变动数据库

而`migrate`则依次执行所有步骤
