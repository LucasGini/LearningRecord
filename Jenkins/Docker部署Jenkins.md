# Docker部署Jenkins

## 安装Jenkins

1. pull镜像
   
   ```shell
   docker pull jenkins/jenkins:2.402
   ```

2. 创建Jenkins挂载目录并授予权限
   
   在启动Jenkins时，需要先创建一个Jenkins的配置目录，并且挂载到docker 里的Jenkins目录下
   
   ```shell
   //创建目录
   
   mkdir -p /var/jenkins_home
   
   //授权权限
   chmod 777 /var/jenkins_home
   ```

3. 启动Jenkins容器
   
   * `-d` 后台运行镜像
   
   * `-p 10240:8080` 将镜像的8080端口映射到服务器的10240端口。
   
   * `-p 10241:50000` 将镜像的50000端口映射到服务器的10241端口
   
   * `-v /var/jenkins_mount:/var/jenkins_home` /var/jenkins_home目录为容器jenkins工作目录，我们将硬盘上的一个目录挂载到这个位置，方便后续更新镜像后继续使用原来的工作目录。这里我们设置的就是上面我们创建的 /var/jenkins_home目录
   
   * `-v /etc/localtime:/etc/localtime`让容器使用和服务器同样的时间设置。
   
   * `–name myjenkins` 给容器起一个别名
   
   ```shell
   docker run -d -p 10240:8080 -p 10241:50000 -v /var/jenkins_home:/var/jenkins_home -v /etc/localtime:/etc/localtime --name jenkins jenkins/jenkins:2.402
   ```

## 换源

1. 修改/var/jenkins_home/updates/default.json 的内容
   
   ```shell
   sed -i 's#https://updates.jenkins.io/download#https://mirrors.tuna.tsinghua.edu.cn/jenkins#g' ~/docker_data/jenkins/updates/default.json
   sed -i 's#http://www.google.com#https://www.baidu.com#g' ~/docker_data/jenkins/updates/default.json
   ```

2. 重启jenkins 容器
   
   ```shell
   docker restart jenkins
   ```

## 浏览器访问Jenkins页面

1. 输入http://192.168.XX.XX:10240

2. 获取管理员密码
   
   ```shell
   vim /var/jenkins_home/secrets/initialAdminPassword
   ```
   
   获得密码后复制粘贴到浏览器

3. 按步骤下载插件以及注册账号
