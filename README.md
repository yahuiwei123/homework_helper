README

createby：@韦亚辉 

## 请求框架

Android端使用okhttp3作为服务器请求工具，配置方法如下：

1. AndroidManifest清单文件里声明需要用到Internet的权限（与application标签同级）

![img](https://nud2qebx5i.feishu.cn/space/api/box/stream/download/asynccode/?code=MzNmOGVmMTZiNWNjNTIzZjRhYTA4ZmFhYzRhMWNlNTFfZjJmR1czUXZybmJMbTBzdW5UMG5Xd0FlQXhuamFvRU1fVG9rZW46Ym94Y25OUGpnQ3M0VmFreTV4NjdjVGxPUU1mXzE2MzgzNDAyMDA6MTYzODM0MzgwMF9WNA)

1. 在build.gradle里面引入如下依赖

![img](https://nud2qebx5i.feishu.cn/space/api/box/stream/download/asynccode/?code=MjkzZWNhNjIzZjZiNDQ1Nzk4ZTIwNDMyZjA2MDY1MDZfcTRkRzVZU0o3WWZHbmpDWHg4NUdLRU9KU3l5YnB5NlFfVG9rZW46Ym94Y25GUnhwTjRQSmhvbnRJT1JkY0UwVlBnXzE2MzgzNDAyMDA6MTYzODM0MzgwMF9WNA)

1. 请求方法参考如下链接

https://blog.csdn.net/chennai1101/article/details/103857371

## 服务端统一框架

使用springboot，用法待补充@孙嘉豪 



## Android开发规范

### java类：

![img](https://nud2qebx5i.feishu.cn/space/api/box/stream/download/asynccode/?code=MmRhMjE2NmUyNjc0MjI5MzUwMTVkZmIxZGY4YjlkMmJfSUlqMUtvbTNueUY0SkNnU2Rjc29pbm9TYmNBUUVqa1lfVG9rZW46Ym94Y25pR1BVak90ZjBMYkVFMEx4ZU1qRU1lXzE2MzgzNDAyMDA6MTYzODM0MzgwMF9WNA)

1. 每个人在自己各自对应模块的文件夹下写自己的java类；
2. 自实现的类（例如adapter，viewholder）的命名方式为各自模块的包名首字母大写+自己需要的名字。例，历史搜题模块的adapter命名为HmyDataAdapter；
3. Activity对应的java类命名为包名首字母大写+你自己想命名的名字。例，历史搜题模块的页面java类命名为HistoryDisplayActivity；



关于各自对应的包：

1. login登录模块@华蕴甲 
2. mainPage拍照主页面@郭辉 
3. result拍搜结果展示@孙嘉豪 
4. userInfo用户信息展示@雷峻松 
5. history历史搜题展示@韦亚辉 

### Themes

themes文件夹下的主题暂定@韦亚辉 