# 搭建Harbor私有仓库

## 前置要求

  安装Harbor私有仓库，首先需要安装docker-compose，docker-compose是docker官方的开源项目，负责实现对docker容器集群的快速编排

（1）快速安装docker-compose

```
wget https://github.com/docker/compose/releases/download/1.29.2/docker-compose-Linux-x86_64   # 下载文件

mv docker-compose-Linux-x86_64 /usr/local/bin/docker-compose   # 将文件移动到/usr/local/bin/并改名为docker-compose

chmod +x /usr/local/bin/docker-compose       # 设置文件权限

ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose   # 创建软链接
```

（2）查看版本信息

```
docker-compose version
```

## 安装Harbor

（1）下载Harbor

```
wget https://github.com/goharbor/harbor/releases/download/v1.10.0/harbor-offline-installer-v1.10.0.tgz
```

（2）解压缩

```
tar -xzvf harbor-offline-installer-v1.10.0.tgz
```

（3）进入解压后的目录，配置harbor.yml文件

* Hostname: 本机的IP地址
* harbor_admin_password：登录密码
* port: 端口号

注意：如果不使用https协议，需要将https的配置注释掉

（4）安装并启动harbor

```
./install.sh
```

命令

```
docker-compose up -d   # 启动
docker-compose stop    # 停止，但不删除容器
docker-compose down    # 停止，删除容器及其网络
```

## 登录Harbor

访问http://ip:端口号，打开Harbor登录页面，输入用户名admin和密码登录进入主页面

## 配置、使用Harbor

（1）登录主界面后，新建一个项目，然后进入项目，点击镜像仓库，可以查看Harbor对镜像格式的要求

推送镜像的Docker命令：

在项目中标记镜像：

```
docker tag SOURCE_IMAGE[:TAG] 47.106.xxx.xxx/autotest_backend/IMAGE[:TAG]
```

推送镜像到当前项目：

```
docker push 47.106.xxx.xxx/autotest_backend/IMAGE[:TAG]
```

（2）使用docker tag 命令将本地的SOURCE_IMAGE[:TAG]镜像标识为Harbor能够识别的47.106.xxx.xxx/autotest_backend/IMAGE[:TAG]格式，格式为:IP地址/项目名称/镜像名称

```
docker tag redis:6.2.5 47.106.xxx.xxx/autotest_backend/IMAGE[:TAG]
```

（3）配置以非HTTPS方式推送镜像
修改/ect/docker/daemon.json文件：

```JSON
{
  "registry-mirrors": ["https://eg49oms8.mirror.aliyuncs.com"],
  "insecure-registries": ["47.106.xxx.xxx"]  # 部署了Harbor的主机IP地址（云服务器则使用公网ip）
}
```

```Shell
systemctl daemon-reload  # 加载配置文件
systemctl restart docker  # 重启docker
```

（4）登录Harbor并上传镜像

```
docker login 47.106.xxx.xxx 
```

然后输入用户名和密码

登录成功后，使用docker push命令上传镜像

```Shell
docker push 47.106.xxx.xxx/autotest_backend/redis:6.2.5
```
