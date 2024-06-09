# Nginx负载均衡部署系统接口
## 负载均衡

负载均衡是一种将任务分派到多个服务器进程的方法，具有以下功能

* 转发：可以按照一定负载均衡策略，将用户请求转发到不同的应用服务器上，减轻单个服务器压力，提高系统的并发性
* 故障转移：通过心跳方式判断当前应用服务器是否正常在工作，如果宕机了，则自动剔除该服务器，并将用户请求发送到其他应用服务器。在故障服务器恢复后，会自动将其启用

## 在Nginx中使用负载均衡
1. 轮询：默认方式。每个请求按照时间顺序被逐一分配到不同的后端服务器，如果后端服务器宕机，则将其自动剔除
2. 权重（weight）：默认为1.服务器权重越高，则被分配的客户端越多，处理的请求就越多，权重越大，责任越大 
配置如下：
```markdown
upstream myserver {
  server 192.168.77.101.8001 weight =1;
  server 192.168.77.102.8001 weight =5;
}
（3）ip_hash算法：每个人请求按照访问IP地址的hash结果进行分配，每个人请求固定访问一个应用服务器，可以解决session共享问题。
upstream myserver {
  ip_hash;
  server 192.168.77.101.8001;
  server 192.168.77.102.8001;
  server 192.168.77.104.8001;
}
```

## 具体配置
nginx.conf 增加如下内容
```markdown
...
    # 负载均衡服务器列表
    upstream myapi{
        server 192.168.77.101:8002 weight=5;
        server 192.168.77.102:8002 weight=3;
        server 192.168.77.103:8002 weight=2;
    }

    # 接口自动化API接口———配置负载均衡
    server {
        listen 84;    # 监听82端口
        listen [::]:84;
        index index.html index.htm index.nginx-debian.html;
        server_name _;
        location / {
            add_header 'Access-Control-Allow-Credentials' 'true';
            add_header 'Access-Control-Allow-Methods' 'GET, POST, OPTIONS';
            add_header 'Access-Control-Allow-Headers' 'content-type,token,id,Content-Type,XFILENAME,XFILECATEGORY,XFILESIZE,XFILESIZE,Origin,X-Requested-With,Accept,content-Type,Authorization';
            proxy_pass http://myapi;     # 转发到负载均衡服务器列表
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
            alias /Users/lidongqiang/DjangoProjects/autotest_backend/static;
        }
        location /media
        {
            alias /Users/lidongqiang/DjangoProjects/autotest_backend/media;
        }
    }
...

```
