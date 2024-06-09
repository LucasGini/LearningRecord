# 用Nginx进行代理

Nginx是一个高性能的HTTP和反向代理服务器，也是一个IMAP/POP3/SMTP代理服务器，以事件驱动的方式编写

* 在性能上，Nginx占用很少的系统资源，支持更多的并发连接，可以达到更高的访问效率
* 在功能上，Nginx是优秀的代理服务器和负载均衡服务器
* 在安装配置上，Nginx安装简单、配置灵活

Nginx支持动静分离。为了加快网站的解析速度，可以把动态页面何静态页面放在不同的服务器上，以减少服务器压力，加快解析速度

Nginx支持热部署，启动速度特别快，还可以在不间断服务的情况下对软件版本或配置进行升级，即使运行数月也无须重新启动

## 正向代理和反向代理
1. **正向代理**

   有些网站使用浏览器无法直接访问，这时可以找一个代理进行访问。将请求发给代理服务器，由代理服务器去访问目标网站，最后代理服务器将访问后的数据返给浏览器
   
   正向代理代理的是客户端，用户的一切请求都交给代理服务器去完成，至于代理服务器如何完成，用户可以不用关心
2. **反向代理**

   在某个网上商城购买物品，只需打开浏览器输入网址即可。现在的网站都是分布式部署。后台有成千上万的机器设备。购物请求由反向代理软件分发到某一台服务器上来完成。

   反向代理代理的是服务器，隐藏了服务器的信息

## 为什么用了uWSGI还需要用Nginx
* Nginx处理静态文件更有优势，性能更好
* Nginx更安全
* Nginx可以进行多台机器的负载均衡

## 安装Nginx
1. 下载Nginx包，并解压缩

   Nginx多版本包下载地址：http://nginx.org/download/

   使用wget命令 下载
   ```markdown
   // 解压缩
   tar -zxvf nginx-1.18.0.tar.gz
   ```
2. 执行命令

   * 安装依赖：yum -y install gcc pcre pcre-devel zlib zlib-devel

   * 指定Nginx的安装目录： 进入解压后的目录，输入命令./configure --prefix=/usr/local/nginx

   * 编译和安装：make && make install 

   * 建立软连接：ln -s /usr/local/nginx/sbin/nginx /usr/bin/nginx
3. 启动并测试
   * 启动命令：nginx
   
   * 查看进程： ps -ef |grep nginx
   
   * 测试：curl http://localhost
   
4. 常用命令
   * 直接停止：nginx -s stop
   
   * 完成已接收的连接请求后退出：nginx -s quit
   
   * 重启Nginx：nginx -s reload

## 配置文件

Nginx的配置文件为nginx.conf，可以使用vim命令编辑，Nginx配置文件分为3部分：全局部分、events部分、http部分

### 全局部分
```markdown
#user  nobody;
worker_processes  1;                 # 开启的进程数，小于或等于cpu数

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;         # 进程号的保存文件

（2）events部分
每个worker允许同时产生多少个链接，默认1024个
events {
    worker_connections  1024;
}
```

### http部分

http部分包括的参数有文件引入、日志格式定义、连接数等。其中server块相当于一个站点，一个http块可以拥有多个server块

其中location块，用来对同一个server中的不同请求路径做特定的处理，比如，将“/admin”转交给8001端口处理，将“/h5”转交给80端口处理等。
```markdown
http {
    include       mime.types;              # 文件扩展名与文件类型的映射
    default_type  application/octet-stream;   # 默认文件类型
    # 日志文件输出格式
    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';

    #access_log  logs/access.log  main;     # 请求日志的保存位置

    sendfile        on;
    keepalive_timeout  65;         # 连接超时时间

    # 配置负载均衡的服务器列表
    upstream myapp{
      server 192.168.0.1:8001 weight=1;
      server 192.168.0.2:8002 weight=2;
    }
    # 配置监控端口、访问域名
    server {
        listen       80;        # 配置监听端口
        server_name  localhost;  # 配置访问域名

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        location / {
            # proxy_pass   http://127.0.0.1;   # 负载均衡反向代理
            root   html;                       # 默认跟目录
            index  index.html index.htm;       # 默认范围文件
        }

        #error_page  404              /404.html;

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;      # 错误页面及其返回地址
        location = /50x.html {
            root   html;
        }

    }


```