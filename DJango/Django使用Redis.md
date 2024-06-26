# Django使用Redis

## Django缓存的存储方式
* Memcached 缓存
* Redis 缓存（需要安装django-redis包）
* 数据库缓存
* 文件系统缓存
* 本地内存缓存
* 伪缓存（Dummy Cache，用于开发、测试）
* 自定义缓存

## 缓存的策略
* 整站缓存
* 视图缓存
* 模板片段缓存

## 安装
1.安装django-redis

```
pip install django-redis
```
2.本机安装Redis客户端

本机安装并启动Redis服务

参考Redis模块安装

3.django.settings配置

```python
# Redis配置
CACHES = {
    "default": {
        "BACKEND": "django_redis.cache.RedisCache",
        "LOCATION": "redis://127.0.0.1:6379/1",
        "TIMEOUT": 300,
        "OPTIONS": {
            "CLIENT_CLASS": "django_redis.client.DefaultClient",
            "SOCKET_CONNECT_TIMEOUT": 5,
            "SOCKET_TIMEOUT": 5,
        }
    }
}
```


4.django.settings配置整站缓存

```python
MIDDLEWARE = [
    ...
    'django.middleware.cache.UpdateCacheMiddleware',    # 整站缓存
    'django.middleware.common.CommonMiddleware',
    'django.middleware.cache.FetchFromCacheMiddleware',  # 整站缓存
    ...
]
```

