# 自定义Hooks



## 概述

* 真正意义上的实现组合式Api
* **作用**：将同类型的方法和数据同一放到一个文件，然后主文件进行引用



## 例子

* 求和

@/hooks/useSum.js

```javascript
import {, computed, onMounted} from 'vue'

let sum = ref(0)
let bigSum = ref(0)

// 计算属性
computed(() => {
  sum.value = sum.value * 10
})

// 自增
function add() {
  sum.value += 1
}

// 挂载后，调用add()函数
onMounted(() => {
  add()
})

// 给外部提供数据和方法
return {sum, bigSum, add}
```



* 获取狗的照片

@/hooks/useDog.js

```javascript
import {reactive, computed, onMounted} from 'vue'
import {axios} from 'axios'

let dogList = reactive([
  'https://images.dog.ceo/breeds/pembroke/n02113023_4373.jpg'
])


// 获取狗的照片并存到dogList
async function getDog() {
  try{
    let result = await axios.get('https://images.dog.ceo/breeds/pembroke/images')
    dogList.push(result.data.message)
  } catch (error) {
    alert(error)
  }
}

// 挂载后，调用getDog()函数
onMounted(() => {
  getDog()
})

// 给外部提供数据和方法
return {dogList, getDog}

```



* 组合

@/components/Combination.vue

```vue
<tamplate>
  <div class='combination'>
    <h2>
      当前求和为：{{sum}}, 乘10为{{bigSum}}
      <button @click="add">
        点我+1
      </button>
    </h2>
    <hr>
    <img v-for="(dog, index) in dogList" :src="dog" :key="index">
    <button @click="getDog">
        再来一张
    </button>
  </div>
</tamplate>
<script setup name="Combination">
  import useSum from '@/hooks/useSum.js'
  import useDog from '@/hooks/useDog.js'
  
  const {sum, bigSum, add} = useSum()
  const {dogList, getDog} = useDog()
</script>
```





