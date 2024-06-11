# 使用Docker部署mysql

## 拉取镜像

```
docker pull mysql:5.7.29
```

2、创建容器

```
docker run -id --name=outside_mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql:5.7.29
```

* -p 端口映射
* -e 添加系统变量，MYSQL_ROOT_PASSWORD是root用户的登录密码

## 进入容器

```
docker exec -it outside_mysql /bin/bash
```

在容器中执行命令

```Shell
mysql -uroot -p
```
