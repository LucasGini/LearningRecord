# Django Form表单上传文件

## 新增models
django.models.py
```python
class ImgFile(models.Model):
    name = models.CharField(verbose_name='名称', default='', max_length=32)
    headimg = models.FileField(verbose_name='文件名', upload_to='uploads/')

    def __str__(self):
        return str(self.name)

    class Meta:
        verbose_name = '用户头像信息'
        db_table = 'user_img'

```

## 新增视图函数

django.views.py
```python
def imgfileform(request):
    if request.method == 'GET':
        f = ImgFileForm()
        return render(request, '5/upload_form.html', {'form_obj': f})
    else:
        f = ImgFileForm(request.POST, request.FILES)
        if f.is_valid():
            name = f.cleaned_data['name']
            headimg = f.cleaned_data['headimg']
            userimg = ImgFile()
            userimg.name = name
            userimg.headimg = headimg
            userimg.save()
            print('上传成功')
            return render(request, '5/upload_form.html', {'form_obj': f, 'user': userimg})
```

## 新增表单
django.forms.py
```python
class ImgFileForm(forms.Form):
    name = forms.CharField()
    headimg = forms.FileField()
```

## 新增模板
django.template
```html
<form enctype="multipart/form-data" action="" method="post">
  {% csrf_token %}
  {{ form_obj.as_p }}
  <br>
  <input type="submit" value="文件上传">
  <img src="/app5/media/{{ user.headimg }}">
</form>
```

## 配置路由
django.usrls.py
```python
from django.urls import path, re_path
from django.views.static import serve
urlpatterns = [
    path('imgfileform/', views.imgfileform, name='imgfileform'),
    re_path(r'media/(?P<path>.*)', serve, {'document_root': settings.MEDIA_ROOT}, name='media'),
    ]
```

## 全局配置
django.settings.py
```html
MEDIA_URL = "/media/"
MEDIA_ROOT = os.path.join(BASE_DIR, 'media')
```

