# 组件通信——$refs与$parent



**概述**：

* $refs用于：父-->子
* $parent用于：子-->父

**原理**：

* $refs：值为对象，包含所有被ref属性标识的dom元素或组件实例，父组件需要访问子组件的数据需要子组件进行授权（使用defineExpose）
* $parent：值为对象，当前组件的父组件实例对象



## 父组件

@/refsAndParent/Father.vue

```vue
<template>
  <div class="father">
    <h3>父组件</h3>
    <h4>房产： {{house}}</h4>
    <button @click="changeToy">修改孩子1的玩具</button>
    <button @click="changeComputer">修改孩子2的电脑</button>
    <button @click="addBookToAllChild($refs)">给所有孩子增加两本书</button>
    <Child1 ref="c1"/>
    <Child2 ref="c2"/>
  </div>
</template>
<script setup name="Father">
  import {Child1} from './Child1.vue'
  import {Child2} from './Child2.vue'
  import {ref} from 'vue'
  
  // 数据
  let house = ref('')
  let c1 = ref()
  let c2 = ref()
  
    
  // 向外部提供数据
  defineExpose({house})

  // 方法
  // 修改子组件1的toy
  function changeToy() {
    c1.value.toy = '芭比娃娃'
  }
  
  // 修改子组件2的computer
  function changeComputer() {
    c2.value.computer = '华为'
  }
  
  // 修改所有子组件的book
  function addBookToAllChild(refs) {
    for (let key in refs) {
      refs[key].book += 2
    }
  }
</script>
```



## 子组件1

@/refsAndParent/Child1.vue

```vue
<template>
  <div class="child1">
    <h3>子组件</h3>
    <h4>玩具：{{toy}}</h4>
    <h4>书：{{book}}</h4>
    <button @click="minusFatherHouse($parent)">减少父亲的房产</button>
  </div>
</template>
<script setup name="Child1">
  import {ref} from 'vue'
  
  // 数据
  let toy = ref('奥特曼')
  let book = ref(4)
  
  // 向外部提供数据
  defineExpose({toy, book})

  // 方法
  function minusFatherHouse(parent) {
    parent.house -= 1
  }
  
</script>
```



## 子组件2

@/refsAndParent/Child2.vue

```vue
<template>
  <div class="child2">
    <h3>子组件</h3>
    <h4>玩具：{{toy}}</h4>
    <h4>电脑：{{computer}}</h4>
  </div>
</template>
<script setup name="Child2">
  import {ref} from 'vue'
  
  // 数据
  let computer = ref('苹果')
  let book = ref(6)
  
  // 向外部提供数据
  defineExpose({computer, book})

</script>
```

