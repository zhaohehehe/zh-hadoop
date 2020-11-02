### thrift demo

1. 下载thrift-*.exe可执行文件

   

2. 生成thrift接口文件：

   - 配置环境变量执行thrift --gen java Shared.thrift

   - 或者直接cd到exe路径执行： ./thrift-*  --gen java Shared.thrift

   - 或者配置maven-thrift-plugin插件执行

     

3. 不同thrift版本生成的文件可能不同，pom依赖版本和thrift-*.exe版本保持一致。