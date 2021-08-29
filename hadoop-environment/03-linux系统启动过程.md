## 简单描述[linux系统启动过程](https://www.runoob.com/linux/linux-system-boot.html)
- 内核的引导
  > + 当计算机打开电源后，首先是BIOS开机自检（load主板BIOS芯片程序到内存），按照BIOS中设置的引导设备（硬盘、U盘、光盘的`MBR（Master Boot Record）`）来启动。
  > +操作系统接管硬件(`Bootloader,例如Grub`)以后，首先读入 /boot 目录下的内核文件。
- 运行 init
  > + 启动时根据"运行级别"，确定要运行哪些（开机启动）程序，它们在Windows叫做"服务"（service），在Linux就叫做"守护进程"（daemon）。
  > + rc启动脚本
- 系统初始化
  > + 它主要完成的工作有：激活交换分区，检查磁盘，加载硬件模块以及其它一些需要优先执行任务。
- 建立终端
  > + init接下来会打开6个终端（`Linux预设提供了六个命令窗口终端机`让我们来登录），以便用户登录系统，终端显示登录界面。`Ctrl + Alt + F1 ~ F6 切换，Ctrl + Alt + F7返回图形界面`。
  > + `login shell`程序来验证用户的身份
- 用户登录系统
  > + 命令行登录
  > + ssh登录
  > + 图形界面登录

## 简单描述[linux系统关机](https://www.runoob.com/linux/linux-system-boot.html)
  - 正确的关机流程为：sync > shutdown > reboot > halt。
  - 不管是重启系统还是关闭系统，首先要运行 sync 命令，把内存中的数据同步到硬盘。










## Shell
Linux 的 Shell 种类众多，常见的命令有：
  - Bourne Shell（/usr/bin/sh或/bin/sh）
  - <style color='red'>Bourne Again Shell（/bin/bash）</style>
  - C Shell（/usr/bin/csh）
  - K Shell（/usr/bin/ksh）
  - Shell for Root（/sbin/sh）

## 内部命令
 1. 系统启动时加载到内存（shell）
 2. help cd、info cd

## 外部命令
  1. 需要使用时，加载到内存。由shell程序管理：`hash`、路径查找(`echo $PATH`)、加载、执行。
  2. man yum、info cd、yum --help
    - `yum install -y man man-pages`
    - `man utf-8`、`man ascii`
    - `man` 进一步查找，例如：`man 2 read`
      + 1用户命令（/bin,/usr/bin,/usr/local/bin）
      + 2系统调用
      + 3库用户
      + 4特殊文件（设备文件）
      + 5文件格式（配置文件的语法）
      + 6游戏
      + 7杂项（Miscellaneous）
      + 8管理命令（/sbin,/usr/sbin,/usr/local/sbin）

## 查看内/外部命令：type
```
[jumpserver@root ~]$ type cd
cd is a shell builtin
[jumpserver@root ~]$ type ifconfig
ifconfig is /sbin/ifconfig
```
## 命令执行过程

```
Shell————>bash（解释执行）————>判断是外部/内部？————>内部执行、外部加载执行————>hash缓存
```

## 常用命令
  1. `whereis` 定位命令位置: whereis yum
  2. `file` 查看文件类型: file /usr/bin/yum
  3. `echo $LANG` 查看当前环境语言
  4. 变量：
  ```
[jumpserver@yanfabu ~]$ abc=hello
[jumpserver@yanfabu ~]$ echo $abc
hello
[jumpserver@yanfabu ~]$ arr=(1 2 3)
[jumpserver@yanfabu ~]$ echo $arr
1
[jumpserver@yanfabu ~]$ echo ${arr[0]}  
1
[jumpserver@yanfabu ~]$ echo ${arr[1]}
2
[jumpserver@yanfabu ~]$ echo ${arr[3]}
[jumpserver@yanfabu ~]$ echo ${arr[2]}
3
[jumpserver@yanfabu ~]$
```
  5. 当前线程的pid`echo $$`重新启动会变化
