# Django 操作Redis

## 安装django-redis

```
pip install django-redis
```

## 配置settings.py文件

```Python
# 缓存配置
CACHES = {
    "default": {
        "BACKEND": "django_redis.cache.RedisCache",
        "LOCATION": "redis://127.0.0.1:6379",
        "OPTIONS": {
            "CLIENT_CLASS": "django_redis.client.DefaultClient",
        }
    }
}
```


3、使用
终端中使用python manage.py shell打开shell模式

>\>>from django.core.cache import cache

>\>>cache.set("mykey", "hello redis", 30)  # 有效期30s

>\>>True

>\>>cache.has_key("mykey")

>\>>True

>\>>cache.get("mykey")

>\>>"hello redis"

timeout=0：立即过期

timeout=None：永不超时

## 用Redis存储session信息

（1）安装django-redis-sessions

```
pip install django-redis-sessions
```
（2）修改配置settings.py文件

```Python
SESSION_ENGINE = 'redis_sessions.session'
SESSION_CACHE_ALIAS = 'default'
```

（3）测试
登录admin后台，django_session表没有新增数据
