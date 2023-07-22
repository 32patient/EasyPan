# EasyPan

EasyPan是一个基于 Spring Boot 和 Vue 3 的网盘项目，旨在帮助用户上传、下载和分享文件。

该项目提供了一个方便快捷的文件管理平台，让用户能够轻松地存储和访问他们的文件。

<br>

## 技术栈

- 后端：Java Spring Boot, Spring Security, Spring Data JPA, MySQL, Redis, FFmpeg
- 前端：Vue 3, Vue Router, Vuex, Axios, Element Plus, Highlight.js
- 其他：Node.js, Webpack, Babel, Sass, SparkMD5, Vue Cookies, Vue Clipboard3, Vue PDF Embed, XLSX, DPlayer, APlayer, docx-preview

### 环境要求

在开始之前，请确保以下环境已经安装：

- Java JDK 8 或以上版本
- Node.js (建议使用 v16 或以上版本)
- MySQL 数据库
- Redis：用于缓存和Session管理
- FFmpeg：处理视频和音频文件
- Nginx：用于前端部署和反向代理
- SwitchHosts：配置 host 方便开发时的本地域名映射

<br>

### 后端项目创建和配置

在开始前，请确保你已经安装了Java JDK 8或以上版本，并且配置了Maven环境。

配置名为 "easypan" 的数据库，使用 Navicat 连接到 MySQL 数据库，方便查看和管理数据库的内容。

<br>

### 前端项目部署和配置

在开始前，请确保你已经安装了Node.js和npm

将"dist"目录下的文件部署进Nginx中。

<br>

### 使用QQ互联实现注册和登录
EasyPan项目使用了QQ互联作为第三方登录认证方式。为了在你的项目中实现此功能，你需要进行以下配置：

获取QQ互联的AppID和AppKey
前往QQ互联开放平台注册一个应用，获取对应的AppID和AppKey。

配置后端的QQ互联信息

<br>

#### EasyPan优点

简洁易用：EasyPan拥有直观的用户界面和用户友好的操作，无论是上传、下载还是分享文件，都能够在几步之内完成。

多媒体支持：通过集成FFmpeg、DPlayer和APlayer等工具，EasyPan支持视频、音频文件的在线播放，让用户可以在线观看或收听媒体文件。

QQ互联登录：EasyPan实现了使用QQ互联进行注册和登录的功能，用户可以方便地通过QQ账号登录，避免繁琐的注册流程。

数据安全保障：EasyPan采用了Spring Security进行用户认证和授权，通过Redis进行数据缓存，保障用户数据的安全和高效访问。

扩展性：EasyPan的后端使用Spring Boot框架，前端使用Vue 3框架，代码结构清晰，易于维护和扩展，方便添加更多功能。

EasyPan旨在为用户提供一个简单、高效的网盘服务，让用户能够轻松管理和共享自己的文件。无论是个人使用还是团队协作，EasyPan都能够满足你的需求，让文件管理变得更加便捷和愉快。

<br>

#### 联系方式
邮箱：ruimuqin@gmail.com

<br>

##### 常见问题
1. 如何配置 Nginx 来部署前端项目？

您可以参考 Nginx 的官方文档或者搜索网络上的教程，配置一个 Nginx 服务器并将前端项目部署到其中。

2. 如何配置 SwitchHosts 来映射本地域名？

SwitchHosts 是一个方便管理本地 hosts 的工具，您可以通过添加一条本地映射来实现本地域名的访问。
