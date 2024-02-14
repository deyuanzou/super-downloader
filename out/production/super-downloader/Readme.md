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

- 在download.txt里输入要下载资源网址，每个网址独占一行
- 运行Main.java


