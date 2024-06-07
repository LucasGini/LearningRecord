# 组件通信——$attrs



**概述**：$attrs用于实现当前组件的父组件，向当前组件的子组件通信（祖→孙）

**具体说明**：$attrs是一个对象，包含所有父组件传入的标签属性

**注意**：$attrs会自动排除props中声明的属性（可以认为声明过的props被子组件自己消费了）



## 父组件

@/attrs/Father.vue

```vue
<template>
  <div class="father">
    <h3>父组件</h3>
    <h4>a：{{a}}</h4>
    <h4>b：{{b}}</h4>
    <h4>c：{{c}}</h4>
    <h4>d：{{d}}</h4>
    <!-- v-bind="{b, c, d}" 等价于 :b="b" :c="c" :d="d" -->
    <Child :a="a" v-bind="{b, c, d}" :updateA="updateA"/>
  </div>
</template>
<script setup name="Father">
  import {Child} from './Child.vue'
  import {ref} from 'vue'
  
  // 数据
  let a = ref(1)
  let b = ref(2)
  let c = ref(3)
  let d = ref(4)
  
  // 方法
  function updateA(value) {
    a.value += value
  }
</script>
```



## 子组件

@/attrs/Child.vue

**注意**：下方代码由于a已经声明接收了，所以$attrs为{b:2, c:3, d:4, updateA: function updateA}

```vue
<template>
  <div class="child">
    <h3>子组件</h3>
    <h4>a：{{a}}</h4>
    <GrandChild v-bind="$attrs"/>
  </div>
</template>
<script setup name="Child">
  import {ref} from 'vue'
  import {GrandChild} from '@/attrs/GrandChild.vue'
  
  // 声明接收props
  let props = defineProps(['a'])
  
  // 数据
  let a = ref(props.a)

  
</script>
```



## 孙组件

@/attrs/GrandChild.vue

```vue
<template>
  <div class="child">
    <h3>孙组件</h3>
    <h4>b：{{b}}</h4>
    <h4>c：{{c}}</h4>
    <h4>d：{{d}}</h4>
    <button @click="handleUpdateA">
      点击更新Father.vue的a
  </button>
  </div>
</template>
<script setup name="GrandChild">
  import {ref} from 'vue'
  
  // 声明接收props
  let props = defineProps(['b', 'c', 'd', 'updateA'])
  
  // 数据
  let b = ref(props.b)
  let c = ref(props.c)
  let d = ref(props.d)
  
  // 方法
  function handleUpdateA() {
    props.updateA(6)
  }

  
</script>
```

