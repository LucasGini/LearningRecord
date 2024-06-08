# customRef



**作用**：创建一个自定义的ref，并对其依赖项跟踪和更新进行逻辑控制



## 自定义一个ref

更新数据后等待一段时间，页面数据才变更

@/hooks/useMsgRef.js

```javascript
import {customRef} from 'vue'
  
export default function(initValue, delay) {
  // 使用Vue提供的customRef定义响应式数据
  let initValue = initValue
  let timer
  // track：跟踪、trigger：触发
  let msg = customRef((track, trigger) => {
    return {
      // msg被读取时访问get()
      get() {
        track()  // 告诉Vue数据msg很重要，要对msg进行持续关注，一但msg变化就去更新
        return initValue = value
        
      },
      // msg被修改时访问set()
      set(value) {
        // 数据修改1秒后，页面数据才变更
        clearTimeout(timer)
        timer = setTimeout(() => {
          initValue = value
          trigger()  // 通知Vue数据msg已经变更
        }, delay)
      }
    }
  })
  return {msg}
}

```



@/App.vue

```vue
<template>
	<div>
    <h2>{{msg}}</h2>
    <input type="text" v-model="msg">
  </div>
</template>
<script setup>
import useMsgRef from '@/hooks/useMsgRef'

// 实现数据更新两秒后，页面数据才变更
let {msg} = useMsgRef('helloWorld', 2000)

</script>
```

