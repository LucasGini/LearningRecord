# Django用户管理

## 常用方法

* `authenticate(username, password)` 用户验证功能，如果认证成功，则返回一个User对象
* `login(HttpRequest, user)` 用户登录功能，其中user位经过认证的User对象，登录成功后将用户身份信息记录到请求的回合对象中存储，使用request.user可以获取当前登录用户对象。如未登录，则request.user得到的是一个匿名用户对象AnonymousUser
* `is_authenticated()` 判断当前用户是否经过认证
* `longout(request)` 清除当前请求，注销会话
* `create_user()` 创建新用户，至少能提供用户名和密码
* `set_password(password)` 修改密码
* `check_password(passwrod)` 检查密码是否正确

## 扩展用户明细

django自带的用户模型的字段是有限的，无法满足实际业务需要。Django框架提供了扩展用户模型的方法，一般使用继承AbstractUser类的方式来扩展用户模型。AbstractUser类不会改变现有验证方法的使用

（1）修改数据模型/app6/models.py

```Python
from django.db import models
from django.contrib.auth.models import AbstractUser


class MyUser(AbstractUser):
    photo = models.CharField('用户头像', max_length=50)
    weChat = models.CharField('微信', max_length=30)
    level = models.CharField('用户等级', max_length=1)

    def __str__(self):
        return self.username

```

（2）配置项目文件django.settings.py

```Python
AUTH_USER_MODEL='app6.MyUser'

```

（3）数据迁移

```
python manage.py makemigrations
python manage.py migrate
```


## 用户注册

（1）视图函数

```Python
from django.contrib.auth.models import User

def myuser_reg(request):
    if request.method == 'GET':
        f = MyUserRegisterForm
        return render(request, '6/myuser_reg.html', {'form_obj': f})
    if request.method == 'POST':
        f = MyUserRegisterForm(request.POST)
        if f.is_valid():
            data = f.cleaned_data
            uname = data['username']
            pwd = data['password']
            email = data['email']
            wechat = data['wechat']
            if MyUser.objects.filter(username=uname):
                info = '用户已存在'
            else:
                b = dict(username=uname, password=pwd, email=email, weChat=wechat, is_superuser=1, is_active=1, level=1, is_staff=1)
                user = MyUser.objects.create_user(**b)
                info = '注册成功，请登录'
                return redirect(reverse('myuser_login'))
        else:
            info = '表单内容为空，请重新输入'
            return render(request, '6/myuser_reg.html', {'info': info, 'form_obj': f})
```


（2）表单

```Python
class MyUserRegisterForm(forms.Form):

    username = forms.CharField(label='用户名', min_length=3, widget=forms.TextInput(
        attrs={'class': 'username', 'placeholder': '请输入用户名称'}), error_messages={'required': '用户名不能为空'})
    password = forms.CharField(label='密码', min_length=6, max_length=15, widget=forms.PasswordInput(
        attrs={'class': 'password', 'placeholder': '请输入密码'}
    ))
    re_password = forms.CharField(label='密码', min_length=6, max_length=15, widget=forms.PasswordInput(
        attrs={'class': 're_password', 'placeholder': '请重复输入密码'}), )
    email = forms.CharField(label='邮箱')
    wechat = forms.CharField(label='微信')
```


（3）模板

```html
<form enctype="multipart/form-data" action=""  method="post">
    {% csrf_token %}
    {{ form_obj.as_p }}
    <input type="submit" value="登录"> {{ info }}
</form>
```


## 登录用户

（1）视图函数

```Python
from django.contrib.auth import authenticate, login, logout

def myuser_login(request):
    if request.method == 'GET':
        f = UserLoginForm
        return render(request, '6/myuser_login.html', {'form_obj': f})
    if request.method == 'POST':
        f = UserLoginForm(request.POST)
        if f.is_valid():
            data = f.cleaned_data
            uname = data['username']
            pwd = data['password']
            if MyUser.objects.filter(username=uname):
                user = authenticate(username=uname, password=pwd)
                if user:
                    if user.is_active:
                        login(request, user)
                        return redirect(reverse('user_index'))
                    else:
                        info = '用户还未激活'
                else:
                    info = '账号密码不正确'
            else:
                info = '用户账号不存在，请查询'
        else:
            info = '请正确输入账号密码'
        return render(request, '6/myuser_login.html', {'form_obj': f, 'info': info})
```


（2）表单

```Python
class UserLoginForm(forms.Form):

    username = forms.CharField(label='用户名', min_length=3, widget=forms.TextInput(
        attrs={'class': 'username', 'placeholder': '请输入用户名称'}), error_messages={'required': '用户名不能为空'})
    password = forms.CharField(label='密码', min_length=6, max_length=15, widget=forms.PasswordInput(
        attrs={'class': 'password', 'placeholder': '请输入密码', }), error_messages={'required': '密码不能为空'})
```

（3）模板

```html
<form enctype="multipart/form-data" action=""  method="post">
    {% csrf_token %}
    {{ form_obj.as_p }}
    <input type="submit" value="登录"> {{ info }}
</form>
```

## 用户退出

（1）视图函数

```Python
def myuser_logout(request):
    logout(request)
    return redirect(reverse('myuser_login'))
```

