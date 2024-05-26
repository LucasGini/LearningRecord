# props的使用



一个TypeScript类型定义

```typescript
//..src/types/index.ts

export interface PersonInter {
  id: string,
  name: string,
  age: number,
  tel?: string
}

// 定义一个数组类型
export type Persons =  PersonInter[]
```





一个父组件App.vue如下：

```vue
<template>
  <Person :list=""/>
</template>
<script lang="ts" setup name="App">
import Person from '/components/Person.vue'
import {reactive} from 'vue'
import {type Persons} from '@/types'

let personList = reactive<Persons>([
  {id: '131233', name: '张三', age: 18},
  {id: '131234', name: '李四', age: 9},
  {id: '131235', name: '王五', age: 3},
])
</script>
```



一个子组件Person.vue如下：

```vue
<template>
 <div>
   <span v-for="item in list" :key="item.id">{{item.name}} -- {{item.age}}</span>
  </div>
</template>
<script setup>
import {defineProps, withDefaults} from 'vue'
import {type Persons} from '@/types'
  

 // 接收父组件传过来的list
 definProps(['list'])
  
 // 接收父组件传过来的list，并保存
  let x = defineProps(['list'])
  console.log(x.list)
  
 // 接收list+限制类型
 definProps<{list:Persons}>()
  
 // 接收list + 限制类型 + 限制必要性 + 指定默认值
 withDefaults(definProps<{list?:Persons}>(), list:() => [{id: '123123', name: '丽丽', age: 22}])
  
</script>
```

