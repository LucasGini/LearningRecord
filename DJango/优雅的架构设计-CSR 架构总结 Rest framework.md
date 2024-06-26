# 优雅的架构设计-CSR 架构总结 Rest framework

解决的问题： 为应用提供Restful API

## DRF 架构之美
* 简单易用，既可以使用自动的 CRUD API，也可以自定义实现API
* 提供可浏览的 HTML API; 一套实现同时提供 HTML/JSON/XML 展现
* 灵活的用户认证，支持 Token/OAuth/OAuth2/JWT 等认证方式
* 提供流量控制，结果过滤筛选，分页，API 版本控制能力
* 灵活的权限控制：登陆用户，管理员，Django内置权限，只读权限，匿名用户
    
4 行代码定义一个 model 的API

```python
class UserSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = User
        fields = ['url', 'username', 'email', 'is_staff']


class UserViewSet(viewsets.ModelViewSet):
    queryset = User.objects.all()
    serializer_class = UserSerializer
```
