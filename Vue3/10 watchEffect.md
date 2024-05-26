# watchEffect

## watchEffect 概述

* **作用**：立即运行一个函数，同时响应式地追踪其依赖，并在依赖更改时重新执行该函数
* watch对比watchEffect
  1. 都能监听响应式数据的变化，不同的是监听数据变化的方式不同
  2. watch：要明确指出监视的数据
  3. warchEffect：不用指出监视的数据(函数中用到哪些属性，就监视那些属性)
* 示例代码

``` vue
<template>
  <div>
    <spen>当前水温：{{temp}}℃</spen>
    <spen>当前水位：{{height}}cm</spen>
    <button @click="changeTemp">温度+10</button>
    <button @click="changeHeight">水位+10</button>
  </div>
</template>
<script setup>
  import {ref, watch ,watchEffect} from 'vue'
  
  // 数据
  let temp = ref(10)
  let height = ref(0)
  
  // 方法
  function changeTemp() {
    temp.value += 10
  }
  
  function changeHeight() {
    height.value += 10
  }
  
  // 监视 -- watch实现
  watch([temp, height], (value) => {
    let [temp, height] = value
    if (temp >= 60 || height >= 80) {
      consloe.log('给服务器发请求')
    }
  })
  
  // 监视 -- watchEffect实现
  watchEffect(() =>{
    if (temp.value >= 60 || height.value >= 80) {
      consloe.log('给服务器发请求')
    }
  })
</script>
```

