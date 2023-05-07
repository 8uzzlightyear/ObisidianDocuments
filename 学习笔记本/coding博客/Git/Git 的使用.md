# 配置Git

安装Git后配置用户名称和e-mail地址

```POWERSHELL
git config --global user.name "name" #名称
git config --global user.email xxx@domain.com #邮箱
```

绑定SSH公钥

本地：

```POWERSHELL
# 进入 C:\Users\Administrator 目录
# 生成公钥
# ssh-keygen -t rsa -C "xxxxx@xxxxx.com"
ssh-keygen -t rsa 
```

github：

进入设置
![[Pasted image 20230505162139.png]]

选择

![[Pasted image 20230505162208.png]]

在C:\Users\Administrator目录中查看隐藏文件，将.ssh.pub内的文本内容复制进key框

![[Pasted image 20230505162352.png]]

点击Add SHH key即可。

# 连接远程仓库

## 先建远程仓库

在github主页的 Repositories 中点击New，新建一个仓库

![[Pasted image 20230505162907.png]]

在本地新建一个文件夹，在此文件夹内打开Git Bash，输入

```POWERSHELL
# 克隆一个项目和它的整个代码历史(版本信息) 
$ git clone [url] 
```

文件夹内会出现一个.git文件，这样远程仓库被克隆至本地，相当于多了一个本地仓库，且本地和远程处于连接状态。

向文件夹内添加文件后，可以在Git Bash中输入下面的代码  ^54e11e

```POWERSHELL
# git pull origin master

git add .
git commit -m 'init'
git push -u origin master

git push origin master

```

git add . 可以将文件夹内所有文件添加至缓存区，git commit -m 'init'，提交缓存区的文件至本地仓库，并给出附言‘init’，git push 将本地仓库文件上传至远程仓库。

## 复制已连接至远程仓库的本地仓库

新建一个文件夹，复制另一个已经连接远程仓库的含.git文件的文件夹至新建文件夹，此时在新建文件夹中可重复[克隆远程仓库后的步骤](#^54e11e)修改远程仓库。