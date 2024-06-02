# 路由传参-query参数



## 传递参数（写法一：字符串写法）

@/views/News.vue

```vue
<template>
  <div class="news">
    <ul>
      <li v-for="news in newsList" :key="news.id">
        <RouterLink to="`/news/detail?id=${news.id}&title=${news.title}&content=${news.content}`">{{ news.title }}</RouterLink>
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

@/views/News.vue

```vue
<template>
  <div class="news">
    <ul>
      <li v-for="news in newsList" :key="news.id">
        <RouterLink :to="{
                    path: '/news/detail',
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





## 接收参数（上级路由传递的参数）

* 获取路由实例
* 获取路由的query对象
* 获取query对象的属性（上级路由传递的参数）

@/views/NewsDetail.vue

```vue
<template>
  <div class="news-list">
    <li>编号：{{ query.id }}</li>
    <li>标题：{{ query.title }}</li>
    <li>内容：{{ query.content }}</li>
  </div>
</template>
<script setup name="NewsDetail">
  import {toRefs} from 'vue'
  import {useRoute} from 'vue-router'
  let { query } = toRefs(useRoute())
</script>
```



