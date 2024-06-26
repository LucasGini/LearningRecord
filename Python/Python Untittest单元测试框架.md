# Python Untittest单元测试框架
## 认识单元测试框架unittest
1. 测试用例（Test Case）
   * 测试用例是unittest中执行测试的最小单元，unittest提供了一个名为TestCase的基础类。可以通过该类来创建测试用例
   * TestCase常用方法：
      * setUp()：在测试方法运行前执行，进行测试前的初始化工作
      * tearDown()：在测试方法运行结束后执行，进行测试后的清理工作
      * setUpClass()：必须使用@classmethod装饰器，在所有用例运行之前运行一次
      * TearDown()：必须使用@classmethod装饰器，在所有用例运行之后运行一次

2. 测试套件（Test Suite）

   测试套件师指一组测试用例，它将测试用例集合在一起，执行一个测试套件，相当于执行当前组内所有的测试用例

3. 测试运行（Test Runner）

   测试运行师指执行测试用例，并将测试结果保存。在结果中包括运行了多少测试用例、成功了多少、失败了多少等信息

4. 测试固件（Test Fixture）

   测试固件可以被简单理解为，在测试之前或者测试之后固定要做的一些动作，比如在测试前的连接数据库、打开浏览器操作等操作；在测试后，关闭数据量，清理文件等操作

## 使用unittest进行单元测试
1. 创建被测试类和方法
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
2. 创建测试类
   ```Python
   import unittest
   from calc import Calc
   
   
   class CalcTestMethod(unittest.TestCase):
   
       def setUp(self):
           self.filename = 'TestCase.log'
           self.file = open(self.filename, mode='a', encoding='utf-8')
           self.file.writelines('在每个测试用例执行之前都会调用' + '\n')
   
       # 测试函数必须以test开头
       def test_add(self):
           result = Calc(2, 3).add()
           try:
               self.assertEquals(result, 4)
           except AssertionError as e:
               self.file.writelines(f'测试异常为{e}' + '\n')
           else:
               self.file.writelines(f'测试通过' + '\n')
   
       def test_sub(self):
           result = Calc(2, 3).sub()
           try:
               self.assertEquals(result, -1)
           except AssertionError as e:
               self.file.writelines(f'测试异常为{e}' + '\n')
           else:
               self.file.writelines('测试通过' + '\n')
   
       def tearDown(self):
           self.file.writelines('测试用例执行结束' + '\n')
           self.file.close()  # 关闭文件
   
   ```
3. 运行测试
   ```Python
   if __name__ == '__main__':
       unittest.main()
   ```

4. 断言

   unittest框架提供了很多断言方法，目的是检查测试的结果是否达到预期，并在断言失败后抛出失败的原因

   常见断言方法：
      * assertEqual(a, b)： a == b 为True，断言 a 和 b 是否相等，如果相等则测试用例通过
      * assertNotEqual(a, b)： a ！= b 为True，断言 a 和 b 是否相等，如果不相等则测试用例通过
      * assertTrue(x)：bool(x) is True, 断言x是否为True，如果为True则测试用例通过
      * assertFalse(x)：bool(x) is False, 断言x是否为False，如果为False则测试用例通过
      * assertIs(a, b)： a is b 为True，断言 a 是否为 b ，如果是则测试用例通过
      * assertNotIs(a, b)： a not is b 为True，断言 a 是否为 b ，如果不是则测试用例通过


## 使用HTMLTestRunner生成HTML报告
1. 创建TestSuite
   ```Python
   import unittest
   from unittest_test.calc_testcase import CalcTestMethod
   
   # 创建测试套件
   suit = unittest.TestSuite()
   # 加载所有测试类
   suit.addTest(unittest.TestLoader().loadTestsFromTestCase(CalcTestMethod))
   ```

2. 使用HTML方式输出
   ```Python
   from unittest_test import HTMLTestRunnerNew
   from unittest_test.calc_testsuits import suit
   
   with open('resport.html', 'wb') as fb:
       test_run = HTMLTestRunnerNew.HTMLTestRunner(stream=fb, verbosity=2, title='测试报告', description='...', tester='lee')
       test_run.run(suit)
   ```

3. 最终会生成repor.html文件
