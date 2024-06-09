# Django 中编写和运行测试用例

## 编写测试用例

每个app都有一个tests.py文件，编辑如下代码
```Python
from django.test import TestCase
from apps.goods.models import Goods


class TestGoodModel(TestCase):

    def setUp(self):
        self.good = Goods.objects.create(name='大枣', market_price=99, price=89, category_id=1)

    def test_goodmodel(self):
        good = Goods.objects.get(id=self.good.id)
        self.assertEquals(good.price, 89)

    def tearDown(self):
        pass
```

## 执行测试用例

```
python3 manage.py test
python3 manage.py test goods
```
