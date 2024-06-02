# 路由props配置



**作用**：让路由组件更方便的收到参数（可以将路由参数作为props传给组件）



## props配置



### props的对象写法

作用：把对象中的每一组key-value作为props传给NewsDetail组件



@/router/index.js

```javascript
import {createRouter, createWebHistory} from 'vue-router'
import {Home} from '@/views/Home.vue'
import {News} from '@/views/News.vue'
import {About} from '@/views/About.vue'
import {NewsDetail} from '@/views/NewsDetail.vue'


// 定义路由
routes = [
  ...
  
  {
    path: '/news',
    name: 'news',
    component: News,
    // 配置嵌套路由
    children: [
      {
        name: 'detail'
        path: 'detail/:id/:title/:content?',
        component: NewsDetail,
        props:{
          id: 'xinwen05',
          title: '新闻05',
          content: '这是新闻05',
      }
      }
    ]
  },
  
  ...
]

const router = createRouter({
  // 路由器的工作模式
  history: createWebHistory(),
  routes
})


// 将路由暴露
export default router

```



### props的布尔值写法

作用：把收到的每一组params参数作为props传给NewsDetail组件

@/router/index.js

```javascript
import {createRouter, createWebHistory} from 'vue-router'
import {Home} from '@/views/Home.vue'
import {News} from '@/views/News.vue'
import {About} from '@/views/About.vue'
import {NewsDetail} from '@/views/NewsDetail.vue'


// 定义路由
routes = [
  ...
  
  {
    path: '/news',
    name: 'news',
    component: News,
    // 配置嵌套路由
    children: [
      {
        name: 'detail'
        path: 'detail/:id/:title/:content?',
        component: NewsDetail,
        props: true
      }
    ]
  },
  
  ...
]

const router = createRouter({
  // 路由器的工作模式
  history: createWebHistory(),
  routes
})


// 将路由暴露
export default router

```



上级路由组件传递params参数

```vue
<template>
  <div class="news">
    <ul>
      <li v-for="news in newsList" :key="news.id">
        <RouterLink :to="{
                    name: 'detail',
                    params: {
                         id: news.id,
                         title: news.title,
                         content: news.content
                         }
                         }">
          {{ news.title }}</RouterLink>
      </li>
    </ul>
  </div>
  <div class="news-detail">
    <RouterView></RouterView>
  </div>
</template>
<script setup name='News'>
  import {RouterView, RouterLink} from 'vue-router'
  import {reactive} from 'vue'
  let newsList = reactive([
    {id:'news01', title: '新闻1', content: '这是新闻1'},
    {id:'news02', title: '新闻2', content: '这是新闻2'},
    {id:'news03', title: '新闻3', content: '这是新闻3'},
    {id:'news04', title: '新闻4', content: '这是新闻4'},
  ])
</script>
```



### props的函数写法

作用：返回的路由对象中每一组key-value作为props传给NewsDetail组件，可以在使用query传参时使用

@/router/index.js

```javascript
import {createRouter, createWebHistory} from 'vue-router'
import {Home} from '@/views/Home.vue'
import {News} from '@/views/News.vue'
import {About} from '@/views/About.vue'
import {NewsDetail} from '@/views/NewsDetail.vue'


// 定义路由
routes = [
  ...
  
  {
    path: '/news',
    name: 'news',
    component: News,
    // 配置嵌套路由
    children: [
      {
        name: 'detail'
        path: 'detail/:id/:title/:content?',
        component: NewsDetail,
        props(route){
          return route.query
      }
      }
    ]
  },
  
  ...
]

const router = createRouter({
  // 路由器的工作模式
  history: createWebHistory(),
  routes
})


// 将路由暴露
export default router

```



上级路由组件传递query参数

```vue
<template>
  <div class="news">
    <ul>
      <li v-for="news in newsList" :key="news.id">
        <RouterLink :to="{
                    name: 'detail',
                    query: {
                         id: news.id,
                         title: news.title,
                         content: news.content
                         }
                         }">
          {{ news.title }}</RouterLink>
      </li>
    </ul>
  </div>
  <div class="news-detail">
    <RouterView></RouterView>
  </div>
</template>
<script setup name='News'>
  import {RouterView, RouterLink} from 'vue-router'
  import {reactive} from 'vue'
  let newsList = reactive([
    {id:'news01', title: '新闻1', content: '这是新闻1'},
    {id:'news02', title: '新闻2', content: '这是新闻2'},
    {id:'news03', title: '新闻3', content: '这是新闻3'},
    {id:'news04', title: '新闻4', content: '这是新闻4'},
  ])
</script>
```





## 使用props参数

* 获取组件props的参数

@/views/NewsDetail.vue

```vue
<template>
  <div class="news-list">
    <li>编号：{{ id }}</li>
    <li>标题：{{ title }}</li>
    <li>内容：{{ content }}</li>
  </div>
</template>
<script setup name="NewsDetail">
  defineProps(['id', 'title', 'content'])
</script>
```

