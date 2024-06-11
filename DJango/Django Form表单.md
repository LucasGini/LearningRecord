# Django Form表单

## 表单常见属性

* `From.as_p`属性:为表单字段提供\<p>标签
* `From.as_table`属性:为表单字段提供\<table>标签
* `From.as_ui属性`: 为表单字段提供\<ui>标签
* `From.isvalid()`方法，验证表单中的数据是否合法
* `From.cleaned_data`属性，获取表单中通过验证的数据
* `From.errors`属性, 表单的验证错误信息

## 表单数据校验
```python
class UserInfoMsgForm(forms.Form):
    username = forms.CharField(label='用户名称', min_length=6, widget=forms.widgets.TextInput(
    attrs={'class': 'form-control', 'placeholder': '请输入用户名称'}),
    error_messages={'required': '用户姓名不能为空',
    'min_length': '长度最少6位', 'invalid': '不能含有特殊字符'})
...
    
error_messages={'required': '用户姓名不能为空', 'min_length': '长度最少6位', 'invalid': '不能含有特殊字符'}
```
required参数对应的是提交内容为空时所提示的信息，invalid对应的是当提交的数据出现格式错误时所提示的信息

## 自定义验证规则
```python
from django.core.exceptions import ValidationError   # 导入错误
def mobile_validate(value):
    """校验手机号格式"""
    mobile_re = re.compile(r'(13[0-9]|15[0123456789]|17[678]|18[0-9]|14[57])[0-9]{8}$')  # 手机号码正则判断
    if not mobile_re.match(value):
        raise ValidationError('手机号码格式错误')
      
def age_validate(value):
    """校验age必须大于0"""
    if value < 0 or value > 120:
        raise ValidationError('年龄范围为1~120')

class UserInfoMsgForm(forms.Form):
      ...
    age = forms.IntegerField(label='年龄', initial=1, validators=[age_validate],
                             error_messages={'required': '年龄不能为空'})
    mobile = forms.CharField(label='手机号码', validators=[mobile_validate], error_messages={'required': '手机号码不能为空'})
      ...

```

## 表单数据获取
```python
def userinfo_msg_form(request):
    if request.method == 'GET':
        myform = UserInfoMsgForm
        return render(request, '5/userinfoform.html', {'form_obj': myform})
    else:
        f = UserInfoMsgForm(request.POST)
        if f.is_valid():
            print(f.clean())
            print(f.cleaned_data['username'])
            print(f.data)
        else:
            errors = f.errors
            print(errors)
            return render(request, '5/userinfoform.html', {'form_obj': f, 'errors': errors})
    return render(request, '5/userinfoform.html', {'form_obj': f})
```

`f.clean()`表示获取全部数据，以字典方式返回；

`f.cleaned_data['username']`表示获取数据中username字段内容；

`f.data`表示获取全部数据，以QusryDict字典方式返回

