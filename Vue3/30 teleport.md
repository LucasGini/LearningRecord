# teleport

teleport是一种能够将组件html结构移动到指定位置的技术



## 实现将子主件内容移动到\<body>标签下

@/Model.vue

to属性的值可以是类选择器、id选择器或标签

```vue
<template>
  <button @click="isShow = true">展示弹窗</button>
  <teleport to="body">
    <div class="model" v-show="isShow">
      <h2>我是弹窗标题</h2>
      <p>我是弹窗内容</p>
      <button @click="isShow = false">关闭弹窗</button>
    </div>
  </teleport>
</template>
<script setup>
  import {ref} from 'vue'
  let isShow = ref(false)

</script>
```



@/App.vue

```vue
<template>
	<div>
    <h2>父组件</h2>
    <Model/>
  </div>
</template>
<script setup>
import Model from '@/Model.vue'

</script>
```

