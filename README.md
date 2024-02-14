## 多线程下载器

### 开发语言

- JDK1.8

### 功能介绍

- 通过读取源文件(download.txt，其中可包含多个网址，每个网址独占一行)多线程下载网络资源到本地硬盘
- 遇到下载故障在控制台打印错误消息
- 用户可以自定义源文件地址，提示用户创建保存下载文件的目录
- 用户可以定义同时下载的任务数量，默认开启10个下载任务
- 下载后的文件名为网址包含的文件名加上时间戳(防止文件重名)
- 下载成功后在控制台输出存储路径与文件尺寸

### 使用说明

- 在download.txt文件里输入要下载资源网址，每个网址独占一行
- download.txt文件的位置默认在项目的conf目录下
- 在config.properties文件里定义thread-num为同时选择的线程数
- 在config.properties文件里定义target-dir为下载文件保存的目录(默认为G:/foo)，建议改为D:/foo
- config.properties文件的位置默认在项目的conf目录下
- idea目录下的`代码实现.xmind`文件请用xmind软件打开
- 运行Main.java开始下载