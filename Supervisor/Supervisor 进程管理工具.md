# Supervisor 进程管理工具
supervisor是一个进程管理工具，使用 python 开发的一个 C/S 服务，是 Linux/Unix 系统下的一个进程管理工具，可以很方便的监听、启动、停止、重启一个或多个进程。用 supervisor 管理的进程，当一个进程意外被杀死，supervisor监听到进程死后，会自动将其重启启动，这样我们就很方便的实现了进程的自动恢复功能。

## Supervisor 的特点
1. 简单
Supervisor 通过配置一个简单易学的ini风格的配置文件进行配置。他提供了许多单进程的选项，使得自动重启失败的进程和自动日志轮换变革更加轻松。

2. 集中化
Supervisor 提供了一个统一的地方来启动、停止和监控你的进程。进程可以单独控制，也可以分组控制。通过配置 Supervisor，你可以使用本地命令、远程命令或者可视化界面来管理你的进程。

3. 高效
Supervisor 通过 fork/exec 来启动子进程。子进程没有守护进程，所以当进程终止时，操作系统会立即向 Supervisor 发出信号，这与某些依赖麻烦的 PID 文件和定期轮询来重新启动失败进程的解决方案有所不同。

4. 可扩展
Supervisor 有一个简单的事件通知协议，用任何语言编写的程序都可以用来监视它，它还有一个用于控制的 XML-RPC 接口，并且也留了一个 python 切点，python 开发人员可以很容易的对他进行扩展。

5. 兼容性
Supervisor 适用于除了 windows 之外的任何系统，比如：Linux, Mac OS X, Solaris, 和 FreeBSD。它完全用 Python 编写，因此安装不需要 C 编译器。

6. 稳定性
虽然目前 Supervisor 的开发非常活跃，但它并不是新软件。Supervisor 已经存在多年，并且已经在许多服务器上使用。

## Supervisor 组件
1. supervisord

   supervisord 是 Supervisor 服务名。它负责启动子进程，响应来自客户端的命令，重启崩溃或者已退出的子进程，记录子进程标准输出和错误输出，以及生成和处理与子进程生命周期中对应的事件。配置文件一般位于/etc/supervisord.conf中。

2. supervisorctl

   supervisorctl 是 Supervisor 提供的命令行客户端，它为 supervisord 提供了一个类似于 shell 的接口。通过 supervisorctl，用户可以连接到不同的 supervisord 进程、获取supervisord 控制的子进程的状态、停止和启动子进程以及获取正在运行的 supervisord 进程的列表。命令行客户端通过 UNIX 套接字或 Internet (TCP) 套接字与服务器进行通信。

3. Web Server

   当我们配置了inet_http_server之后，就可以通过浏览器访问http://localhost:9001/查看和控制进程的状态。

4. XML-RPC 接口

   为 Web UI 提供服务的同一 HTTP 服务器提供 XML-RPC 接口，可用于询问和控制管理程序及其运行的程序。请参阅 XML-RPC API 文档。

## Supervisor 安装
1. CentOS
   ```markdown
   yum install supervisor
   ```
2. Debian/Ubuntu
   ```markdown
   apt-get install supervisor
   ```

3. 查看版本
   ```markdown
   supervisord --version
   ```
## 配置文件
supervisor 配置文件/etc/supervisord.conf

子进程配置文件路径：/etc/supervisord.d/

1. supervisord.conf
   ```markdown
   [unix_http_server]
   file=/tmp/supervisor.sock   ;UNIX socket 文件，supervisorctl 会使用
   ;chmod=0700                 ;socket文件的mode，默认是0700
   ;chown=nobody:nogroup       ;socket文件的owner，格式：uid:gid
   
   ;[inet_http_server]         ;HTTP服务器，提供web管理界面
   ;port=127.0.0.1:9001        ;Web管理后台运行的IP和端口，如果开放到公网，需要注意安全性
   ;username=user              ;登录管理后台的用户名
   ;password=123               ;登录管理后台的密码
   
   [supervisord]
   logfile=/tmp/supervisord.log ;日志文件，默认是 $CWD/supervisord.log
   logfile_maxbytes=50MB        ;日志文件大小，超出会rotate，默认 50MB，如果设成0，表示不限制大小
   logfile_backups=10           ;日志文件保留备份数量默认10，设为0表示不备份
   loglevel=info                ;日志级别，默认info，其它: debug,warn,trace
   pidfile=/tmp/supervisord.pid ;pid 文件
   nodaemon=false               ;是否在前台启动，默认是false，即以 daemon 的方式启动
   minfds=1024                  ;可以打开的文件描述符的最小值，默认 1024
   minprocs=200                 ;可以打开的进程数的最小值，默认 200
   
   [supervisorctl]
   serverurl=unix:/
   ;serverurl=http://127.0.0.1:9001 ; 通过HTTP的方式连接supervisord
   
   ; [program:xx]是被管理的进程配置参数，xx是进程的名称
   [program:xx]
   command=/opt/apache-tomcat-8.0.35/bin/catalina.sh run  ; 程序启动命令
   autostart=true       ; 在supervisord启动的时候也自动启动
   startsecs=10         ; 启动10秒后没有异常退出，就表示进程正常启动了，默认为1秒
   autorestart=true     ; 程序退出后自动重启,可选值：[unexpected,true,false]，默认为unexpected，表示进程意外杀死后才重启
   startretries=3       ; 启动失败自动重试次数，默认是3
   user=tomcat          ; 用哪个用户启动进程，默认是root
   priority=999         ; 进程启动优先级，默认999，值小的优先启动
   redirect_stderr=true ; 把stderr重定向到stdout，默认false
   stdout_logfile_maxbytes=20MB  ; stdout 日志文件大小，默认50MB
   stdout_logfile_backups = 20   ; stdout 日志文件备份数，默认是10
   ; stdout 日志文件，需要注意当指定目录不存在时无法正常启动，所以需要手动创建目录（supervisord 会自动创建日志文件）
   stdout_logfile=/opt/apache-tomcat-8.0.35/logs/catalina.out
   stopasgroup=false     ;默认为false,进程被杀死时，是否向这个进程组发送stop信号，包括子进程
   killasgroup=false     ;默认为false，向进程组发送kill信号，包括子进程
   
   ;包含其它配置文件
   [include]
   files = relative/directory
   ```

2. 子进程配置文件

   给需要管理的子进程编写一个配置文件，放在/etc/supervisor.d/目录下，以.ini作为扩展名，比如：
   ```markdown
   [program:demo]
   directory = /home/casey/software
   command = /home/casey/software/demo
   autostart = true
   autorestart = false
   stderr_logfile = /tmp/demo_stderr.log
   stdout_logfile = /tmp/demo_stdout.log
   ```
## Supervisor 启停
   ```markdown
   # 启动
   service supervisord start
   # 停止
   service supervisord stop
   ```
## 常用命令
```markdown
# 读取有更新（增加）的配置文件，不会启动新添加的程序 
$supervisorctl reread
 # 重启配置文件修改过的程序 
$supervisorctl update
 # 查看程序状态 
$supervisorctl status
 # 启动程序 App_name 
$supervisorctl start App_name
 # 关闭程序 App_name 
$supervisorctl stop App_name
 # 重启程序 App_name 
supervisorctl -c /etc/supervisord.conf restart App_name:    
supervisorctl -c /etc/supervisord.conf start App_name:App_name_01
#supervisord **重载配置要用update ，不要用reload!

$supervisorctl restart App_name
以上命令也可以在supervisorctl Shell中执行：
$supervisorctl
supervisor>reread
supervisor> update
supervisor> status
supervisor> start App_name
supervisor> stop App_name
supervisor> restart App_name
```

## 开机自动启停
```markdown
systemctl enable supervisord
验证命令
systemctl is-enable supervisord
```

