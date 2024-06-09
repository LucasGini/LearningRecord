# Selenium使用：UI自动化测试

## 安装selenium库

（1）pip install selenium

（2）下载浏览器驱动

## 基本使用
```Python
from selenium import webdriver
browser = webdriver.Chrome()  # 声明浏览器
url = 'https://www.baidu.com'
browser.get(url)  # 打开浏览器预设网址
browser.maximize_window()
print(browser.page_source)  # 打印网页源代码
```

## 页面元素定位方法
* `find_element_by_id()`：通过页面元素id定位
* `find_element_by_xpath()`：通过xpath表达式定位
* `find_element_by_name()`：通过页面元素name定位
* `find_element_by_link_text()`：通过完整超链接定位
* `find_element_by_partial_link_text()`：通过部分链接定位
* `find_element_by_tag_name()`：通过标签定位
* `find_element_by_class_name()`：通过类名进行定位
* `find_element_by_css_selector()`：通过css选择器进行定位

最新的selenium已经弃用上面的方法

改为使用
* `find_element(by=By.ID, value='J_cate')`:获取一个WebElement实例
* `find_elements(by=By.CLASS_NAME, value='cate_menu_item')`:获取多个WebElement实例


使用例子：
```Python
from selenium import webdriver
from selenium.webdriver.common.by import By

browser = webdriver.Chrome()  # 声明浏览器
url = 'https://www.jd.com'
browser.get(url)  # 打开浏览器预设网址
cate_text = browser.find_element(by=By.ID, value='J_cate')
print(cate_text.text)
print('**********')
cate_names = browser.find_elements(by=By.CLASS_NAME, value='cate_menu_item')

for x in cate_names:
    print(x.text)
browser.close()  # 关闭浏览器
```


## selenium库的高级用法

### 模拟单击事件
```Python
import time
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys

browser = webdriver.Chrome()  # 声明浏览器
url = 'https://www.baidu.com'
browser.get(url)  # 打开浏览器预设网址
# 获取页面文本框并输入内容
input_tag = browser.find_element(by=By.ID, value='kw')
input_tag.send_keys('python')
# 按键盘上的enter键
input_tag.send_keys(Keys.ENTER)
# 休息两秒
time.sleep(2)
print(browser.page_source)   # 打印网页源代码
browser.close()  # 关闭浏览器
```


### 隐藏浏览器（无界面模式）
```Python
①创建一个ChromeOptions对象
②添加headless参数
③在声明浏览器对象时传递ChromeOptions对象

from selenium import webdriver

chrome_options = webdriver.ChromeOptions()  # 创建一个ChromeOptions对象
chrome_options.add_argument('--headless')  # 添加headless参数
driver = webdriver.Chrome(chrome_options)  # 声明浏览器， 传递ChromeOptions对象
url = 'https://www.baidu.com'
driver.get(url)  # 打开浏览器预设网址
print(driver.page_source)  # 打印网页源代码
driver.close()  # 关闭浏览器
```



### 等待元素被加载

#### 隐式等待
```Python
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By

browser = webdriver.Chrome()
browser.get('https://www.baidu.com')
# 隐式等待：为了等待页面显示完成设置一个最长等待时间，这里设置10秒
browser.implicitly_wait(10)
# 获取页面文本框并输入内容
input_tag = browser.find_element(by=By.ID, value='kw')
input_tag.send_keys('python')
# 按键盘上的enter键
input_tag.send_keys(Keys.ENTER)
# content_left元素在具体的搜索结果页面中。如果没有打开搜索结果页面而直接查找该元素，则会出现找不到该元素错误
contents = browser.find_element(by=By.ID, value='content_left')
print(contents.text)
browser.close()
```


#### 显式等待

通过WebDriverWait类的until()方法进行显示等待

wait = WebDriverWait(browser, 10)

wait = until(method, message='')

例子：
```Python
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

browser = webdriver.Chrome()
browser.get('https://www.baidu.com')
# 获取页面文本框并输入内容
input_tag = browser.find_element(by=By.ID, value='kw')
input_tag.send_keys('python')
# 按键盘上的enter键
input_tag.send_keys(Keys.ENTER)
# 显式等待：定义一个显式等待的条件，直到条件满足才执行下一条语句
wait = WebDriverWait(browser, 10)
wait.until(EC.presence_of_element_located((By.ID, 'content_left')))
# content_left元素在具体的搜索结果页面中。如果没有打开搜索结果页面而直接查找该元素，则会出现找不到该元素错误
contents = browser.find_element(by=By.ID, value='content_left')
print(contents.text)
browser.close()
```



### 当前浏览器窗口进行截屏
#### 将当前窗口进行截屏
```Python
import os.path
import time
from selenium import webdriver

cur_pat = os.path.dirname(__file__)
savefilename = os.path.join(cur_pat, 'scree01.png')
browser = webdriver.Chrome()
browser.get('https://www.phei.com/cn')
time.sleep(10)
# 将当前页面窗口保存为scree01.png文件，保存在当前目录下
browser.save_screenshot(savefilename)
browser.quit()
```


#### 保存网页长图
隐藏窗口界面

调用JavaScript函数获取当前浏览器的带滚动条的宽和高

调整浏览器的实际窗口大小

延迟几秒，因为有些网页使用了图片延迟加载技术，否则保存下来的部分图片是空白的

例子：
```Python
import os
import time
from selenium import webdriver

cur_pat = os.path.dirname(__file__)
savefilename = os.path.join(cur_pat, 'scree01.png')

chrome_options = webdriver.ChromeOptions()
chrome_options.add_argument('--headless')
browser = webdriver.Chrome(chrome_options)
browser.get('https://phei.com.cn/')
width = browser.execute_script('return document.body.scrollWidth')
height = browser.execute_script('return document.body.scrollHeight')
browser.set_window_size(width, height)
time.sleep(10)
# 将当前页面窗口保存为scree01.png文件，保存在当前目录下
browser.save_screenshot(savefilename)
browser.quit()
```

