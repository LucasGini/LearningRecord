## 解决跨域问题（django）

1. 以django为例下载django_cors_headers

   ```liunx
   pip install django_cors_headers
   ```

2. 配置项目settings.py文件

   ```python
   # 将包添加到APP
   INSTALLED_APPS = [
     ...
       'corsheaders',
   	...
   ]
   
   # 配置中间件，并将django.middleware.csrf.CsrfViewMiddleware注释掉
   MIDDLEWARE = [
     ...
       'corsheaders.middleware.CorsMiddleware',
       # 'django.middleware.csrf.CsrfViewMiddleware',
     ...
   ]
   
   # 配置允许跨域地址（前端服务的地址）
   CORS_ALLOWED_ORIGINS = ['http://localhost:8080']
   # 配置允许所有请求
   CORS_ALLOWED_HEADERS = ('*')
   
   ```

   
