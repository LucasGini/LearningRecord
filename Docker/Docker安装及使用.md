# Docker安装及使用

安装Docker的操作系统的内核版本不能低于3.10

## 安装Docker
（1）查看linux内核版本
```shell
uname -r
```

（2）安装软件包

```
yum install yum-utils
```

（3）设置yum安装源
```
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
```

（4）在线安装Docker CE版本
```
yum install -y docker-ce
```

（5）卸载Docker
```
yum remove docker-ce
```

## 启动Docker命令
```shell
systemctl start docker   # 启动docker

systemctl restart docker # 重启docker

systemctl stop docker    # 停止docker

systemctl status docker  # 查看状态

systemctl enable docker  # 开机时docker自动启动
```

## 操作镜像
（1）搜索镜像
```shell
docker search 镜像名称
```

（2）获取镜像
```shell
docker pull 镜像名称:镜像标签
```
不指定标签，则默认为latest标签

（3）查看镜像
```shell
docker images
```
* REPOSITORY: 镜像所在的仓库名称
* TAG: 镜像标签
* IMAGE ID: 镜像ID
* CREATED: 镜像创建日期
* SIZE: 镜像的大小

## 导入导出镜像
（1）导出镜像
```shell
docker save imagename -o filepath
如：docker save python:3.8 -o /root/python3.8.tar
```
（2）导入镜像

将导出的镜像文件复制到其他主机，执行如下命令
```shell
docker load -i /root/python3.8.tar
或者使用
cat root/python3.8.tar | docker import - python:3.8
```

## 配置国内镜像仓库
（1)阿里云镜像加速地址
https://cr.console.aliyun.com/cn-hangzhou/instances/mirrors

（2）登录获取加速器地址
https://eg49oms8.mirror.aliyuncs.com

（3）新建配置文件：
```markdown
vim /etc/docker/daemon.json

输入如下内容
{
  "registry-mirrors": ["https://eg49oms8.mirror.aliyuncs.com"]
}
```
保存

（4）重启生效
```shell
systemctl daemon-reload
systemctl restart docker
```

## 操作容器
（1）启动容器

使用docker run 命令来创建并启动容器，相当于docker create 和docker start命令的组合

|  选项参数  | 含义                        |
|:------:|:--------------------------|
|   -i   | 在创建容器后启动容器并进入容器，通常和-t联合使用 |
|   -t   | 启动后进入让你过去的命令行，通常与-i联合使用   |
| --name | 为容器指定一个名称                 |
|   -d   | 创建的容器在后台执行，不会自动登录容器       |
|   -p   | 端口映射，格式为宿主机端口:容器端口        |
|   -v   | 目录映射，格式为宿主机目录:容器目录        |
|   -e   | 设置环境变量，格式为key=value       |
 

（2）查询容器
```shell
docker ps -a
```

（3）进入交互型容器
```shell
docker run -it centos:latest /bin/bash
```

（4）创建后台型容器
```shell
docker run -it -d --name "centos-port" -p 80:80 centos:latest
```
-p 参数指将容器的端口暴露出来，格式为“宿主机端口:容器端口”

## 进入容器
```shell
docker ps

docker exec -it 容器ID /bin/bash
```


## 停止容器
```shell
docker stop -t=60 容器ID或者容器名
```
参数-t指关闭容器的时间，默认值为10s

```
docker kill 容器ID或容器名
```

两者的区别是docker stop会留出一定的时间来让容器保存状态，kill直接关闭容器

## 删除容器
```shell
docker rm 容器名称
```

删除所有未使用的容器
```shell
docker rm $(docker ps -a -q)

```


## 复制容器中的文件
```shell
docker cp [OPTIONS] 容器id:src_path dest_path   # 从容器复制文件到宿主机
docker cp [OPTIONS] dest_path 容器id:src_path  # 从宿主机复制文件到容器
```

## 查看容器中的日志
```shell
docker logs 容器id或容器名

docker logs nginx   # 查看Nginx的logs
```


