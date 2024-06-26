# 使用AJAX提交表单

## 基于jQuery技术实现AJAX

```html
<script src="plugins/jquery/jquery.min,js"></script>  <!-- 引用jQuery文件 -->
{% load static %}
<script>
$("#submit").click(function()){
    $.ajax({
        url: "/app5/ajax_login_data/",  <!-- 提交的URL地址 -->
        async: true,  <!-- 是否异步，默认true -->
        type: "post",  <!-- 提交方式 -->
        data: {  <!-- 以字典方式传递参数 -->
            "username": $('#username').val(),
            "password": $('#password').val(),
            "csrfmiddlewaretoken": $('[name=csrfmiddlewaretoken]').val()，
        },
        success: function (data) {  <!-- 请求成功，返回相关数据 -->
            console.log(data)
        },
        error: function (jaXHR, textStatus, err) {  <!-- 请求失败，返回相关数据 -->
            console.log(arguments)
        },
    })
}
</script>
```

## 在AJAX请求中设置令牌获取csrf_token

在前台模板中解决

(1)获取名称为csrfmiddlewaretoken的HTML元素的值并赋值给csrfmiddlewaretoken参数

```html
{% csrf_token %}
data: { 
            "username": $('#username').val(),
            "password": $('#password').val(),
            "csrfmiddlewaretoken": $('[name=csrfmiddlewaretoken]').val()，
        },
```

(2) 直接使用模板变量{{csrf_token}}的值并赋值给csrfmiddlewaretoken参数

```html
{% csrf_token %}
data: { 
            "username": $('#username').val(),
            "password": $('#password').val(),
            "csrfmiddlewaretoken": {{csrf_token}}，
        },
```


在视图中解决,视图函数增加@csrf_exempt装饰器

```python
@csrf_exempt
def imgfileform(request):
    if request.method == 'GET':
        f = ImgFileForm()
        return render(request, '5/upload_form.html', {'form_obj': f})
```

