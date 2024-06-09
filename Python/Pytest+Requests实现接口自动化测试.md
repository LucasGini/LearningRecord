# Pytest+Requests实现接口自动化测试
## Response常用属性方法
response.url：获取请求连接地址
response.text：响应返回的文本
response.content：响应返回的内容，一般用来爬取图片、视频
response.status_code：响应的状态码
response.encoding：响应体编码方式
response.headers：查看响应头
response.cookies：获取返回的cookies信息
response.request：获取请求方式
response.json()：将结果反序列化
response.history：返回以列表存储的请求历史记录

## 编写接口测试用例

```Python
import requests
import pytest

host = 'http://localhost:8000'


class TestIndexCase():

    def test_IndexCategoryList(self):
        url = host + '/goods/goodscategory'
        response = requests.get(url)
        assert response.status_code == 200  # 分类接口状态正常
        assert len(response.content) > 0  # 分类列表不为空
        goods_list = response.json().get('data')
        for goods in goods_list:
            assert len(goods['name']) > 0  # 分类名称不为空

    def test_IndexGoodsList(self):
        url = host + '/goods/good_custom'
        response = requests.get(url)
        assert response.status_code == 200  # 商品接口状态正常
        assert len(response.content) > 0  # 商品列表不为空
        goods_list = response.json().get('data')
        for goods in goods_list:
            assert len(goods['name']) > 0  # 商品名称不为空


if __name__ == '__main__':
    pytest.main(['-s', '-v', 'pytest_test/test_api.py'])
```
