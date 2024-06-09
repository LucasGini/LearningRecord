# Django ModelForm

## 新增models
django.models.py
```Python
class UserBaseInfo(models.Model):
    id = models.AutoField(verbose_name='编号', primary_key=True)
    username = models.CharField(verbose_name='用户名称', max_length=30)
    password = models.CharField(verbose_name='密码', max_length=20)
    status = models.CharField(verbose_name='状态', max_length=1)
    createdate = models.DateTimeField(verbose_name='创建日期', db_column='createDate')
    mobile = models.CharField(verbose_name='手机号码', null=True, max_length=255)
    age = models.IntegerField(verbose_name='年龄', null=True, max_length=12)

    def __str__(self):  # __str__()方法只允许返回字符类型的字段，如需要返回其他类型字段，需要使用str()函数进行转换
        return self.username

    class Meta:
        verbose_name = '人员基本信息'
        db_table = 'UserBaseInfo4'

```

## 新增视图函数
django.views.py
```Python
def user_base_info_model_form(request):
    if request.method == 'GET':
        f = UserBaseInfoModelForm()
        return render(request, '5/user_base_info_model_form.html', {'form_obj': f})
    else:
        # （1）接收POST请求提交的数据，直接保存到数据库中，返回一个模型对象
        f = UserBaseInfoModelForm(request.POST)
        new_userinfo = f.save()

        # （2）调用表单类生成实例，完成数据显示
        a = UserBaseInfo.objects.get(id=1)
        f = UserBaseInfoModelForm(initial=a)

        # （3）调用表单类生成实例，完成数据修改
        a = UserBaseInfo.objects.get(id=1)
        f = UserBaseInfoModelForm(request.POST, initial=a)
        new_userinfo = f.save()

        # （4）延迟保存
        # save()方法接受一个commit参数，默认为True, 可以实现Model实例的保存。如果将commit参数设置为False，则不会立刻执行保存动作，可以在这个保存动作之前增加一些其他操作
        user = UserBaseInfoModelForm(request.POST)
        if user.is_valid():
            user = user.save(commit=False)
            user.username = request.username
            user.save()

        return render(request, '5/user_base_info_model_form.html', {'user_info': new_userinfo, 'form_obj': f})

```

## 新增表单
```Python
class UserBaseInfoModelForm(forms.ModelForm):
    class Meta:
        # 定义关联模型
        model = UserBaseInfo
        # 定义需要在表单中展示的字段
        fields = ['username', 'password', 'age', 'mobile', 'status']
        # 显示全部字段可以如下设置
        # fields = '__all__'
        # 如果在Models中定义了名称，则在这里不用再定义
        labels = {
            'age': '年龄',
            'mobile': '手机信息'
        }
        # 将文本框渲染为密码输入框
        widgets = {'password': forms.PasswordInput(attrs={'class': 'password'}, render_value=True)}
        error_messages = {
            'username': {'required': '用户姓名不能为空', 'min_length': '长度最少6位', 'invalid': '输入正确的用户姓名'},
            'password': {'required': '密码不能为空', 'min_length': '长度最少6位', 'max_length': '密码最长10位'},
            'age': {'required': '年龄不能为空'},
            'mobile': {'required': '手机号码不能为空'},
            'status': {'required': '状态不能为空'},
        }

          # 校验手机号码的局部钩子函数，校验函数的语法格式为：clean_字段名()，该字段中输入的数据必须经过验证函数的校验
        def clean_mobile(self):
            mobile = self.cleaned_data.get('mobile')
            mobile_re = re.compile(r'(13[0-9]|15[0123456789]|17[678]|18[0-9]|14[57])[0-9]{8}$')  # 手机号码正则判断
            if not mobile_re.match(mobile):
                raise ValidationError('手机号码格式错误')
            return mobile

        # 全局钩子函数，重写clean()函数来实现校验，使用该函数相当于对每个字段都进行处理
        def clean(self):
            password = self.cleaned_data.get('password')
            confirm_password = self.cleaned_data.get('confirm_password')
            if password != confirm_password:
                raise ValidationError('二次密码输入不一致')
```


## 新增模板
django.template
```html
<form enctype="multipart/form-data" action="" method="post">
  {% csrf_token %}
  {{ form_obj.as_p }}
  <input type="submit" value="提交">
  <p>新增客户id为：{{ user_info.id }}</p>
</form>
```

## 配置路由
django.usrls.py
```Python
from django.urls import path, re_path
from django.views.static import serve
urlpatterns = [
    path('user_base_info_model_form/', views.user_base_info_model_form, name='user_base_info_model_form')
    ]
```



