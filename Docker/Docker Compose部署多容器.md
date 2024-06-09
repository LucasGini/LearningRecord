# Docker Compose部署多容器

使用Docker Compose可以轻松、高效地管理容器。通过Docker Compose可以先使用yml文件来配置应用程序所需要的所有服务，之后使用一个命令从yml配置文件创建并启动所有服务

主要有以下三个步骤：
   * 用Dockerfile定义应用程序的环境
   * 用docker-compose.yml定义构成应用程序的服务，这样他们可以在隔离环境中一起运行
   * 执行“docker-compose up”命令来启动并运行整个应用程序

## 编排容器文件
新建文件“docker-compose.yml”
```yml
version: "3"
services:
  db:
    image: mysql:5.7
    container_name: mysql
    ports:
      - "3306:3306"
    expose:
      - "3306"
    environment:
      - MYSQL_ROOT_PASSWORD=123456
      - MYSQL_DATABASE=autotest_backend
    command: [
      '--character-set-server=utf8mb4',
      '--collation-server=utf8mb4_unicode_ci'
    ]

  redis:
    image: redis:6.2.6
    container_name: redis
    ports:
      - "6379:6379"
    volumes:
      - /home/docker/redis/data:/data
    expose:
      - "6379"
    command: [
       'redis-server /etc/redis/redis.conf',
       '--appendonly yes',
       '--requirepass 123456',
       '--bind 0.0.0.0'
    ]


  autotest_backend:
    image: 47.106.245.146/autotest_backend/autotest_backend:1.0.1
    container_name: autotest
    command: /bin/bash -c "python manage.py makemigrations && python manager.py migrate && gunicorn -c gunicorn.py autotest_backend.wsgi:application"
    ports:
      - "8000:8000"
    depends_on:
      - db
      - redis
    expose:
      - "8000"
    links:
      - db:mydb
      - redis:myredis
    restart: always
    environment:
      - MYSQL_HOST=mydb
      - MYSQL_DB=autotest_backend
      - MYSQL_USER=root
      - MYSQL_PASSWORD=123456


  nginx:
    image: nginx:1.19.12
    container_name: nginx
    ports:
      - "88:88"
    depends_on:
      - autotest_backend
    expose:
      - "88"
    links:
      - autotest_backend:myweb
    restart: always

```
参数解释

`container_name`：容器名称

`expose`：将端口暴露给其他容器

`ports`：将容器端口和主机端口绑定

`command`：执行的命令

`links`：与其他容器关联

`depends_on`：指定当前服务所依赖的服务

`restart`：在出现错误后会自动重启

2、 构建和启动
```markdown
docker-compose build
docker-compose up -d
# 关闭
docker-compose down
```




  
