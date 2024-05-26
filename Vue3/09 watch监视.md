# watch监视



## watch概述

* **作用**：监视数据的变化
* **特点**：Vue3中的watch只能监视以下四种数据
  1. ref定义的数据
  2. reactive定义的数据
  3. 函数返回一个值(getter函数)
  4. 一个包含上述内容的数组



## 情况一

监视ref定义的【基本类型】数据：直接写数据名即可，监视的是其value值的改变

``` vue
<template>
  <div class='sum'>
    <span>当前求和为{{sum}}</span>
    <button @click="changeSum">
      点我sum+1
  </button>
  </div>
</template>
<script setup>
import {ref, watch} from 'vue'

// 数据
let sum = ref(2)

// 方法
function changeSum() {
  sum.value += 1
}

// 监视，watch返回的是停止监视的函数
const stopWatch = watch(sum, (newValue, oldValue) => {
  console.log('sum变化了', newValue, oldValue)
  if (newValue > 10) {
    console.log('sum大于10了')
    // 调用watch的返回值，停止监视
    stopWatch()
  }
})
  
</script>
```



## 情况二

监视ref定义的对象类型数据，直接写数据名。监视的是对象的地址值，如需要监视对象内部的数据，需要手动开启深度监视

注意：

* 若修改的是ref定义的对象中的属性，newValue和oldValue都是新值，因为他们是同一个对象
* 若修改整个ref定义的对象，newValue是新值，oldValue是旧值，因为不是同一个对象了



```vue
<template>
  <div class='watch'>
    <span>姓名:{{person.name}}</span>
    <span>年龄:{{person.age}}</span>
    <button @click="changeName">变更姓名</button>
    <button @click="changeAge">变更年龄</button>
    <button @click="changeObj">变更整个对象</button>
  </div>
</template>
<script setup>
import {ref, watch} from 'vue'

// 数据
let person = ref({
  name:'张三',
  age: 18
})

// 点击事件函数
function changeName() {
  person.value.name += '~'
  console.log(person.value.name) // 控制台显示 李四 页面数据姓名变更为李四
}

// 点击事件函数
function changeAge() {
  person.value.age += 1
  console.log(person.value.age) // 控制台显示 19 页面数据年龄变更为19
}
  
// 点击事件函数
function changeObj() {
  person.value = reactive({
      name: '王五',
      age: 30,
    }）
}
  
// 这种情况监视的是对象的地址值, person对象整个变了才会监视，如点击变更整个对象按钮，后台才会打印数据
watch(person, (newValue, oldValue) => {
  consloe.log('person变化了'，newValue, oldValue)
}) 
  
// 手动开启了深度监视{deep: true}， 修改对象里面的属性也会监视了，但是newValue和oldValue都是新值，因为他们是同一个对象
// watch的第一个参数是：被监视的对象
// watch的第二个参数是：监视的回调
// watch的第三个参数是：配置对象（deep，immediate等等...）
watch(person, (newValue, oldValue) => {
  consloe.log('person变化了'，newValue, oldValue)
}, {deep: true, immediate: true})  //开启深度监视{deep: true}  // 组件加载完后，先执行一次{immediate: true}
</script>
```



## 情况三

监视reactive定义的对象类型数据，且默认开启了深度监视

``` vue
<template>
  <div class='watch'>
    <span>姓名:{{person.name}}</span>
    <span>年龄:{{person.age}}</span>
    <button @click="changeName">变更姓名</button>
    <button @click="changeAge">变更年龄</button>
    <button @click="changeObj">变更整个对象</button>
  </div>
</template>
<script setup>
import {reactive, watch} from 'vue'

// 数据
let person = reactive({
  name:'张三',
  age: 18
})

// 点击事件函数
function changeName() {
  person.name += '~'
  console.log(person.name) // 控制台显示 李四 页面数据姓名变更为李四
}

// 点击事件函数
function changeAge() {
  person.age += 1
  console.log(person.age) // 控制台显示 19 页面数据年龄变更为19
}
  
// 点击事件函数
function changeObj() {
  Object.assign(person, {
  name:'李四',
  age: 19
})
}

// 监视reactive定义的对象类型数据，默认开启了深度监视，且不可关闭，所以改对象属性也会触发监视
watch(person, (newValue, oldValue) => {
  consloe.log('person变化了'，newValue, oldValue) // 由于对象地址没变，所以newValue和oldValue是一样的
}) 

</script>
```



## 情况四

监视ref或reactive 定义的对象类型数据中的某个属性，注意点如下：

1. 若该属性不是对象类型，需要写成函数形式
2. 若该属性值依然是对象类型，可以直接编，也可以写成函数，建议写成函数

结论：监视的是对象里的属性，最好写成函数式，注意点：若是对象监视的是地址值，需要关注对象内部，需要手动开启深度监视

``` vue
<template>
  <div class='watch'>
    <span>姓名:{{person.name}}</span>
    <span>年龄:{{person.age}}</span>
    <span>汽车:{{person.car.c1}}、{{person.car.c2}}</span>
    <button @click="changeName">修改姓名</button>
    <button @click="changeAge">修改年龄</button>
    <button @click="changeCar1">修改第一台车</button>
    <button @click="changeCar2">修改第二台车</button>
    <button @click="changeCar">修改第整个车</button>
  </div>
</template>
<script setup>
import {reactive, watch} from 'vue'

// 数据
let person = reactive({
  name:'张三',
  age: 18,
  car: {
    c1: '奔驰',
    c2: '宝马'
  }
})

// 点击事件函数
function changeName() {
  person.name += '~'
  console.log(person.name) // 控制台显示 李四 页面数据姓名变更为李四
}

// 点击事件函数
function changeAge() {
  person.age += 1
  console.log(person.age) // 控制台显示 19 页面数据年龄变更为19
}
  
// 点击事件函数
function changeCar1() {
  person.car.c1 = '奥迪'
}
  
// 点击事件函数
function changeCar2() {
  person.car.c2 = '大众'
}
  
// 点击事件函数
function changeCar() {
  person.car = {c1: '雅迪' ,c2: '爱玛'}
}

// 监视，由于person的属性值name是基本类型的，所以不能直接监视，需要包一层函数并返回(getter函数：一个函数返回一个值)
watch(() => person.name, (newValue, oldValue) =>{
  console.log('person的name属性发生变化了', newValue, oldValue)  // 点击修改姓名，触发监视
})
  
// 监视,由于person的属性值car是对象类型，可以直接监视，但是建议包一层函数并返回(getter函数：一个函数返回一个值)
// 直接编写能监视car对象属性，但是整个car对象换了就无法监视了；
// 包成getter函数后，不加上{deep: true}配置，无法监视car对象属性的变更，但是能监视整个car对象的变更
// 包成getter函数后，加上{deep: true}配置，能监视car对象属性，也可以监视整个car对象的变更
watch(() => person.car, (newValue, oldValue) => {
  console.log('person的car属性发生变化了', newValue, oldValue  
}, {deep: true})

</script>
```



## 情况五

监视上述多个数据

```vue
<template>
  <div class='watch'>
    <span>姓名:{{person.name}}</span>
    <span>年龄:{{person.age}}</span>
    <span>汽车:{{person.car.c1}}、{{person.car.c2}}</span>
    <button @click="changeName">修改姓名</button>
    <button @click="changeAge">修改年龄</button>
    <button @click="changeCar1">修改第一台车</button>
    <button @click="changeCar2">修改第二台车</button>
    <button @click="changeCar">修改第整个车</button>
  </div>
</template>
<script setup>
import {reactive, watch} from 'vue'

// 数据
let person = reactive({
  name:'张三',
  age: 18,
  car: {
    c1: '奔驰',
    c2: '宝马'
  }
})

// 点击事件函数
function changeName() {
  person.name += '~'
  console.log(person.name) // 控制台显示 李四 页面数据姓名变更为李四
}

// 点击事件函数
function changeAge() {
  person.age += 1
  console.log(person.age) // 控制台显示 19 页面数据年龄变更为19
}
  
// 点击事件函数
function changeCar1() {
  person.car.c1 = '奥迪'
}
  
// 点击事件函数
function changeCar2() {
  person.car.c2 = '大众'
}
  
// 点击事件函数
function changeCar() {
  person.car = {c1: '雅迪' ,c2: '爱玛'}
}

// 监视，监视上述多个数据，包在数组里
watch([() => person.name, () => person.car.c1], (newValue, oldValue) => {
  console.log('person发生变化了', newValue, oldValue )  // 点击修改姓名，点击修改修改第一台车触发监视
}, {deep: true})

</script>
```

