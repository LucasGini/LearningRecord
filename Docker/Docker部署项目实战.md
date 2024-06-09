# Docker部署项目实战

## 拉取并启动mysql容器

1、拉取镜像
```
docker pull mysql:latest
```

2、创建并启动镜像
```
docker run -id --name=mysql -p 3306:3306 -e MYSQL_DATABASE=autotest_backend -e MYSQL_ROOT_PASSWORD=xxxxxx mysql:latest
```
3、配置字符集

（1）复制容器的配置文件到本地
```
docker cp mysql:/etc/mysql/mysql.conf.d/mysqld.cnf /temp
```
（2）修改配置文件
```
vim /temp/mysqld.cnf
```
输入以下内容
```markdown
character_set_server=utf8

init_connect='SET collation_connection = utf8_general_ci'
init_connect='SET NAMES utf8'
character-set-server=utf8
collation-server=utf8_general_ci
skip-character-set-client-handshake
[client]
default-character-set=utf8
```


（3）复制文件到容器
```
docker cp /temp/mysqld.cnf mysql:/etc/mysql/mysql.conf.d/mysqld.cnf 
```
4、重启容器
docker restart mysql

## 部署redis

具体如下：

[docker部署Redis](https://github.com/LucasGini/LearningRecord/blob/main/Docker/Docker%E9%83%A8%E7%BD%B2Redis)

注意：如果其他容器需要链接redis容器，redis.conf配置文件的bind 127.0.0.1注释掉并且设置密码，且需要开放对应的端口，


## 创建项目镜像并启动容器

1、项目主目录下，创建Dockerfile文件，添加以下内容
```Docker
# 基础镜像，基于Python3.10.13
FROM python:3.10.13
# 维护者信息
MAINTAINER lucasGini <dongqianglucas@gmail.com>
# 创建目录
RUN mkdir -p /home/lidongqiang/autotest_backend
# 切换到工作目录
WORKDIR /home/lidongqiang/autotest_backend
# 复制项目到容器中
ADD . /home/lidongqiang/autotest_backend
# 安装依赖
RUN pip install -r requirements.txt -i https://pypi.tuna.tsinghua.edu.cn/simple
# 映射端口
EXPOSE 8000
```


2、项目主目录下新增start.sh文件，添加一下内容
```Shell
#!/bin/bash
python manage.py makemigrations&&
python manage.py migrate&&
gunicorn -c gunicorn.py autotest_backend.wsgi:application
```


3、打包镜像文件并上传到Harbor仓库

克隆代码到/home/lidongqiang目录下，执行以下命令
```Shell
docker build -t autotest_backend:1.0.0 /home/lidongqiang/Dockerfile
docker tag autotest_backend:1.0.0 133:134:33:1/autotest_backend/autotest_backend:1.0.0
docker push 133:134:33:1/autotest_backend/autotest_backend:1.0.0
```


4、启动容器
```
docker run -itd --name=autotest --ling mysql:mydb --link redis_auto:myredis -p 8000:8000 autotest_backend:1.0.0 /bin/bash
```
* --link参数用来链接两个容器，使得源容器和接收容器可以互相通信，并且接收容器可以获取源容器的一些数据。 格式：--link 源容器的id或者name: alias， 如：--ling mysql:mydb
注意：项目setting文件的数据库连接配置，需要将HOST参数改为'mydb'

## 拉取并启动Nginx容器
1、编写Dockerfile文件

新建文件/autotest_backend/conf/nginx/Dockerfile，在其中添加如下内容
```Docker
# 基础镜像，基于nginx最新版本
FROM nginx
# 维护者信息
MAINTAINER lidongqiang <dongqianglucas@gmail.com>
# 工作目录
WORKDIR /usr/share/nginx/html/
# 创建目录
RUN mkdir -p /usr/share/nginx/html/static && mkdir -p /usr/share/nginx/html/media
# 复制本地的nginx配置文件到容器中
COPY nginx.conf /etc/nginx/nginx.conf
# 复制本地的静态文件到容器中
ADD ./static /usr/share/nginx/html/static
ADD ./media /usr/share/nginx/html/media
# 映射端口
EXPOSE 88
# 运行命令，关闭守护模式
CMD ["nginx", "-g", "daemon off;"]
```

2、新建文件/autotest_backend/conf/nginx/nginx.conf，添加如下内容
```
 # 接口自动化API接口
    server {
        listen 88;    # 监听82端口
        listen [::]:88;
        index index.html index.htm index.nginx-debian.html;
        server_name _;
        location / {
            add_header 'Access-Control-Allow-Credentials' 'true';
            add_header 'Access-Control-Allow-Methods' 'GET, POST, OPTIONS';
            add_header 'Access-Control-Allow-Headers' 'content-type,token,id,Content-Type,XFILENAME,XFILECATEGORY,XFILESIZE,XFILESIZE,Origin,X-Requested-With,Accept,content-Type,Authorization';
            proxy_pass http://myweb:8000;     # 转发到本机的8000端口
            proxy_http_version 1.1;
            proxy_set_header  Upgrade $http_upgrade;
            proxy_set_header  Connection keep-alive;
            proxy_cache_bypass $http_upgrade;
            server_name_in_redirect off;
            proxy_set_header Host $host:$server_port;
            proxy_set_header  X-Real-IP $remote_addr;
            proxy_set_header  REMOTE-HOST $remote_addr;
            proxy_set_header  X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header  X-Forwarded-Proto $scheme;
        }
        location /static
        {
            alias /usr/share/nginx/html/static;
        }
        location /media
        {
            alias /usr/share/nginx/html/media;
        }
    }
```

其中proxy_pass http://myweb:8000;，myweb是link参数中项目容器的别名

## 打包并上传到仓库中
```Shell
docker build -t nginx:1.21.1 /autotest_backend/conf/nginx/Dockerfile
docker tag nginx:1.21.1 133:134:33:1/autotest_backend/nginx:1.21.1
docker push 133:134:33:1/autotest_backend/nginx:1.21.1
```


4、启动容器
```
docekr run -itd --name=nginx1.21.1 --link autotest:myweb -p 88:88 nginx:1.21.1
```
5、使用133:134:33:1:88访问项目





