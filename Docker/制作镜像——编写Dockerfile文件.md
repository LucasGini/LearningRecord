# 制作镜像——编写Dockerfile文件

Dockerfile 文件本质上是一个文本文件，可以使用文本编辑器打开并修改。

## 语法规则

Dockerfile 文件是一个镜像构建命令集合的文本文件。

Dockerfile 文件一般分为4 部分:基础镜像、维护者信息、操作指令，以及容器启动时执行的指令

|           基本指令           | 说明                                                              |
|:------------------------:|:----------------------------------------------------------------|
|   FROM \<image>:\<tag>   | FROM 必须是第1行代码，FROM 后面是镜像名称，指明基于哪个基础镜像来构建容器                      |
|        MAINTAINER        | 描述镜像的维护者信息，如名称和邮箱                                               |
|          RUN 命令          | RUN 后面是一个具体的命令                                                  |
|       ADD 源文件 目标路径       | 不仅能够将构建命令所在主机的文件或目录复制到镜像中，还能够将远程 URL所对应的文件或目录复制到镜像中，并支持文件的自动解压缩 |
|      COPY 源文件 目标路径       | 能够将构建命令所在主机的文件或目录复制到镜像中                                         |
|         USER 用户名         | 指定容器启动的用户                                                       |
| ENTRYPOINT command,param | 指定容器的启动命令                                                       |
|    CMD command,param     | 指定容器启动参数                                                        |
|      ENV key=value       | 指定容器运行时的环境变量，格式为:key=value                                      |
|          ARG 参数          | 传递参数                                                            |
|     EXPOSE port xxX      | 指定容器监听的端口，格式为:porVtcp                                           |
| WORKDIR /path/to/workdir | 设置工作目录                                                          |
                        

注意：

FROM 指令:如果在本地仓库中没有镜像,则从公共仓库拉取。如果没有指定镜像的标签则使用默认的 latest 标签。

RUN 指令: 构建镜像的指令。每执行一次 RUN 指令,就会生成一层镜像。在实际使用中可以通过&&链接符号来减少镜像层数，这样可以让镜像的体积更小。

## 构建Nginx镜像

（1）创建目录
```Shell
mkdir /home/autotest_backend
```
进入目录
```Shell
cd /home/autotest_backend
```

下载nginx

http://nginx.org/download/nginx-1.18.0.tar.gz

将nginx.conf配置文件复制到当前目录

（2）在当前目录下创建Dockerfile文件，输入以下内容
```Docker
# 基础镜像,基于Centos 7.9版本构建
FROM centos:7.9.2009
# 维护者信息
MAINTAINER lcuas dongqianglucas@gmail.com
# 安装依赖
RUN yum install -y gcc pcre pcre-devel zlib zlib-devel automake autoconf libtool make
# 复制Nginx包
ADD nginx-1.18.0.tar.gz /opt
# 切换工作目录
WORKDIR /opt/nginx-1.18.0
# 指定安装目录并编译安装
RUN ./configure --prefix=/usr/local/nginx && make && make install
# 创建软链接
RUN ln -s /usr/local/nginx/sbin/nginx /usr/bin/nginx
# 复制本地的Nginx配置文件到容器中
COPY nginx.conf /usr/local/nginx/conf/nginx.conf
# 映射端口
EXPOSE 82
# 运行命令，关闭守护模式
CMD ["nginx", "-g", "daemon off;"]
```

## 根据Dockerfile文件构建镜像
```
docker build -t 镜像:标签 dir
```
-t 参数指给镜像加一个tag。dir指Docker文件所在目录
