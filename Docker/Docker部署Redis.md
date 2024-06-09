# Docker部署Redis

1、拉取redis镜像
```markdown
docker pull redis:6.2.5
```
2、创建数据和配置存放目录
```markdown
# 创建 redis 配置存放目录
mkdir -p /home/docker/redis/conf && chmod 777 /home/docker/redis/conf

# 创建 redis 数据存放目录
mkdir -p /home/docker/redis/data && chmod 777 /home/docker/redis/data
```

3、下载配置文件
```markdown
# 进入 redis 配置文件目录
cd /home/docker/redis/conf

# 下载 redis 示例配置文件
wget http://download.redis.io/redis-stable/redis.conf
```

4、修改配置文件
```markdown
# 设置 redis 连接密码
# 如果为空则不需要密码
requirepass 123456789

# 数据持久化 - 开始
# 开启 AOF 持久化
appendonly yes

# AOF文件刷新的方式
# always 每提交一个修改命令都调用fsync刷新到AOF文件，非常非常慢，但也非常安全。
# everysec 每秒钟都调用fsync刷新到AOF文件，很快，但可能会丢失一秒以内的数据。
# no 依靠OS进行刷新，redis不主动刷新AOF，这样最快，但安全性就差。
appendfsync everysec

# 随着持久化的不断增多，AOF文件会越来越大，这个时候就需要AOF文件重写了。AOF文件重写
# 如果该参数取值为yes，那么在重写AOF文件时能提升性能，但可能在重写AOF文件时丢失数据。
# 如果取值为no，则不会丢失数据，但较取值为yes的性能可能会降低。默认取值是no。
no-appendfsync-on-rewrite no

# AOF文件重写
# 参数能指定重写的条件，默认是100，
# 即如果当前的AOF文件比上次执行重写时的文件大一倍时会再次触发重写操作。
# 如果该参数取值为0，则不会触发重写操作。
auto-aof-rewrite-percentage 100

# AOF文件重写
# 指定触发重写时AOF文件的大小，默认是64MB。
auto-aof-rewrite-min-size 64mb

# auto-aof-rewrite-percentage 和 auto-aof-rewrite-min-size 两个参数指定的重写条件是“And”的关系。
# 即只有当同时满足这两个条件时才会触发重写操作。

# 数据持久化 - 结束

# 绑定redis服务器网卡IP，默认为127.0.0.1,即本地回环地址。
# 这样的话，访问redis服务只能通过本机的客户端连接，而无法通过远程连接。
# 如果bind选项为空的话，那会接受所有来自于可用网络接口的连接。
bind 172.0.0.4 127.0.0.1

# Redis key 过期事件监听
notify-keyspace-events Ex
```


5、启动容器
```markdown
docker run -p 6379:6379 --name redis -v /home/docker/redis/conf/redis.conf:/etc/redis/redis.conf -v /home/docker/redis/data:/data -d redis:6.2.5 redis-server /etc/redis/redis.conf --appendonly yes

```
* -p 6379:6379 把容器的6379端口映射到宿主机的6379端口
* -v /home/docker/redis/conf/redis.conf:/etc/redis/redis.conf 把在宿主机中配置号的redis.conf放到容器相同位置
* -v /home/docker/redis/data:/data  把redis持久化的数据在宿主机中显示，作为数据备份
* redis-server /etc/redis/redis.conf  关键配置，让redis安装redis.conf的配置启动
* --appendonly yes  让redis在启动后做数据持久化

6、Redis 交互终端
```markdown
# 在容器 redis 中开启一个交互模式的终端
docker exec -it redis /bin/bash
```


7、连接redis
```markdown
redis-cli
```
如果设置了密码

auth 密码 即可链接

8、Redis 常用命令
```markdown
# 新增数据
set woniu 'woniu word'
 
# 查询数据
get woniu
 
# 选择数据库
select 1
 
# 所有 key
keys *
 
# 删除 key
del woniu

# 退出
exit
```

