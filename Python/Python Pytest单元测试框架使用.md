# Python Pytest单元测试框架使用
pytest 是一个非常成熟的、全功能的Python测试框架，主要特点如下：

* 简单灵活，文档丰富
* 支持参数化
* 支持简单的单元测试和复杂的功能测试，如接口自动化测试和用户界面自动化测试
* 支持众多第三方插件，如pytest-html（支持生成HTML格式测试报告）等，还可以自定义功能扩展
* 可以与持续集成工具jenkins完美结合

## 测试用例编写规则
* 测试文件以test开头或者test结尾
* 测试类以Test开头，且不能带有init方法
* 在测试类中可以包含一个或者多个以test开头的方法
* 断言使用基本的assert()方法即可

## 安装
```markdown
pip install pytest
pip install pytest-html
```

## 编写被测试类
```Python
class Calc:

    def __init__(self, a, b):
        self.a = a
        self.b = b

    def add(self):
        return self.a + self.b

    def sub(self):
        return self.a - self.b
```

## 编写测试类
```Python
import pytest
from pytest_test.calc import Calc


class TestCalc():

    def setup_class(self):
        print("在每个类之前执行一次"+ '\n')

    def teardown_class(self):
        print("在每个类之后执行一次"+ '\n')

    def setup_method(self):
        print("在每个方法之前执行一次"+ '\n')

    def teardown_method(self):
        print("在每个方法之后执行一次" + '\n')

    # 测试函数必须以test开头
    def test_add(self):
        c = Calc(2, 3)
        result = c.add()
        assert result == 5

    def test_sub(self):
        c = Calc(2, 3)
        result = c.sub()
        assert result == -2


if __name__ == '__main__':
    pytest.main(['-s', '-v', '--html=/Users/lidongqiang/DjangoProjects/myshop-back/pytest_test/report/report.html',
                 '/Users/lidongqiang/DjangoProjects/myshop-back/pytest_test/calc_test.py'])
```



