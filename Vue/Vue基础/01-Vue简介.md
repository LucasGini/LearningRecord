# Vue简介

## 什么是vue
vue 是一套基于MVVM模式构建用户界面的渐进式框架，
支持前后端分离，
vue开发效率高，帮助减少不必要的DOM操作，双向数据绑定

## 安装vue
1. 使用独立版本

   直接使用vue官网下载vue的JavaScript脚本文件

   下载完成后，通过\<script src>引入vue.js文件
    ```html
    <body>
      <script src='vue.js'></script>
    </body>
    ```
   
2. 使用CDN方式引入

   CDN（Content Delivery Network,内容分发网络），是构建在现有网络基础上的智能虚拟网络，依靠部署在各地的边缘服务器，通过中心平台的负载均衡、内容分发、调度等功能模块，使网络用户就近获取所需内容，降低网络拥塞，提高用户访问响应速度和命中率
CDN服务商

   1. jsDelivr
   
      进入网页后，搜索vue，进入获取CDN地址页面

      将获取的地址引入script

      ```html
      <body>
      <script src="https://cdn.jsdelivr.net/npm/vue@2.6.12/dist/vue.min.js"></script>
      </body>
      ```
      
   2. cdnjs
   
      如上述操作
   
3. 使用npm方式安装
   ```markdown
   npm install -g @vue/cli
   ```

