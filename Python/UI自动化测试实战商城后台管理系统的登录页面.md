```Python
import random
import time
import unittest
import os
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.select import Select
from selenium.webdriver.common.keys import Keys


class TestPage(unittest.TestCase):

    def setUp(self):  # 执行一次，设置浏览器

        self.driver = webdriver.Chrome()

    def test_goodscategory_add(self):
        # 访问商城后台
        self.driver.get('http://127.0.0.1:8000/admin')
        time.sleep(5)  # 暂停5秒，使得页面加载完成
        try:
            # 获取用户名输入框
            self.driver.find_element(By.NAME, 'username').send_keys('lidongqiang')
            # 获取密码输入框
            self.driver.find_element(By.NAME, 'password').send_keys('123456')
            # 获取登录按钮
            login = self.driver.find_element(By.XPATH, '//*[@id="login-form"]/div[3]/input')
            login.click()  # 点击登录按钮
            time.sleep(2)
            assert '商城平台管理' in self.driver.page_source
            time.sleep(2)
            # 单击商品分类中增加的连接
            add = self.driver.find_element(By.XPATH, '//*[@id="content-main"]/div[1]/table/tbody/tr[2]/td[1]/a')
            add.click()
            time.sleep(2)
            assert '增加 商品分类' in self.driver.page_source
            # 输入分类名称
            self.driver.find_element(By.NAME, 'name').send_keys('新疆特级大枣')
            # 选择父类
            select_element = Select(self.driver.find_element(By.XPATH, '//*[@id="id_parent"]'))
            select_element.select_by_visible_text('枣类')
            print(select_element.all_selected_options[0].text)
            self.assertEquals(select_element.all_selected_options[0].text, '枣类')

            # 上传文件
            file = self.driver.find_element(By.ID, 'id_logo')
            file.send_keys('/Users/lidongqiang/DjangoProjects/myshop-back/selenium_test/scree01.png')
            # 输入排序
            self.driver.find_element(By.NAME, 'sort').send_keys('5')
            time.sleep(2)
            # 单击保存按钮
            self.driver.find_element(By.NAME, '_save').send_keys(Keys.ENTER)
        except AssertionError as e:
            print(f'测试异常为{e}' + '\n')
            savescreem(self.driver)
            raise e

        except Exception as e:
            savescreem(self.driver)
            raise e

    def tearDown(self):
        self.driver.quit()


def savescreem(driver):
    cur_path = os.path.dirname(__file__)
    filename = os.path.join(cur_path, str(random.randint(0, 1000000)) + ".png")
    try:
        driver.get_screenshot_as_file(filename)
    except Exception as e:
        print(e)


if __name__ == '__main__':
    unittest.main()
```

