# 20行代码实现只读站点ReadOnlyAdmin

## 场景/解决问题

集成遗留的已有系统

已有系统的数据涉及到核心数据

为了确保数据安全，管理后台只提供数据的浏览功能

设置列表页list_display展示所有字段

```python
from django.contrib import admin
from .models import Country, Province, Area, City

# 定义父类继承admin.ModelAdmin
class ReadOnlyAdmin(admin.ModelAdmin):
    readonly_fields = []

    # 返回所有字段
    def get_list_display(self, request):
        return [field.name for field in self.model._meta.concrete_fields]

    # 获取只读字段
    def get_readonly_fields(self, request, obj=None):
        return list(self.readonly_fields) + \
               [field.name for field in obj._meta.fields] + \  # 把obj.meta里面的字段取出来
               [field.name for field in obj._meta.many_to_many] # 把obj.meta里面有外键依赖关系的字段取出来

    # 是否具有新增功能权限返回false
    def has_add_permission(self, request):
        return False

    # 是否具有删除功能权限返回false
    def has_delete_permission(self, request, obj=None):
        return False

    # 是否具有修改功能权限返回false
    def has_change_permission(self, request, obj=None):
        return False

#继承ReadOnlyAdmin
@admin.register(Country)
class CountryAdmin(ReadOnlyAdmin):
    search_fields = ('chn_name', 'eng_name',)


@admin.register(Province)
class ProvinceAdmin(ReadOnlyAdmin):
    search_fields = ('chn_name', 'eng_name',)


@admin.register(City)
class CityAdmin(ReadOnlyAdmin):
    autocomplete_fields = ['provinceid', 'countryid', ]

    list_display = ('cityid', 'countryid', 'areaid', 'provinceid', 'chn_name', 'eng_name')
```
