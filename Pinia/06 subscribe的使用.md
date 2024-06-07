# subscribe的使用



* 类似于watch监视



## 数据仓库

@/store/loveTalk.js

```javascript
import {defineStore} from 'pinia'
import {reactive} from 'vue'
import axios from 'axios'
import nanoid from 'nanoid'

const useLoveTalkStore = defineStore('loveTalk', {
  state() {
    return {
      LoveTalkList: JSON.parse(localStorage.getItem('loveTalkList')) || []
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
  
  // 监视loveTalkStore的数据变化
  loveTalkStore.$subscribe((mutate, state) => {
    console.log('loveTalkStore里面保存的数据发生了变化', mutate, state)
    // 将数据保存到本地内存
    localStorage.setItem('loveTalkList', JSON.stringify(state.loveTalkList))
  })
</script>
```

