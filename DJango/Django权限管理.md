# Django权限管理

## 权限设置
Django权限认证系统默认对一个数据库模型设置4个权限：查看（view）、增加（add）、修改（change）、删除（delete）权限

权限表auth_permission
* `id`：主键
* `name`：权限名称，描述信息
* `content_type_id`：使用那个模型管理django_content_type表
* `codename`：具体权限。命名为“权限名称_模型名称”


## 权限认证相关方法
（1）装饰器

Django框架内置Django.contrib.auth模块提供了身份认证和权限管理方面的装饰器

login_required 用户身份认证资源访问装饰器，验证用户是否通过身份认证。如果认证了则允许该用户访问该装饰器关联的函数，否则跳转搭配login_url参数所指定的登录地址。如果不提供login_url参数，则跳转到setting.py中的LOGIN_URL路径
  
permission_required 数据资源访问装饰器，验证用户是否拥有指定权限。如果通过认证了，则允许改用户访问该装饰器关联的函数，否则跳转搭配login_url参数所指定的登录地址。如果不提供login_url参数，则跳转到setting.py中的LOGIN_URL路径
  
Required_GET 请求访问限制装饰器，只允许使用GET方式访问函数
  
Required_POST 请求访问限制装饰器，只允许使用POST方式访问函数

（2）用户通过身份认证后，可以通过权限方法进行用户组和权限的增加

* `user.groups.set(group1, group2)` 为指定用户配置用户组
* `user.groups.remove(group1, group2))` 从指定的用户组中删除用户
* `user.groups.clear()` 将用户从所有的用户组中删除
* `user.user_permissions.set(per1, per2)` 为指定用户配置权限
* `user.user_permissions.remove(per1, per2)` 删除当前用户的权限中指定的权限
* `user.user_permissions.clear()` 删除当前用户所有的权限
* `user.has_perm('app6.add_departinfo')` 检查用户是否拥有app6应用中add_departinfo模型的添加权限

## 自定义用户权限

实际业务比较复杂，Django提供的4个权限显然是不够的需要自定义用户权限

下面的例子在Meta类中新增了permissions属性，以列表或者元组的方式表示，每个权限中包含两个项。分别对应数据库表auth_permission中的codename字段和name字段

```Python
from django.db import models
from django.contrib.auth.models import AbstractUser


class MyUser(AbstractUser):
    photo = models.CharField('用户头像', max_length=50)
    weChat = models.CharField('微信', max_length=30)
    level = models.CharField('用户等级', max_length=1)

    def __str__(self):
        return self.username

    class Meta(AbstractUser.Meta):
        permissions = (
            ['check_myuser', '审核用户信息'],  #  添加自定义权限
        )
```


## 使用装饰器控制页面权限

（1）视图函数

```Python
from django.contrib.auth.decorators import login_required, permission_required
          
@permission_required('app6.change_myuser')
@login_required
def myuser_edit(request):
    return render(request, '6/myuser_edit.html')


@permission_required('app6.view_myuser')  # 用来淹没在当前用户是否拥有对应权限
@login_required  # 装饰器用来设置当前页面的访问权限
def user_index(request):
    users = MyUser.objects.all()
    return render(request, '6/user_index.html', {'users': users})
```


（2）模板

```html
{% if perms.myuser.delete_myuser %}  <!-- 如果在当前权限中包含myuser模型的delete权限，则显示删除 -->
<td><a href="">删除</a></td>
{% endif %}
```

