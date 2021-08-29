## xargs
- xargs 是一个强有力的命令，它能够捕获一个命令的输出，然后传递给另外一个命令。
- 之所以能用到这个命令，关键是**由于很多命令不支持|管道**来传递参数，而日常工作中有这个必要，所以就有了 xargs 命令，例如：
<br> find /sbin -perm +700 |ls -l       #这个命令是错误的
<br> find /sbin -perm +700 |xargs ls -l   #这样才是正确的
或者
<br> echo "/" |ls -l       #展示的是当前文件夹下的文件
<br> echo "/" |xargs ls -l   #这样才是展示/根目录下的文件
- xargs 一般是和管道一起使用。
- xargs 默认的命令是 echo，这意味着通过管道传递给 xargs 的输入将会包含换行和空白，不过通过 xargs 的处理，**换行和空白将被空格取代**。例如：多行输入单行输出`cat test.txt | xargs`。
- xargs 其他应用
<br>假如你有一个文件包含了很多你希望下载的 URL，你能够使用 xargs下载所有链接:`cat url-list.txt | xargs wget -c`

## 管道
- 简单理解就是前一个命令的标准输出作为后一个简单命令的标准输入。
