# supervisor对Django后台程序和接口程序进行管理
1. 新建文件supervisor.ini，添加如下配置项
   ```markdown
   [program:autotest_backend]
   command=uwsgi --ini /Users/lidongqiang/DjangoProjects/autotest_backend/uwsgi.ini  ;启动程序
   priority=1               ;字数越高，优先级越高
   numprocs=1               ;启动几个进程
   autostart=true           ;随着supervisord启动而启动
   autorestart=true         ;程序出错时自动重启
   startretries=10          ;启动失败自动重试次数
   redirect_stderr=true     ;重定向stderr到stdout
   stdout_logfile=/Users/lidongqiang/DjangoProjects/autotest_backend/logs/autotest_backend.log
   stderr_logfile=/Users/lidongqiang/DjangoProjects/autotest_backend/logs/autotest_backend.log
   
   [program:autotest_api]
   command=gunicorn -c /Users/lidongqiang/DjangoProjects/autotest_api/gunicorn.conf.py autotest_backend.wsgi:application  ;启动程序
   priority=1               ;字数越高，优先级越高
   numprocs=1               ;启动几个进程
   autostart=true           ;随着supervisord启动而启动
   autorestart=true         ;程序出错时自动重启
   startretries=10          ;启动失败自动重试次数
   redirect_stderr=true     ;重定向stderr到stdout
   stdout_logfile=/Users/lidongqiang/DjangoProjects/autotest_backend/logs/autotest_backend.log
   stderr_logfile=/Users/lidongqiang/DjangoProjects/autotest_backend/logs/autotest_backend.log
   ```

2. 将文件上传至主机的/etc/supervisord.d目录下

3. 加载配置文件并重启
   ```markdown
   supervisorctl reload
   ```

4. 通过Web监控界面查看服务的状态
   ```markdown
   Web监控打开方式：将/etc/supervisord.conf的inet_http_server下的注释去掉
   [inet_http_server]         ; inet (TCP) server disabled by default
   port=127.0.0.1:9001        ; ip_address:port specifier, *:port for all iface
   username=user              ; default is no username (open server)
   password=123               ; default is no password (open server)
   ```

