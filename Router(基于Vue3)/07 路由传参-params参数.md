# 路由传参-params参数



## 配置参数占位

**注意**：

* 想要使用params参数，需要在对应的path上提前占位， 如detail/:id/:title/:content，占位名就是params对象的key，
* 配置了几个占位，就要传递几个参数，否则会报错，如果想要可传可不传，需要在占位后面加上“?”，如'detail/:id/:title/:content?'，第三个参数就可传可不传了

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
        component: NewsDetail
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



## 传递参数（写法一：字符串写法）

@/views/News.vue

```vue
<template>
  <div class="news">
    <ul>
      <li v-for="news in newsList" :key="news.id">
        <RouterLink to="`/news/detail/${news.id}/${news.title}/${news.content}`">{{ news.title }}</RouterLink>
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



## 传递参数（写法二：对象写法）

**注意**：使用params传参时，只能使用路由的name去跳转，而且params传参不能传对象和数组

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





## 接收参数（上级路由传递的参数）

* 获取路由实例
* 获取路由的params对象
* 获取params对象的属性（上级路由传递的参数）

@/views/NewsDetail.vue

```vue
<template>
  <div class="news-list">
    <li>编号：{{ params.id }}</li>
    <li>标题：{{ params.title }}</li>
    <li>内容：{{ params.content }}</li>
  </div>
</template>
<script setup name="NewsDetail">
  import {toRefs} from 'vue'
  import {useRoute} from 'vue-router'
  let { params } = toRefs(useRoute())
</script>
```



