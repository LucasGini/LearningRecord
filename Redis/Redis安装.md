# Redis安装
1. 临时服务安装  
   * cmd敲命令进入Redis安装文件下，启动临时服务：redis-server.exe  redis.windows.conf，如果出现一个方形图标，安装临时服务成功。

   * 备注：通过以上面命令，会创建Redis临时服务，不会在window Service列表出现Redis服务名称和状态，此窗口关闭，服务会自动关闭。可以装一个Redis Management测试一下，当关闭这个临时服务是数据库连不上的。

   * 现在进行客户端调用，因为只有临时服务，所以不要关闭上面窗口，我们打开文件夹下redis-cli.exe执行文件，进入客户端Dos窗口，敲入Set uid 1 返回OK ，表示写入内存中；我们再敲 Get uid，会返回一个Value值1，如下图：

2. 默认服务安装

   * 我们不可能每次要用Redis都去开一下临时服务，可不可以跟其它服务一样能够开机自启？当然是可以得，但是有点区别，后续会讲。进入Redis安装包文件下，敲入命令注册服务：redis-server.exe --service-install redis.windows.conf --loglevel verbose（一定要把临时服务关闭，否则安装不上）。

   * 根据英文提示显然服务已经安装了，在window Service列表中能看到，但是没启动，也无法手动启动，只有敲命令启动/暂停/卸载服务：redis-server.exe --service-start；redis-server.exe --service-stop；redis-server.exe --service-uninstall。

3. 自定义服务安装  
   * 所谓自定义服务安装，就是将服务重命名。进入Redis安装包文件下，注册服务：redis-server.exe --service-install redis.windows.conf --Service-name RedisServer1 --loglevel verbose

   * 备注：通过以上面命令，会在window  Service列表出现"redisserver1"服务，但此服务不是启动状态，需要调下面命令启动服务。

   * 与默认安装一样，唯一不同的就是在相应安装服务、启动、关闭、卸载服务时需要加上自定义Redis服务名：redis-server.exe --service-start --Service-name RedisServer1；redis-server.exe --service-stop --Service-name RedisServer1； redis-server.exe --service-uninstall --Service-name RedisServer1通过命令行卸载自定义服务后，电脑重启一下，卸载服务会全部完成。

4. Redis主从服务安装  
   * 主从服务可以实现负载均衡，其实就是把上面Redis安装文件包，拷贝到相应目录，修改主、从服务器配置文件中IP、Port，同时从服务器要指定主服务器 的IP、Port，按照Redis自定义服务安装中命令进行服务安装、服务启动、服务关闭、服务卸载即可使用。

   * 我本地主从服务器安装包都还是在D:\redis-win目录，文件夹Redis-x64-3.2.100为主服务，文件夹Redis-x64-3.2.100-2则是从服务，用的都是本地IP:127.0.0.1，生产环境大家可以根据自己实际情况进行设置。

   * 主服务器（RedisServer1）redis.windows.conf修改如下：port  6379(默认)，不需要作修改；

   * 从服务器（RedisServer2）redis.windows.conf修改如下：port  6380；slaveof  127.0.0.1  6379，修改地方看下图：

   * 我们用RedisManagement分别进行两个端口连接做客户端，在主服务添加键可以同步到从服务，从服务添加无效。如果从服务没有配置从属关系，我们可以在Redis管理器中对从服务进行配置，打开控制台，输入slaveof 127.0.0.1 6379，效果一样。

## 总结

我们安装从服务时候，可以不通过配置文件来安装指定端口和指定主机。redis-server.exe --service-install redis.windows.conf --Service-name RedisServer2 --loglevel verbose --port 6380，这样也是指定端口安装。

运行从服务redis-cli.exe，，这跟RedisManagement一样，也能配从属关系。我之所以没采用，因为我每次运行从服务出不了6380端口（仍是6379），但是我在Redis管理器中是可以连两个端口的服务，故没采用这种方法，网上很多教程说可以，我未实践成功！
