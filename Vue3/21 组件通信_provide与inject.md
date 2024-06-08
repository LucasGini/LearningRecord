# 组件通信——provide与inject



**概述**：实现祖孙组件直接通信，不需要通过中间组件传递数据

**具体使用**：

* 在祖先组件中通过provide配置向后代组件提供数据
* 在后代组件中通过inject配置来声明接收数据



## 父组件

@/provideAndInject/Father.vue

```vue
<template>
  <div class="father">
    <h3>父组件</h3>
    <h4>存款：{{money}}</h4>
    <h4>车：{{car}}</h4>
    <Child/>
  </div>
</template>
<script setup name="Father">
  import {Child} from './Child.vue'
  import {ref, reactive, provide} from 'vue'
  
  // 数据
  let money = ref(100)
  let car = reactive({
    brand: '奥迪',
    prcie: 100
  })
  
  // 方法 
  function updateMoney(value) {
    money.value -= value
  }
  
  // 向后代提供数据
  provide('moneyContext', {money, updateMoney})
  provide('car', car)
  
</script>
```



## 子组件

@/provideAndInject/Child.vue

```vue
<template>
  <div class="child">
    <h3>子组件</h3>
    <GrandChild/>
  </div>
</template>
<script setup name="Child">
  import {GrandChild} from '@/attrs/GrandChild.vue'
  
</script>
```



## 孙组件

@/provideAndInject/GrandChild.vue

```vue
<template>
  <div class="grand-child">
    <h3>孙组件</h3>
    <h4>爷爷的钱：{{money}}</h4>
    <h4>爷爷的车：{{car}}</h4>
    <button @click="updateMoney(6)">减少爷爷的钱</button>
  </div>
</template>
<script setup name="GrandChild">
  import {inject} from 'vue'
  
  // 接收上级组件的数据
  // 第二个参数是默认值
  let {money, updateMoney} = inject('moneyContext', {money:333, updateMoney:(value) => {}})
  let car = inject('car', {brand:'未知', price: 0})
  
</script>
```

