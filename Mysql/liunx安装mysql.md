# liunx安装mysql

## 安装 MySQL 的准备工作
1. 查看系统版本
   ```markdown
   cat /etc/redhat-release
   ```

2. 查看系统是否已经安装过 MySQL
   查看是否安装了 MySQL
   ```markdown
   rpm -qa | grep mysql
   ```
   查看是否有安装 mariadb，该软件与 MySQL 数据库有冲突，需要手动卸载

   如果是 CentOS7 可以检测出已经安装了 mariadb
   ```markdown
   rpm -qa | grep mariadb
   ```
3. 移除 MySQL 相关的软件

   如果系统中没有安装过与 MySQL 相关的软件，直接跳到下一步
   ```markdown
   rpm -e --nodeps mariadb-libs-5.5.68-1.el7.x86_64
   ```
## 安装 MySQL
官网传送门

1. 找到压缩包的下载链接

   点击 MySQL Community Server，

   选择 MySQL 的版本，

   注意 MySQL 的版本需要与 Linux 的版本对应上，
   
   centos7 系统对应的 MySQL 是 el7，
   
   centos8 系统对应的 MySQL 是 el8，
   
   一般情况下，直接选择第一个安装包即可，第一个安装包的版本会不定时的更新，与本文所用的安装包有略微的差别，但是并不影响具体的使用，
   
   截止 2023-5-11 最新版为 mysql-8.0.33-1.el8.x86_64.rpm-bundle.tar，

2. 下载压缩包

   此时有两种方法：

      * 第一种，直接点击 No thanks, just start my download 进行下载，然后通过上传工具上传到服务器
      * 第二种，右键 No thanks, just start my download，选择复制链接，然后在服务器中通过 wget 命令下载到服务器

   ```markdown
   cd /usr/local
   wget https://dev.mysql.com/get/Downloads/MySQL-8.0/mysql-8.0.31-1.el8.x86_64.rpm-bundle.tar
   ```

3. 查看下载好的压缩包
   ```markdown
   ll
   ```

4. 解压

   解压之后会有很多包产生，为了方便查看，将其解压到 /usr/local/mysql_package 里面
   
   ```markdown
   mkdir /usr/local/mysql_package
   
   cd /usr/local/mysql_package
   
   tar -xvf /usr/local/mysql-8.0.31-1.el8.x86_64.rpm-bundle.tar -C /usr/local/mysql_package
   ```

5. 使用 rpm 安装

   必须按照顺序执行命令，否则会出现依赖错误的报错，如果安装过程中出现错误，可以参考下一步给出的总结

   ```markdown
   rpm -ivh mysql-community-common-8.0.31-1.el8.x86_64.rpm
   
   rpm -ivh mysql-community-client-plugins-8.0.31-1.el8.x86_64.rpm
   
   rpm -ivh mysql-community-libs-8.0.31-1.el8.x86_64.rpm
   
   rpm -ivh mysql-community-client-8.0.31-1.el8.x86_64.rpm
   
   rpm -ivh mysql-community-icu-data-files-8.0.31-1.el8.x86_64.rpm
   
   rpm -ivh mysql-community-devel-8.0.31-1.el8.x86_64.rpm
   
   rpm -ivh mysql-community-server-8.0.31-1.el8.x86_64.rpm
   ```

6. 常见错误总结

   1. 未按照规定顺序安装软件 
   
      按照顺序安装即可解决

   2. libc.so.6 缺失
      大概率是因为下载的 MySQL版本与 Linux 版本对应不上，可以检查一下 MySQL 压缩包后缀名里面的参数是否与系统版本匹配

      centos7 系统对应的 MySQL 是 el7

      centos8 系统对应的 MySQL 是 el8

   3. openssl 缺失

      安装 openssl-devel 即可解决
      ```markdown
      rpm -ivh mysql-community-devel-8.0.31-1.el8.x86_64.rpm
      ```
      使用 yum install openssl-devel -y 安装 openssl-devel
      ```markdown
      yum install openssl-devel -y
      ```
      再次安装该软件包即可 
      ```markdown
      rpm -ivh mysql-community-devel-8.0.31-1.el8.x86_64.rpm
      ```

   4. perl 和 libaio 缺失
      安装 prel 和 libaio 即可解决
   
      查看与 perl 相关的软件
      ```markdown
      yum list perl
      ```
      安装
      ```markdown
      yum install -y perl.x86_64
      ```
      查看与 libaio 相关的软件
      ```markdown
      yum list libaio
      ```
      安装
      ```markdown
      yum install -y libaio*
      ```

7. 查看已安装的 MySQL 的版本
   ```markdown
   mysql -V
   mysql --version
   ```

8. 删除无用的包
   ```markdown
   rm -rf /usr/local/mysql-8.0.31-1.el8.x86_64.rpm-bundle.tar
   rm -rf /usr/local/mysql_package/
   ```

## 配置 MySQL 服务
1. 简单配置
   ```markdown
   查看 MySQL 服务状态
   
   systemctl status mysqld
   
   开启 MySQL 服务
   
   systemctl start mysqld
   
   设置 MySQL 服务开机自启
   
   systemctl enable mysqld
   
   重启 MySQL 服务
   
   systemctl restart mysqld
   
   再次查看 MySQL 服务状态，可以看到 MySQL 服务已经成功运行
   
   systemctl status mysqld
   ```

2. 命令解析
   ```markdown
   （1）查看 MySQL 服务状态
   systemctl status mysqld
   
   （2）暂时关闭 MySQL 服务
   服务器重新启动之后，MySQL 服务会再次启动
   
   systemctl stop mysqld
   
   （3）永久关闭 MySQL 服务
   服务器重新启动之后，MySQL 服务也不会再次启动
   
   systemctl disable mysqld
   
   （4）开启 MySQL 服务
   systemctl start mysqld
   
   （5）设置 MySQL 服务开机自启
   systemctl enable mysqld
   
   （6）重启 MySQL 服务
   systemctl restart mysqld
   ```

## 简单的使用 MySQL
1. 获取 root 用户的初始密码

   第一次运行 MySQL 服务时，会进行初始化加载，同时会生成一个 root 用户的初始密码，可以通过查看日志文件 /var/log/mysqld.log 获取到 root 用户的初始密码，后续可手动修改密码
   ```markdown
   cat /var/log/mysqld.log | grep 'password'
   ```

2. 使用 root 用户登录到 MySQL 服务
   ```markdown
   mysql -u root -p
   ```
   
3. 修改 root 的密码
   ```markdown
   （1）修改初始化密码
   alter user 'root'@'localhost' identified by 'BuGu123456!';
   
   
   
   （2）修改 MySQL 校验密码的安全策略【可选，一般不建议修改】
   # 设置密码长度的最低位数
   set global validate_password.length=4;
   
   
   # 设置密码的安全等级，修改密码安全策略为低（只校验密码长度，至少8位）
   set global validate_password.policy=LOW;
   ```

4. 测试是否正常工作
   ```markdown
   show databases;
   ```

5. 退出 MySQL 环境
   ```markdown
   exit;
   ```

## 远程链接 MySQL
1. 第一次远程连接测试

   提示无法连接到主机，这是因为 3306 端口未开放

   如果是云服务器，防火墙的端口、安全组的端口都需要开放
   ```markdown
   （1）开放 3306 端口
   firewall-cmd --zone=public --add-port=3306/tcp --permanent
   
   （2）重新加载服务
   firewall-cmd --reload
   
   （3）查看端口号是否开放
   firewall-cmd --zone=public --list-port
   ```

2. 第二次远程连接测试

   可以看到提示该 root 用户没有远程访问的权限，不被允许远程链接到 MySQL 数据库
   ```markdown
   （1）切换到 MySQL 数据库
   mysql -u root -p
   
   use mysql;
   
   （2）查看用户的访问权限
   user 表中的 host 字段表示用户的访问权限
   
   localhost：只可以本地访问
   %：允许任意地方访问
   select host, user, plugin from user;
   
   注意：此时 root 用户并没有被授权远程访问 MySQL 服务，下图中的 host 字段应该为 localhost，而不是 %，由于我的失误，下面这张图片并不是该步骤对应的图片，在此展示的图片中，root 用户的 host 字段应为 localhost
   
   
   
   （3）设置 root 用户任意地方可以访问
   update user set host='%' where user='root';
   
   （4）刷新权限
   flush privileges;
   
   （5）再次查看
   select host, user, plugin from user;
   
   经过修改，root 用户的 host 字段已经成功被修改为 %，表示 root 用户可以远程访问 MySQL 服务
   ```

3. 第三次远程连接测试

   可以看到提示密码校验方式不被允许远程链接到 MySQL 数据库

   caching_sha2_password 加密方式在远程访问时候不支持，需要修改为 mysql_native_password

   ```markdown
   （1）修改密码策略
   注意、注意、注意！！！
   
   修改密码策略的同时，将当前 root 用户的密码的验证策略也修改一下，否则当前用户的密码会失效，使得 root 用户无法使用原密码登录 MySQL 服务，目前我知道的解决办法就是重新安装 MySQL
   
   mysql -u root -p
   
   alter user 'root'@'%' identified with mysql_native_password by 'BuGu123456!';
   
   （2）刷新权限
   flush privileges;
   ```

4. 第四次远程连接测试

   连接成功



## 数据的导入和导出
1. 导入数据到 MySQL 数据库
   ```markdown
   进入 MySQL 服务
   
   mysql -uroot -p
   
   创建新的数据库
   
   create database test;
   
   连接到目标数据库
   
   use test;
   
   运行指定路径的 SQL 文件
   
   source /root/test.sql;
   
   测试
   
   select * from user;
   ```

2. 将 MySQL 数据库的数据导出

   返回 Linux 界面，将名为 test的数据库备份到指路径的 test1.sql 文件中
   ```
   mysqldump -uroot -p test > /root/test1.sql
   ```
   完成后，使用 ll 命令可以查看到导出的 SQL 文件，也即完成备份
   ```
   ll
   ```


