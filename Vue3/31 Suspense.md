#  Suspense

**作用**：等待异步组件时渲染一些额外内容，让应用有更好的用户体验

**使用步骤**：

* 异步引入组件
* 使用suspense包裹组件，并配置号default插槽与fallback插槽（default插槽是在子组件异步任务完成后展示，fallback插槽子组件任务未完成前，临时展示）



## 子组件

进行了异步请求

@/Child.vue

```vue
<template>
  <div class="child">
    <h2>子组件</h2>
    <h3>求和为：{{sum}}</h3>
    <h3>情话：{{loveTalk}}</h3>
  </div>
</template>
<script setup>
  import {ref} from 'vue'
  import axios from 'axios'
  
  let sum = ref(333)
  
  let {data:{content:loveTalk}} = await axios.get('https://xxxx.com')

</script>
```



## 父组件

Child.vue是一个异步组件，如果不使用\<Suspense>包一层，会导致无法显示子组件

@/App.vue

```vue
<template>
	<div class="app">
    <h2>父组件</h2>
    <Suspense>
      <!-- default插槽是在子组件异步任务完成后展示 -->
      <template v-slot:default>
        <Child/>
      </template>
      <!-- fallback插槽子组件任务未完成前，临时展示 -->
      <template v-slot:fallback>
         加载中...
      </template>
    </Suspense>
  </div>
</template>
<script setup>
import Child from '@/Child.vue'
import {Suspense} from 'vue'
</script>
```

