# HTML表单上传文件

upload.html

```html
<form enctype="multipart/form-data" action="" method="post">
    {% csrf_token %}
    <input type="file" name="myfile"/>
    <br>
    <input type="submit" value="upload">
</form>

```



views.py

```Python
def upload_file(request):
    if request.method == 'GET':
        return render(request, '5/upload.html')
    # 在请求方法为POST时进行处理。文件上传为POST请求
    if request.method == 'POST':
        # 获取上传的文件，如果没有文件，则默认为None
        myFile = request.FILES.get('myfile', None)
        if myFile:
            # 二进制的写操作
            path = 'media/uploads/'
            if not os.path.exists(path):  # 判断如果不存在该目录，则创建一个
                os.makedirs(path)
            file_path = os.path.join(path+myFile.name)
            dest = open(file_path, 'wb+')
            for chunk in myFile.chunks():   # 分块写入文件
                dest.write(chunk)
            dest.close()
            return HttpResponse('上传完成')
        else:
            return HttpResponse('没有上传文件！')
```

