##### 准备安装文件
1. 下载VMware® Workstation 16 Pro。
2. 下载CentOS-7-x86_64-Minimal-2003.iso。

##### 开始安装linux

1. 安装VMware Workstation（例如：VMware® Workstation 16 Pro）
2. 在VMware页面新建虚拟机。
  1. 选择稍后安装操作系统；
  2. 选择在虚拟机中安装的操作系统（例如：CentOS 7 64 位）；
  3. 设置虚拟机名称/位置（node01/D:\Hadoop\VMwareMachines\node01）；
  4. 选择处理器数量（1）；
  5. 选择虚拟机内存（>=768M？？，1G）
  6. 网络连接（可以选择其他方式，这里选择NAT网络地址转换）
  7. 选择磁盘（创建新虚拟磁盘）
    - 选择`最大`磁盘大小：100G
    - 在不勾选立即分配所有磁盘空间的情况下，不要求指定的100G都可用，实际使用空间会依据实际数据量的增大而增大，当然，这个最大值是可以通过挂载新磁盘扩容的，这里选择100G；
3. 为新建的虚拟机（硬件）安装操作系统（软件）
  1. 双击“CD/DVD(IDE)”，选择下载好的ISO镜像文件（D:\Hadoop\Downloads\CentOS-7-x86_64-Minimal-2003.iso）；
  2. 点击“确定”，然后点击“开启此虚拟机”，进入安装窗口等待安装完成（Ctrl+Alt退出窗口）；
  3. 开始系统配置：
    - Language:English；
    - keyBoard:US English；
    - 时区：Asia.Shanghai；
    - hostname:（hnode01，也可以和虚拟机名称一样）；
    - network:暂时不连；
    - Installation destination(磁盘分区): 点击“sad / 100GiB free磁盘”，选择“I will configure partitioning”，选择“Done”之后开始create分区：
        + SYSTEM:/boot（ext4/200MiB）、swap（swap/2048MiB,系统内核使用）、/（ext4/剩余GiB，根目录）
        + DATA:/home（10G，也可以不create该分区）。`注意：这里可以选择标准分区或者LVM分区!`
    - 设置root（默认用户）密码（123456）
    - 创建user（也可以不创建）：hadoop/123456。
  4. 等待安装完成之后，点击“Reboot”；
  5. 黑窗口登录：
      hnode01 login:`root`
      Password:`123456`
      [root@hnode01 ~]# （登录成功！！！）
4. 安装开源open-vm-tools（***不建议安装，可以使用类似Xshell的工具***）：执行`yum -y install open-vm-tools`，`systemctl enable vmtoolsd`，`systemctl start vmtoolsd`安装完成后，`reboot`重新开机即可。CentOS7自带open-vm-tools。如果安装官方版本，继续。
5. 手动安装官方VMware Tools（***不建议安装，可以使用类似Xshell的工具***）

 参考1 https://docs.vmware.com/cn/VMware-Fusion/11/com.vmware.fusion.using.doc/GUID-08BB9465-D40A-4E16-9E15-8C016CC8166F.html

 参考2 https://blog.csdn.net/zhujing16/article/details/88677253

  1. 点击VMware菜单栏【虚拟机】，查看【设置>硬件>CD/DVD】正在使用的iso镜像文件D:\Hadoop\Downloads\CentOS-7-x86_64-Minimal-2003.iso
  2. 点击VMware菜单栏【虚拟机】，选择【安装/更新VMware Tools】，弹出以下提示，选择是。
  `客户机操作系统已将 CD-ROM 门锁定，并且可能正在使用 CD-ROM，这可能会导致客户机无法识别介质的更改。如果可能，请在断开连接之前从客户机内部弹出 CD-ROM。确实要断开连接并覆盖锁定设置吗?`。
  3. 提示 `装载虚拟CD驱动器，启动终端，解压tar安装程序，执行vmvare-install.pl安装文件`。
    - 查看虚拟CD驱动器是否装载成功。再次查看步骤1，此时iso镜像文件已经变为D:\Hadoop\VMwareWorkstation\linux.iso（代表装载成功）。
    - 挂载 CD-ROM 设备。在虚拟机中，以 root 身份登录客户机操作系统，打开终端窗口并运行ls /dev命令查看是否含有cdrom目录。如果没有，创建挂载点（`mkdir /mnt/cdrom或mkdir -p /mnt/cdrom（-p则可以一次创建多个目录）`）。将CD-ROM挂载到该目录（`mount /dev/cdrom /mnt/cdrom`）。提示`mount:/dev/src0 is write-protected.mounting read-only`，查看ls /mnt/cdrom是否挂载成功。
    - 解压安装包。`cp /mnt/cdrom/VMwareTools-x.x.x-yyyy.tar.gz ~` ls 查看是否成功。 `tar zxpf VMwareTools-x.x.x-yyyy.tar.gz` ls 查看是否成功（vmware-tools-distrib）
    - 如果需要，请卸载 CD-ROM 映像：`umount /dev/cdrom`
    - 安装。`./vmware-install.pl`。如果出现`-bash: ./vmware-install.pl: /usr/bin/perl: bad interpreter: No such file or directory`，说明未安装Perl编译环境（VMware Tools 安装程序是使用 Perl 编写的，请确认已在客户机操作系统中安装 Perl。）
    - 启动`./`

6. 安装 Perl。执行`yum -y install perl gcc gcc-c++ make cmake kernel kernel-headers kernel-devel net-tools`。如果出现错误`Could not resolve host : mirrorlist.centos.org`，说明没有配置网络。
7. 配置静态网络:参见《02-linux网络配置.md》。
