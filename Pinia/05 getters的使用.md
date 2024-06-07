# getters的使用

* 当state中的数据要经过处理后再使用时，可以使用getters配置



## 数据仓库

@/store/count.js

```javascript
import {defineStore} from 'pinia'
import {ref} from 'vue'

const useConutStore = defineStore('conut', {
  state() {
    return {
      sum: 0
      company: 'tx'
      address: '深圳南山粤海街道'
    }
  },
 actions: {
    add(value){
    this.sum += value
  }
  
    minus(value){
    this.sum -= value
  }
  },
 getters: {
  bigSum:state => state.sum * 10,
  upperCompany(){
    return this.company.toUpperCase()
  }
 }
})
```



## 组件1

@/components/Conut.vue

```vue
<template>
  <div class="conut">
    <h3>当前求和为：{{ sum }}</h3>
    <select v-model.number='n'>
      <option value="1">1</option>
      <option value="3">3</option>
      <option value="6">6</option>
    </select>
    <button @click="add">加</button>
    <button @click="minus">减</button>
  </div>
</template>
<script setup name="Count">
  import {ref} from 'vue'
  import {useCount} from '@/store/count.js'
  import {storeToRefs} from 'pinia'
  
  countSotre = useCount()
  
  let {sum, company, address, bigSum, upperCompany} = storeToRefs(countSotre)
  
  function add() {
    countSotre.add(n.value)
  }
</script>
```

