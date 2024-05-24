# toRefs与toRef



## 概述

* **作用**：将一个响应式对象中的每一个属性，转换为ref对象
* **备注**：toRefs与toRef功能一致，但toRefs可以批量转换



## toRefs的使用

``` vue
<template>
	<div>
    <h2>人物信息</h2>
    <span>姓名:{{person.name}}</span>
    <span>年龄:{{person.age}}</span>
    <span>电话:{{person.tel}}</span>
    <button @click="changeName">变更姓名</button>
    <button @click="changeAge">变更年龄</button>
  </div>
</template>
<script setup>
import {reactive, toRefs} from 'vue'

// 创建对象类型响应式数据
let person = reactive({
  name: '张三',
  age: 18,
  tel: '13423233332'
})

// 解构赋值,这种方式解构的是普通变量
// let {name, age} = person

// 解构赋值,这种方式解构的是ref响应式变量
let {name, age} = toRefs(person)

// 点击事件函数
function changeName() {
  name.value = '李四'
  // 使用toRefs解构修改name person.name也变更
  console.log(person.name) // 控制台显示 李四 使用toRefs解构的页面数据姓名变更为李四，不使用的则不变
}

// 点击事件函数
function changeAge() {
  age.value += 1
  // 使用toRefs解构修改age person.age也变更
  console.log(person.age) // 控制台显示 19 使用toRefs解构的页面数据年龄变更为19，不使用的则不变
}

</script>
```



## toRef的使用

``` vue
<template>
	<div>
    <h2>人物信息</h2>
    <span>姓名:{{person.name}}</span>
    <span>年龄:{{person.age}}</span>
    <span>电话:{{person.tel}}</span>
    <button @click="changeName">变更姓名</button>
    <button @click="changeAge">变更年龄</button>
  </div>
</template>
<script setup>
import {reactive, toRefs} from 'vue'

// 创建对象类型响应式数据
let person = reactive({
  name: '张三',
  age: 18,
  tel: '13423233332'
})


// 这种方式返回的是ref响应式变量，与toRefs的区别是，toRef是从对象中一个一个的转换，而toRefs则是批量
let name = toRef(person, 'name')
let age = toRef(person, 'age')

// 点击事件函数
function changeName() {
  name.value = '李四'
  // 使用toRef解构修改name person.name也变更
  console.log(person.name) // 控制台显示 李四 使用toRef解构的页面数据姓名变更为李四，不使用的则不变
}

// 点击事件函数
function changeAge() {
  age.value += 1
  // 使用toRef解构修改age person.age也变更
  console.log(person.age) // 控制台显示 19 使用toRef解构的页面数据年龄变更为19，不使用的则不变
}

</script>
```



