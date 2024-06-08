# 用uWSGI进行部署
1. WSGI、uwsgi、uWSGI的关系

   - WSGI（the Python Web Server Gateway Interface）指python的Web服务的网关接口。从名称可以看出，WGSI是一个网关，网关的作用是在协议之间进行转换。因此，WGSI是一个Web服务器与Django等程序进行通信的规范或者协议
   - uwsgi是一个线路协议，用于实现uWGSI服务器与其他服务软件（如Nginx）的数据通信
   - uWSGI是一个具体的Web服务器，是实现了上述两个协议的Web服务器
   - 三者关系归纳如下， 
     - WSGI：网关、接口 
     - uwsgi：线路协议 
     - uWSGI：一种服务的具体实现

   **客户端<--http协议-->Nginx<--uwsgi线路协议-->uWSGI<--WSGI协议-->Django或Flask**

2. 安装uwsgi软件
    ```markdown
    (1）安装uwsgi
    pip install uwsgi
    
    (2)查看版本
    查看uwsgi版本
    uwsgi --version
    
    查看相关的Python版本
    uwsgi --python-version
    ```
   
3. 启动并测试uwsgi 

   1. 编写一个测试uwsgi的简单Python脚本

       vi test_uwsgi.py 

   2. test_uwsgi.py文件内容如下

       ```Python
       def application(env, start_response):
           start_response('200 OK', [('Content-Type', 'text/html')])
           return [b"Hello Django"]
       ```

   3. 启动uwsgi，在端口9000上开放Web访问

        uwsgi --http :9000 --wsgi-file test_uwsgi.py
   
   4. 测试uwsgi

        curl http://localhost:9000
   
   5. 结果如下

        Hello Django

4. 配置文件

    uwsgi提供了通过配置文件来启动服务的方式。安装uwsgi后，默认没有配置文件，可以创建一个uwsgi.ini文件作为配置文件，文件内容大致如下
    ```markdown
    socket = 127.0.0.1:8000  # socket方式不能直接通过http方式访问，需要配合Nginx使用
    http = 127.0.0.1:8000    # 外部通过HTTP方式访问
    chmod-socket = 666       # 分配socket的权限
    chdir = /var/www/web/    # 项目目录
    master = true            # 是否启动主进程来管理其他进程
    max_requests = 5000      # 设置每个工作进程的请求上限
    processes = 5            # 进程个数
    threads = 2              # 设置每个工作进程的线程数
    pidfile = /web/uwsgi.pid # 指定pid文件，在该文件内包含uwsgi的进程号
    daemonize= /web/uwsgi.log# 进程后台执行，并保存日志到特定路径；如果uwsgi进程被supervisor管理，则不能设置该参数
    wsgi-file                # 加载wsgi-file文件路径
    module                   # 加载wsgi模块
    home                     # 虚拟环境目录
    harakiri=300             # 请求的超时时间
    vacuum = true            # 在服务结束后删除对应的socket和pid文件
    buffer_size = 65535      # 设置用于uwsgi包解析内部缓存区大小，默认4kb
    ```
   
5. 常用命令

    通过uwsgi.ini配置文件启动uwsgi服务后，会在指定目录下生成uwsgi.pid文件。uwsgi.pid文件用来重启和停止uwsgi服务，相关命令如下
        
    启动： uwsgi --ini /路径/uwsgi.ini

    重启： uwsgi --reload /路径/uwsgi.pid

    停止： uwsgi --stop /路径/uwsgi.pid

