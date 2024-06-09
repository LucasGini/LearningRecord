# all()方法用法
## 列表中所有元素都为True
```Python
lst1 = [True, True, True]
print(all(lst1))  # 输出: True
```
 
## 列表中存在一个元素为False
```Python
lst2 = [True, False, True]
print(all(lst2))  # 输出: False
```

 
## 字符串中所有字符都为True
```Python
str1 = 'Hello'
print(all(str1))  # 输出: True
```
 
## 字符串中存在一个字符为False
```Python
str2 = 'Hello0'
print(all(str2))  # 输出: False
```

