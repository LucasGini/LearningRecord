# 安装kafka

## 通过docker安装
1. 配置docker-compose.yml文件
   ```
     zookeeper:
       image: wurstmeister/zookeeper
       ports:
         - "2181:2181"

     kafka:
       image: wurstmeister/kafka
       ports:
         - "9092:9092"
       environment:
         KAFKA_ADVERTISED_HOST_NAME: 127.0.0.1
         KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
       volumes:
         - /var/run/docker.sock:/var/run/docker.sock
   ```

2. 使用docker-compose命令启动容器（[docker-compose命令安装](../Docker/搭建Harbor私有仓库.md)）
   > docker-compose up
   
