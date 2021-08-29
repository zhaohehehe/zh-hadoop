## linux虚拟文件系统
以下图片以/boot分区目录为例，无论/boot分区目录是否挂载，`目录结构保持不变`，其他分区目录也是一样。
![img](/imgs/linux虚拟文件系统说明.png "linux虚拟文件系统")

## linux[目录结构](https://www.runoob.com/linux/linux-system-contents.html)
- > - / ( `/`根目录，`~`是家目录)
      - root（管理员的家目录：su root=cd /root）
      - home（普通用户的家目录: su alice）
      - boot
- /proc目录。以下图中的命令等价于`ls /proc/$$/fd`。
![img](/imgs/linux-proc目录.png "linux-proc目录")
上图显示出来的`0 1 2 255`是常用的文件描述符（fd,file descriptor）。
> + 0 标准输入stdin
  + 1 标准输出stdout
  + 2 标准错误输出stderr
  + 255 输入输出重定向。`eg:这里的 2 和 > 之间不可以有空格，2> 是一体的时候才表示错误输出`
- /usr目录。usr 是 unix shared resources(共享资源) 的缩写，这是一个非常重要的目录，用户的很多应用程序和文件都放在这个目录下，类似于 windows 下的 program files 目录。
- /opt目录。opt 是 optional(可选) 的缩写，这是给主机额外安装软件目录。比如你安装一个ORACLE数据库则就可以放到这个目录下。默认是空的。如果不指定安装位置(/opt)，默认/usr，
- /mnt目录。系统提供该目录是为了让用户临时挂载别的文件系统的，我们可以将光驱挂载在 /mnt/ 上，然后进入该目录就可以查看光驱里的内容并使用了（`这也是linux安装时建新虚拟磁盘容量设置尽量大的原因`）。
- 其他目录（见标题链接）

## Linux[磁盘管理](https://www.runoob.com/linux/linux-filesystem.html)
- `df [-ahikHTm] [目录或文件名]` Linux df（英文全拼：disk free） 命令用于显示目前在 Linux 系统上的文件系统磁盘使用情况统计。
- `du [-ahskm] 文件或目录名称` 显示指定的目录或文件所占用的磁盘空间。
- `fdisk [-l] 装置名称`磁盘分区表操作工具
- `mkfs [-t 文件系统格式（例如 ext3, ext2, vfat 等）] 装置文件名`磁盘格式化。
- `fsck [-t 文件系统] [-ACay] 装置名称`磁盘检验file system check
- `mount [-t 文件系统] [-L Label名] [-o 额外选项] [-n]  装置文件名  挂载点。`。
- `umount [-fn] 装置文件名或挂载点`
  +  `umount /boot`卸载磁盘分区（<em>虚拟文件系统，不影响物理文件<em>）
  +  `mount /dev/sda1 /boot`挂载设备到磁盘分区

## linux[文件基本属性](https://www.runoob.com/linux/linux-file-attr-permission.html)

## linux[文件系统命令](https://www.runoob.com/linux/linux-comm-ls.html)
- `ll 或者 ls –l` 命令来显示一个文件的属性以及文件所属的用户和组。
  + ls /mnt /home 按照树结构层次遍历展示多级目录。`yum install -y tree`安装tree命令查看文件系统目录。
  + `-rw-r--r--`:参见[文件基本属性](https://www.runoob.com/linux/linux-file-attr-permission.html)

```
[root@hnode001 boot]# ls -l
total 92525
-rw-r--r--. 1 root root   153562 Apr  1  2020 config-3.10.0-1127.el7.x86_64
drwxr-xr-x. 3 root root     1024 Mar 14 20:37 efi
```
- cd,mkdir(目录),rm(文件或者目录)
- cp(文件或者目录)
- mv (移动或者重命名文件/目录)
- `ln [参数][源文件或目录][目标文件或目录]`[ln命令](https://www.runoob.com/linux/linux-comm-ln.html)
  + 给文件创建软链接，为log2013.log文件创建软链接link2013（`inode相同`），如果log2013.log丢失/更改，link2013将失效/更改: `ln -s log2013.log link2013`
  + 给文件创建硬链接，为log2013.log创建硬链接ln2013（`inode相同`），log2013.log与ln2013的各项属性相同。如果log2013.log丢失/更改，link2013不会丢失/更改: `ln log2013.log ln2013`
  + 无论是软链接还是硬链接，文件都会修改，因为inode地址是一样的。
- `stat [文件或目录]`显示inode内容（元数据）。
- `touch [-acfm][-d<日期时间>][-r<参考文件或目录>] [-t<日期时间>][--help][--version][文件或目录…]`。Linux touch命令用于修改文件或者目录的时间属性，包括存取时间和更改时间。若文件不存在，系统会建立一个新的文件。 `
