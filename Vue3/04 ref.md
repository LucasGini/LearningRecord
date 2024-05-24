

# ref



## ref概述

* **作用**：定义响应式变量
* **语法**：let xxx = ref(初始值)
* **返回值**：一个RefImpl的实例对象，简称ref对象或ref，ref对象的value属性是响应式的
* **注意点**：
  * JS中操作数据需要：xxx.value，但是模板中不需要.value，直接使用即可
  * 对于let name = ref('张三')来说，name不是响应式的，name.value是响应式的。



## Ref创建基本类型的响应式对象

```vue
<template>
	<div>
  <span>{{refStr}}</span>
  <span>{{refNum}}</span>
  <span v-show="refBool">{{refBool}}</span>
  <button @click=changeRef>变更响应式变量</button>
   <span>{{str}}</span>
  <span>{{num}}</span>
  <span v-show="bool">{{bool}}</span>
  <button @click=changeCommon>变更普通变量</button>
  </div>
</template>
<script setup>
import {ref} from 'vue'
  
// 定义普通变量
let str = ''
let num = 122
let bool = true

// 定义基本类型响应式变量
let refStr = ref('')
let refNum = ref(2)
let refBool = ref(true)

// 点击事件函数，变更数据
function changeCommon() {
  str = 'abc'
  num = 123
  bool = false
  console.log(str, num, bool = false)  // 输出 'abc' 123 false, 但是页面展示的数据没有变更，且没有隐藏refBool的展示
}

// 点击事件函数，变更数据
function changeRef() {
  refStr.value = 'abc'
  refNum.value = 123
  refBool.value = false
  console.log(refStr.value, refNum.value, refBool.value)  // 控制台输出 'abc' 123 false, 页面展示的数据也变更了，隐藏了refBool的展示
}

</script>
```



## ref创建对象类型响应式数据

``` vue
<template>
	<div>
    <h2>人物信息</h2>
    <span>姓名:{{person.name}}</span>
    <span>年龄:{{person.age}}</span>
    <span>电话:{{person.tel}}</span>
    <button @click="changeName">变更姓名</button>
    <button @click="changeAge">变更年龄</button>
    <button @click="changeObj">变更整个对象</button>
  </div>
</template>
<script setup>
import {ref} from 'vue'

// 创建对象类型响应式数据
let person = ref({
  name: '张三',
  age: 18,
  tel: '13423233332'
})

// 点击事件函数
function changeName() {
  person.value.name = '李四'
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
      tel: '15999999999'
    }）
  console.log(person.value) // 控制台显示 Proxy(Object){name: '王五', age: 30, tel: '15999999999'}，页面数据也变更
  // 为什么与reactive定义的对象不一样呢？原因是该点击后，变更的是person的vlaue属性，person指向的对象没有变化，所以页面数据也会变化
}

</script>
```

