# 标签的ref属性

**作用** ：用于注册模板引用。

* 用于普通DOM标签上，获取的是DOM节点
* 用在组件标签上，获取的是组件实例对象

``` vue
// person.vue
<template>
  <div>
    <span>{{a}}</span>
    <span>{{b}}</span>
    <span>{{c}}</span>
  </div>
</template>
<script setup>
  import {ref, defineExpose} from 'vue'
  
  let a = ref(0)
  let b = ref(1)
  let c = ref(2)
  
  // 开放数据a，b让父组件可以通过ref标记获取实例并访问
  defineExpose({a, b})
</script>
```



```vue
<template>
  <div>
    <span>中国</span>
    <span ref="title">深圳</span>
    <span>宝安</span>
    <person ref="title"></person>
    <button @click="showLog">点我输出ref=title这个元素</button>
    <button @click="showLog1">点我输出ref=title1这个元素</button>
  </div>
</template>
<script setup>
  import {ref} from 'vue'
  
  // 创建一个title，用于存储ref标记的内容
  let title = ref()
  
  // 用于普通DOM标签上，获取的是DOM节点
  function showLog() {
    console.log(title.vlaue)
  }
  
  // 创建一个title1，用于存储ref标记的内容
  let title1 = ref()
  
  // 用在组件标签上，获取的是组件实例对象
  // 父组件可以通过title1.vlaue获取组件的属性，但是前提是子组件开放访问
  function showLog1() {
    console.log(title1.vlaue)
  }
</script>
```



## 局部样式

vue 文件中，当style标签加上scoped，表示这是一个局部样式，仅当前文件适用。删除后就是全局样式，全局适用

```vue
<template>
  ...
</template>
<script setup>
  ...
</script>
<style scoped>
</style>
```

