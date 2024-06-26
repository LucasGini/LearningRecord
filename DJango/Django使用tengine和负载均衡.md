# django使用tengine和负载均衡

https://tengine.taobao.org/
* Tengine完全兼容Nginx, 同时提供了额外的强大功能
* 增强相关运维、监控能力,比如异步打印日志及回滚,本地DNS缓存,内存监控等；
* 动态脚本语言Lua支持。扩展功能简单高效；
* 更加强大的负载均衡能力，包括一致性hash模块、会话保持模块
* 主动健康检查，根据服务器状态自动上线下线，以及动态解析upstream中出现的域名；
* 输入过滤器机制支持，更强大的防攻击（访问速度限制）模块；方便实现应用防火墙；* 
...

## 安装Tengine

安装依赖 pcre

```shell
wget https://ftp.pcre.org/pub/pcre/pcre-8.44.tar.gz

./configure --prefix=/usr/local/pcre && make && make install
```

指定pcre源码目录安装tengine
```shell
wget https://tengine.taobao.org/download/tengine-2.3.2.tar.gz

tar zxvf tengine-2.3.2.tar.gz && cd /data/tengine-2.3.0

./configure --prefix=/data/tengine/ --with-http_realip_module -- with-http_gzip_static_module --with-pcre=/data/pcre-8.44

make && make install
```

## 最简单的配置：路由转发请求到 Gunicorn/uWSGI 服务

```
server {
    listen: 80;
    server_name recruit.mycompany.com;

    location / {
        proxy_pass https://127.0.0.1:8000;
        proxy_set_header    Host            $http_host;
        proxy_set_header    X-Real-IP       $http_host;
        # 包含了客户端地址，以及各级代理IP的完整IP链
        proxy_set_header    X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}
```

## 使用Tengine/Nginx 负载均衡
* 请求路由到两台后端服务器
* 两台后端服务器 Upstream
* 服务器运行 Gunicorn/uWSGI
* 单台服务器运行有两个实例
    
```shell
vim /data/tengune/conf.d/recruitment.conf
```
```
upstream django-server {
    # max_fails = 3 为允许失败的次数，默认值为1.对后端节点做健康检查
    # 20s内，当max_fails次失败后，暂停将请求分发到该服务器
    server 127.0.0.1:8081 max_fails=3 fail_timeout=20s;
    server 127.0.0.1:8082 max_fails=3 fail_timeout=20s;
    }
server {
    listen: 80;
    server_name http://django-server;
    access_log /data/tengine/logs/recruitment-access.log main;
    error_log /data/tengine/logs/recruitment-error.log
    
    location / {
        proxy_pass https://127.0.0.1:8000;
        rroxy_redirect off;
        proxy_set_header    Host            $host;
        # 把真实的IP放在HTTP头里面
        proxy_set_header    X-Real-IP       $remote_addr;
        proxy_set_header    REMOTE-HOST      $remote_ad
        # 包含了客户端地址，以及各级代理IP的完整IP链
        proxy_set_header    X-Forwarded-For $proxy_add_x_forwarded_for;'
        proxy_connect_timeout 300;
        # 后端服务器回传时间，就是在规定时间内后端服务器必须传完所有数据
        proxy_send_timeout 300;
        # 链接成功后，等待后端服务器的响应时间，已经进入后端的排队之中等候处理
        proxy_read_timeout 600;
        
        proxy_next_upstream error timeout invalid_header http_500 http_502 http_503 http_504 http_403 http_404;
        }
    }
```

        
## Tengine 的分流策略

1.负载分流策略
* round-robin — 平均分配流量：轮询模式
* least-connected — 最少连接优先，下一个请求分到活跃连接最少的服务器
* ip-hash — 按照客户端 IP 哈希来分配服务器 IP
* 带权重流量分配
* 一致性哈希 （Tengine）
* 会话保持 （Tengine 特性）

(1)最少连接优先

前面配置的为平均分配流量；

按照最少连接优先/ip hash 的配置：
```
upstream django-upstream {
    least_conn; # for ip hash; ip_hash;
    server 127.0.0.1:8081;
    server 127.0.0.1:8082;
    server 127.0.0.2:8081;
    server 127.0.0.2:8082;
    }
```
            
(2)按权重分配

按照权重分配（适合机器配置不一样时）

6个请求里面，3个走到第一台，其它3台没台1个请求。
```
upstream django-upstream {
    server 127.0.0.1:8081 weight=3;
    server 127.0.0.1:8082;
    server 127.0.0.2:8081;
    server 127.0.0.2:8082;
    }
```
            
(3)会话保持

尽可能保证同一个客户端访问的都是同一个后端服务器
```
upstream django-upstream {
    server 127.0.0.1;
    server 127.0.0.2;
    session_sticky;
    }
server {
    location/ {
        proxy_pass http://django-upstream;
        }
    }
```
                
## 健康检查自动容错
1.被动健康检查: ngx_http_upstream_module 实现了被动的健康检查功能

```
upstream django-server {
    # max_fails = 3 为允许失败的次数，默认值为1.对后端节点做健康检查
    # 20s内，当max_fails次失败后，暂停将请求分发到该服务器
    server 127.0.0.1:8081 max_fails=3 fail_timeout=20s;
    server 127.0.0.1:8082 max_fails=3 fail_timeout=20s;
    }
```
            
2.主动健康检查： http_upstream_check_module，主动定时检查

```
upstream django-upstream {
        least_conn; # for ip hash; ip_hash;
        server 127.0.0.1:8081;
        server 127.0.0.1:8082;
        check interval=3000 rise=2 fall=3 timeout=1000 type=http;
        check_http_send "HEAD / HTTP/1.0\r\n\r\n";
        check_http_expect_alive http_2xx http_3xx;

        }
```
指令后面的参数意义：

* `interval`：向后端发送的健康检查包间隔。
* `fall(fall_count)`:如果连续失败次数达到fall_conut，服务器就被认为是down
* `rise(rise_count)`：如果连续成功次数达到rise_count，服务器就被认为是up。
* `timeout`：后端健康请求的超时时间.

上面配置的意思是，对django_upstream的所有节点，每隔3秒检测一次，请求2次

正常则标记realserver状态为up，如果检测3次都是失败，则标记realserver的状态为down。超时时间为1秒
    
        