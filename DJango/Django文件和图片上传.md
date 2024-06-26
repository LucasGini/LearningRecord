# Django文件和图片上传


## 本地磁盘存储操作过程

1.设置图片、文件存储路径&URL映射

settings里面添加/media路径,urls.py中添加图片路径映射

```python
MEDIA_ROOT = os.path.join(BASE_DIR, 'media')
MEDIA_URL = '/media/'
```

    
2.准备model，form，view和html表单

model里面添加图片/文件字段（如个人照片，个人简历字段到resume）

```python
picture = models.ImageField(upload_to='images/', blank=True, verbose_name='个人照片')
attachment = models.FileField(upload_to='file/', blank=True, verbose_name='简历附件')
```

form.py中增加图片，附件字段

```python
class ResumeForm(ModelForm):

class Meta:
    model = Resume

    fields = ["username", "city", "phone",
              "email", "apply_position", "born_address","gender", "picture", "attachment",
              "bachelor_school", "master_school", "major", "degree",
              "candidate_introduction", "work_experience", "project_experience"
    ]
```

创建简历的视图中展示picture，attachment字段

```python
class ResumeCreateView(LoginRequiredMixin, CreateView):
"""
简历职位页面
"""
template_name = 'resume_form.html'
success_url = '/joblist/'
model = Resume
fields = ['username', 'city', 'phone',
          'email', 'apply_position', 'gender', "picture", "attachment",
          'bachelor_school', 'master_school', 'major', 'degree',
          'candidate_introduction', 'work_experience', 'project_experience']

def post(self, request, *args, **kwargs):
    form = ResumeForm(request.POST, request.FILES)
    if form.is_valid():
        form.save()
        return HttpResponseRedirect(self.success_url)

    return render(request, self.template_name, {'form': form})
```

HTMlL表单模板中增加enctype属性（resume_form.html）

```html
<H2> 提交简历 </H2>
<form method="post" method="post" enctype="multipart/form-data" class="from" style="width:600px;margin-left:5px">
    {% csrf_token %}
    {% bootstrap_form form %}
    {% buttons %}
    <button type="submit" class="btn btn-primary">
        Submit
    </button>
    {% endbuttons %}
</form>
```

3.admin简历列表页展示图片
        

4.变更数据库

5.admin里面添加展示字段，简历列表中加上照片展示

admin文件中：

```python
class ResumeAdmin(admin.ModelAdmin):

actions = (enter_interview_process,)

def image_tag(self, obj):
    if obj.picture:
        return format_html('<img src="{}" style="width:100px;height:80px;"/>'.format(obj.picture.url))
    return ""
image_tag.allow_tags = True
image_tag.short_description = 'Image'
list_display = ('username', 'applicant', 'city', 'apply_position', 'bachelor_school', 'master_school', 'major', 'create_date',
                "image_tag", "attachment")
...
```

        
## 阿里云OSS存储操作过程

优点：复用前面创建好的类，把存储替换为OSS存储

提升系统扩展性、可靠性

1.安装OSS库
```
pip install django-oss-storage
```
2.OSS的依赖添加django_oss_storage到APPS

```python
INSTALLED_APPS = [
    ...
    'django_oss_storage',
]
```

3.settings里面添加OSS设置

```python
# 阿里云OSS配置
DEFAULT_FILE_STORAGE = 'django_oss_storage.backends.OssMediaStorage'
OSS_ACCESS_KEY_ID = ''
OSS_ACCESS_KEY_SECRET = ''
OSS_BUCKET_NAME = 'djangorecruit'
OSS_ENDPOINT = 'oss-cn-beijin.aliyuncs.com'
```

