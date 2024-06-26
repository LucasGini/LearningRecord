# vue-router实现路由管理
1. 下载命令安装Vue Router库
   ```markdown
   npm install -save vue-router
   ```

2. 以vue-cli脚手架默认生成的应用为例，进入src/router/index.js文件
   ```Javascript
   import HelloWorld from '@/components/HelloWorld'  //引入@路径下名称为HelloWorld的页面组件
   import Vue from 'vue'   //引入Vue.js框架
   import Router from 'vue-router'  //引入vue-router组件
   
   Vue.use(Router)  //全局使用Router
   
   //定义路由，只有在导出后在其他地方才可以使用
   export default new Router({
   routes: [
      {
         //在访问根路径是会连接到HelloWord.vue组件
         path: '/',    //路由的路径
         name: 'HelloWorld',  //路由的名称
         component: HelloWorld  //对应的组件模板
      }
      ]
   })
   ```
   
3. 在项目main.js中引入路由
   ```Javascript
   import Vue from 'vue'
   import App from './App'
   import router from './router'  //引入路由，会去寻找index.js配置文件
   
   Vue.config.productionTip = false  //关闭生产模式下的提示
   
   /* eslint-disable no-new */
   new Vue({
     el: '#app',
     router,  //在框架中使用路由功能
     components: { App },
     template: '<App/>'
   })
   ```

4、编辑Helloworld.vue的代码

5、执行命令 npm run dev启动服务，然后通过http://localhost:8080/#进行访问
