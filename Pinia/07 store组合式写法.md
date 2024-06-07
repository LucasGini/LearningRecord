# store组合式写法





## 数据仓库

@/store/count.js

```javascript
import {defineStore} from 'pinia'
import {ref, computed} from 'vue'

const useConutStore = defineStore('conut', () => {
  const sum = ref(0)
  const company = ref('tx')
  const address = ref('深圳南山粤海街道')
  
  function add(value){
    this.sum += value

  
  function minus(value){
    this.sum -= value
  }
    
  const bigSum = computed(() => sum.value * 10),
  const upperCompany = computed(() =>{
    return company.value.toUpperCase()
  })
 
  return {
    sum,
    company,
    address,
    add,
    minus,
    bigSum,
    upperCompany
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

