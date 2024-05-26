# vue2 与 vue3的生命周期



## 组件的生命周期

**概念**：Vue组件实例在创建时要经历一系列的初始化步骤，在过程中Vue会在合适的时机，调用特定的函数，从而让开发者有机会在特定阶段运行自己的代码，这些特定的函数就是：生命周期钩子

* 创建
* 挂载
* 更新
* 销毁

每个阶段都有两个钩子，一前一后

常用的钩子：onMounted、onUpdated、onBeforeUnmount

## vue2的生命周期

```vue
<template>
  <div>
    sum的值: {{sum}}
  </div>
	<button @click="changeSum">
    点我+1
  </button>
</template>
<script>
  export default {
    name: Person,
    
    data() {
      return {
        let sum = 0
      }
    },
    methods() {
      function changeSum(){
        this.sum += 1
      }
    },
    // 创建前的钩子
    beforeCreate() {
      console.log('创建前')
    },
    // 创建完成的钩子
    created() {
      console.log('创建完成')
    },
    // 挂载前的钩子
    beforeMount() {
      console.log('挂载前')
    },
    // 挂载完成的钩子
    mounted() {
      console.log('挂载完成')
    },
    // 更新前的钩子
    beforeUpdate() {
      console.log('更新前')
    },
    // 更新完成的钩子
    updated() {
      console.log('更新完成')
    },
    // 销毁前的钩子
    beforeDestroy() {
      console.log('销毁前')
    },
    // 销毁完成的钩子
    destroyed() {
      console.log('销毁完成')
    }, 
  }
</script>
```



## vue3的生命周期

```vue
<template>
  <div>
    sum的值: {{sum}}
  </div>
	<button @click="changeSum">
    点我+1
  </button>
</template>
<script setup name='Person'>
  import {ref, onBeforeMount, onMounted, onBeforeUpdate, onUpdated, onBeforeUnmounted, onUnmounted} from 'vue'
  let sum = ref(0)
  function changeSum(){
     sum += 1
  }
  console.log('创建') // vue3 中setup起到了创建的作用
  // 挂载前的钩子
  onBeforeMount(()=> {
    console.log('挂载前')
  }),
  // 挂载完成的钩子
  onMounted(()=> {
    console.log('挂载完成')
  }),
  // 更新前的钩子
  onBeforeUpdate(()=> {
    console.log('更新前')
   }),
  // 更新完成的钩子
  onUpdated(()=> {
    console.log('更新完成')
  }),
  // 销毁前的钩子
  onBeforeUnmount(()=> {
    console.log('销毁前')
  }),
  // 销毁完成的钩子
  onUnmounted(()=> {
    console.log('销毁完成')
  })

</script>
```



## vue组件加载过程

入口函数——> 根组件——>子组件——>子子组件

入口函数找到根组件发现存在子组件，要先把子组件加载完父组件才能继续加载，加载子组件的，发现子组件也存在组件，也要先把子子组件加载完成才能继续加载。

故组件加载过程为：子子组件——>子组件——>根组件

