# storeToRefs



* 借助storeToRefs将store中的数据转为ref对象，方便在模板中使用
* 对比与使用toRefs，storeToRefs只会关注store中的数据，不会对方法进行ref包裹



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
  }
 actions: {
    add(value){
    this.sum += value
  }
  
    minus(value){
    this.sum -= value
  }
  }
})
```



@/store/loveTalk.js

```javascript
import {defineStore} from 'pinia'
import {reactive} from 'vue'
import axios from 'axios'
import nanoid from 'nanoid'

const useLoveTalkStore = defineStore('loveTalk', {
  state() {
    return {
      LoveTalkList: [
          {id:'werqwerq01', title: '今天天气真好'}
      ]
    }
  },
  actions: {
    async getLoveTalk() {
      // 连续解构加重命名
      let {data: {content:title }} = await axios.get('https://api.uomg.com/api/rand.qinghua?format=json')
      let obj = {id: nanoid(), title}
      this.loveTalkList.unshift(obj)
  }}
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
  
  let {sum, company, address} = storeToRefs(countSotre)
  
  function add() {
    countSotre.add(n.value)
  }
</script>
```



## 组件2

@/components/LoveTalk.vue

```vue
<template>
  <div class="love-talk">
    <button @click="getLoveTalk">获取一句话</button>
    <ul>
      <li v-for="talk in loveTalkList" :key="talk.id">{{ talk.title }}</li>
    </ul>
  </div>
</template>
<script setup name="LoveTalk">
  import {reactive} from 'vue'
  import {useLoveTalk} from 'loveTalk.js'
  import axios from 'axios'
  import nanoid from 'nanoid'
  import {storeToRefs} from 'pinia'
  
  loveTalkStore = useLoveTalk()
  let {loveTalkList} = storeToRefs(loveTalkStore)
  
  function getLoveTalk() {
    loveTalkStore.getLoveTalk
  }
</script>
```

