# 组件通信——props

**概述**：props是使用频率最高的一种通信方式，常用于：父←→子

* 若 **父传子**：属性值是非函数
* 若 **子传父**：属性值是函数



## 父组件

@/props/Father.vue

```vue
<template>
  <div class="father">
    <h3>父组件</h3>
    <h4>汽车：{{car}}</h4>
    <h4 v-if='toy'>孩子给的玩具： {{toy}}</h4>
    <Child :car="car" :getToy="getToy"/>
  </div>
</template>
<script setup name="Father">
  import {Child} from './Child.vue'
  import {ref} from 'vue'
  
  // 数据
  let car = ref('奔驰')
  let toy = ref('')
  
  // 方法
  function getToy(value) {
    toy.value = value
  }
</script>
```



## 子组件

@/props/Child.vue

```vue
<template>
  <div class="child">
    <h3>子组件</h3>
    <h4>玩具：{{toy}}</h4>
    <h4 v-if='car'>父亲给的车：{{car}}</h4>
    <button @click="sendToy">把玩具给父亲</button>
  </div>
</template>
<script setup name="Child">
  import {ref} from 'vue'
  
  // 声明接收props
  let props = defineProps(['car', 'sendToy'])
  
  // 数据
  let toy = ref('奥特曼')
  let car = ref(props.car)
  
  // 方法
  // 通过调用父组件的方法，将数据传给父组件
  function sendToy() {
    props.getToy(toy.value)
  }
  
</script>
```

