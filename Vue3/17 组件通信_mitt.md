# 组件通信——mitt

**概述**：用于任意层级组件通信

**注意**：卸载组件时，要解绑事件



## 安装mitt

```
// npm安装
npm i mitt

// yarn安装
yarn add mitt
```



## 引入mitt

@/utils/emitter.js

```javascript
// 引入mitt
import mitt from 'mitt'

// 调用mitt得到emitter，emitter能绑定事件、触发事件
const emitter = mitt()

// 暴露emitter
export default emitter
```

@/main.js

```javascript
import { createApp } from 'vue'
import App from './App.vue'
import emitter from '@/utils/emitter'

const app = createApp(App)

app.mount('#app')
```



## 父组件

@/custom_event/Father.vue

```vue
<template>
  <div class="father">
    <h3>父组件</h3>
    <h4 v-if='toy'>孩子给的玩具： {{toy}}</h4>
    <!-- 给子组件绑定事件 -->
    <Child1/>
    <Child2/>
  </div>
</template>
<script setup name="Father">
  import {Child1} from './Child1.vue'
  import {Child2} from './Child2.vue'
  import {ref} from 'vue'
  
  // 数据
  let toy = ref('')
  
  // 方法
  function getToy(value) {
    toy.value = value
  }
</script>
```



## 子组件1

@/custom_event/Child1.vue

```vue
<template>
  <div class="child">
    <h3>子组件</h3>
    <h4>玩具：{{toy}}</h4>
    <button @click="sendToy">把玩具给哥哥</button>
  </div>
</template>
<script setup name="Child1">
  import {ref} from 'vue'
  import emitter from '@/utils/emitter'
  
  // 数据
  let toy = ref('奥特曼')
  
  // 触发事件
  function sendToy() {
    // 触发事件
    emitter.emit('get-toy', toy.value)
  }
</script>
```



## 子组件2

@/custom_event/Child2.vue

```vue
<template>
  <div class="child">
    <h3>子组件</h3>
    <h4>电脑：{{computer}}</h4>
    <h4>弟弟给的玩具：{{ toy }}</h4>
    <button @click="sendToy">把玩具给父亲</button>
  </div>
</template>
<script setup name="Child2">
  import {ref, onUnmounted} from 'vue'
  import emitter from '@/utils/emitter'

  
  // 数据
  let computer = ref('mac')
  let toy = ref('')
  
  // 绑定事件
  emitter.on('get-toy', (value) => {
    toy.value = value
  })
  
  // 卸载组件时解绑get-toy事件,减少内存消耗
  onUnmounted(() => {
    emitter.off('get-toy')
  })
  
</script>
```

