## 生成/添加 SSH
```
ssh-keygen -t rsa -C "dongqiangXX@gmail.com"  
```

这里的 dongqiangXX@gmail.com 只是生成的 sshkey 的名称，并不约束或要求具体命名为某个邮箱

## 查询公钥
Macbook查询方法：

进入到目录
```markdown
cd ~/.ssh 
```
查看 
```
ls
```
有id_rsa，id_rsa.pub，说明之前生成过ssh 秘钥，可以直接使用
```markdown
cat id_rsa.pub
```

显示的一大串 全部复制

Windows查询方法：

c盘 ---->用户 ---->自己用户名的文件 ----> .ssh的文件夹
----> id_rsa.pub ----> 记事本打开

全选 复制里面内容

## 仓库配置 SSH（github为例）
进入仓库-->Settings-->Deploy keys-->Add Deploy keys

将公钥复制到对应的输入框，点击保存

## 配置IDE远程仓库（pychram）
Git-->管理远程

将HTTPS的连接改成SSH的连接，点击确定，重新拉取或者推送即可解决该问题
