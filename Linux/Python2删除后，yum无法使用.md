# Python2删除后，yum无法使用

系统：CentOS  7.4

装了Python3觉得Python2没用了删了python2 导致yum不可用

1. 卸载现有python
   ```markdown
   rpm -qa|grep python|xargs rpm -ev --allmatches --nodeps  #卸载python
   whereis python |xargs rm -frv     ##删除所有残余文件
   whereis python  ##验证删除，返回无结果
   ```

2. 删除现有的yum
   ```markdown
   rpm -qa|grep yum|xargs rpm -ev --allmatches --nodeps # 删除yum
   whereis yum |xargs rm -frv  # 删除残留文件
   whereis yum  # 验证删除完成
   ```
   下载文件源  http://vault.centos.org/

   首先在这里找到服务器对应的版本，如 7.4 进入这里目录 找到对应版本的rpm，替换下面的文件。

   下载并安装，注意顺序，先安装python 然后 yum。不然安装后还会报错，重新来一遍。

## python安装

```markdown
rpm -ivh  --nodeps http://vault.centos.org/7.4.1708/os/x86_64/Packages/python-2.7.5-58.el7.x86_64.rpm
rpm -ivh  --nodeps http://vault.centos.org/7.4.1708/os/x86_64/Packages/python-devel-2.7.5-58.el7.x86_64.rpm
rpm -ivh  --nodeps http://vault.centos.org/7.4.1708/os/x86_64/Packages/python-iniparse-0.4-9.el7.noarch.rpm
rpm -ivh  --nodeps http://vault.centos.org/7.4.1708/os/x86_64/Packages/python-libs-2.7.5-58.el7.x86_64.rpm
rpm -ivh  --nodeps http://vault.centos.org/7.4.1708/os/x86_64/Packages/python-pycurl-7.19.0-19.el7.x86_64.rpm
rpm -ivh  --nodeps http://vault.centos.org/7.4.1708/os/x86_64/Packages/python-urlgrabber-3.10-8.el7.noarch.rpm
rpm -ivh  --nodeps http://vault.centos.org/7.4.1708/os/x86_64/Packages/rpm-python-4.11.3-25.el7.x86_64.rpm
```
 
## yum安装
```markdown
rpm -ivh  --nodeps http://vault.centos.org/7.4.1708/os/x86_64/Packages/yum-3.4.3-154.el7.centos.noarch.rpm 
rpm -ivh  --nodeps http://vault.centos.org/7.4.1708/os/x86_64/Packages/yum-metadata-parser-1.1.4-10.el7.x86_64.rpm 
rpm -ivh  --nodeps http://vault.centos.org/7.4.1708/os/x86_64/Packages/yum-plugin-fastestmirror-1.1.31-42.el7.noarch.rpm
```

## 验证 

#验证python
```markdown
python
```


#验证yum
```markdown
yum
```

