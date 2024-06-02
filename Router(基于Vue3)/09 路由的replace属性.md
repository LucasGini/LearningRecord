# 路由的replace属性



1. **作用**：控制路由跳转时操作浏览器历史记录模式。

2. 浏览器的历史记录有两种写入方式：分别为push和replace：

   * push是追加历史记录（默认值）
   * replace是替换当前记录

3. 开启replace模式：@/components/MainPage.vue

   ```vue
   <template>
   	<!-- 导航区 -->
     <div class="main-page">
       <RouterLink to="/home" replace>
         <button>首页</button>
       </RouterLink>
       <RouterLink to="/news" replace>
         <button>新闻</button>
       </RouterLink>
       <RouterLink to="/about" replace>
         <button>关于</button>
       </RouterLink>
     </div>
     <!-- 展示区 -->
     <div class="main-content">
       <RouterView></RouterView>
     </div>
   </template>
   <script setup name='MainPage'>
     import {RouterLink, RouterView} from 'vue-router'
   </script>
   ```

   

