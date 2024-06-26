# uWSGI部署Django实战
1. 允许授权主机访问
    ```markdown
    在上线部署是，如果setting.py文件中的ALLOWED_HOSTS项是通配符‘*’，则允许所有的IP地址访问商城系统后台。出于安全考虑，一般只允许列表中的IP地址访问
    修改setting.py文件，将ALLOWED_HOSTS=[]修改为ALLOWED_HOSTS = ['允许访问的IP1', '允许访问的IP2']
    ```
2. 处理静态文件
    ```markdown
    使用如下命令把各个包中的静态文件收集到在setting.py文件定义的STATIC_ROOT目录中
    python manage.py collectstatic
    ```

3. 关闭调试模式,将setting.py文件中的DEBUG修改为False
    ```markdown
    DEBUG = False
    ```

4. 安装相关的包
    ```markdown
    pip freeze > requirements.txt
    ```

5. 在服务器上使用pip install -r requirements.txt下载相关包

    编写uwsgi.ini配置文件,文件内容如下
    ```markdown
    [uwsgi]
    http = 127.0.0.1:8001
    chdir = /Users/lidongqiang/DjangoProjects/autotest_backend
    wsgi-file = /Users/lidongqiang/DjangoProjects/autotest_backend/autotest_backend/wsgi.py
    uid = root
    gid = root
    master = true
    processes = 4
    buffer-size = 65535
    vacuum = true
    pidfile = /Users/lidongqiang/DjangoProjects/autotest_backend/uwsgi.pid
    daemonize = /Users/lidongqiang/DjangoProjects/autotest_backend/uwsgi.log
    ```

6. 启动服务并测试
    ```markdown
    uwsgi --ini uwsgi.ini
    ```
    curl http://localhost:8001/admin/ 进行测试

7. 打开防火墙，开通端口
    ```markdown
    firewall-cmd --zone=public --add-port=8001/tcp --permanent
    firewall-cmd --reload
    ```

