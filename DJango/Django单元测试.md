# Django单元测试

## 单元测试-测试用例基类层次

* `SimplTestCase`: 可以发起 HTTP 请求，跟页面， 模板，URL 交互，禁止了数据库的访问；
* `TransactionTestCase`：在用例运行之后，清理 所有表来重置数据库; 可以运行提交、回滚 来观 察中间状态（需要测试事务时使用）
* `TestCase`: 测试用例执行完后不清理表数据； 在 一个事务中执行用例，最后自动回滚事务。
* `LiveServerTestCase`: 在后台自动启动一个 Server，以便使用外部工具如 Selenium 做测试
    
## 一个简单的测试用例
```python
from django.test import TestCase
from django.test import Client

from jobs.models import Job, JobTypes, Cities


class JobTests(TestCase):
    @classmethod
    def setUpTestData(cls):
        cls.job = Job.objects.create(job_name="Java开发工程师", job_type=JobTypes[0][0], job_city=Cities[1][0], job_requirement="精通Java开发")

    def test1(self):
        pass

    def test_index(self):
        client = Client()
        response = client.get('/joblist/')
        self.assertEqual(response.status_code, 200)

    def test_detail(self):
        response = self.client.get('/job/')
        self.assertEqual(response.status_code, 200)

        job = response.context['job']
        self.assertEqual(job.job_name, JobTests.job.job_name)
```


            
## 单元测试-目录结构组织

哪些逻辑需要测试？

* Django 自带的代码（框架中实现的）逻辑不需要测试
* 自己写的代码需要测试，比如自定义的页面的访问，自定义的功能菜单

测试用例目录组织

Django 使用 unittest 模块的内置测试查找机制，
它将在当前工作目录下， 查找任何匹配模式test*.py 命名的文件作为 Test Case。
    
## 执行测试用例

```Shell
# 运行项目下面所有 test
$python manage.py test
# 测试指定模块
$python manage.py test jobs.testcase
# 测试单个模块中的文件
$python manage.py test jobs.testcase.test_views
# 指定类
$python manage.py test jobs.testcase.test_views.JobTests
# 测试指定方法
$python manage.py test jobs.testcase.test_views.JobTests.test_detail
```