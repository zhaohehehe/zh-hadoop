1. 使用`ip addr`查看当前的ip地址（centos无法使用命令ifconfig查看ip）
2. 如果如以下所示，可知网卡ens33并没有相应的inet属性即没有IP地址，也就无法通过IP地址连接虚拟机。centos获取IP地址的方式有两种：一是动态获取IP，二是设置静态IP。
```
[rootChnode01 umare -tools -distrib]# ip addr
1: lo: <LOOPBACK,UP LOWER UP> mtu 65536 qdisc noqueue state UNKNOIN group default qlen 1000
l ink/ loopback 00 :00 :00:00 :00:00 brd 00 :00:00 : 00 :00:00
inet 127 .0.0.1/8 scope host lo
valid lft forever pref erred_ lft forever
inet6 : :1/128 scope host
vaHid_ Hft forever preferred_ Hft forewer
2: ens33: <BROADCAST, MULTICAST,UP LOWER UP> mtu 1500 qdisc pfifo_ fast state UP group default qlen 10
00
Hink/ether 08:8c :29:b4:a4:c2 brd ff ff ff:ff:ff ff
[rootChnode01 unnware tools -d istr ib]#
```
### 静态网络配置
  1. 编辑`vi /etc/sysconfig/network-scripts/ifcfg-ens33`
    - TYPE=Ethernet
    - PROXY_ METHOD=none
    - BROWSER ONLY=no
    - BOOTPROTO=dhcp（动态获取IP改为静态）TO:`static`
    - DEFROUTE=yes
    - IPV4_FAILURE_FATAL=no
    - IPV6_INIT=yes
    - IPV6_AUTOCONF=yes
    - IPV6_DEFROUTE=yes
    - IPV6_FAILURE_FATAL=no
    - IPV6_ADDR_GEN_MODE=stable-privacy
    - NAME =ens33
    - UUID=****
    - DEUICE=ens33
    - 0NBOOT=no TO:`yes`
    - IPADDR=`192.168.?.?`（IP地址）
        + 第一个?：【编辑>虚拟网络编辑器>VMnet8>VMnet信息：子网IP、子网掩码】，?和子网 IP一致。
        + 第二个?：范围[3,254]。255广播地址、0被VMnet8`子网IP`占用、2被【编辑>虚拟网络编辑器>VMnet8>NAT设置】`网关IP`占用、1被【控制面板>网络和 Internet>网络连接>双击"VMnet8">详细信息】`IPv4地址`占用。这里配置：101
    - NETMASK= `同VMnet8子网掩码`（子网掩码）
    - GATEWAY=`同VMnet8网关IP`（默认网关）
    - DNS1=114.114.114.114（DNS服务器，常用免费DNS服务器即可）
    - DNS2=8.8.8.8（DNS服务器，常用免费DNS服务器即可）
  2. 保存并重起网络`systemctl restart network`。
  3. `ip addr`检查网络是否设置成功。 `ping www.baidu.com`检查网络是否可用。
  4. 虚拟机快照功能克隆多个server
    - 需要调整IPADDR
    - 需要调整/etc/hostname文件
