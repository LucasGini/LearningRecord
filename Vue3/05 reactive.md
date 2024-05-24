# reactive

## reactive概述

* **作用**：定义一个响应式对象(基本类型不要用它，要用ref，否则报错)
* **语法**：let 响应式对象 = reactive(源对象)。
* **返回值**：一个Proxy的实例对象，简称响应式对象
* **注意点**：reactive定义的对象是深层次的，可以对多层嵌套的对象进行响应式



## reactive创建对象类型响应式数据

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
import {reactive} from 'vue'

// 创建对象类型响应式数据
let person = reactive({
  name: '张三',
  age: 18,
  tel: '13423233332'
})

// 点击事件函数
function changeName() {
  person.name = '李四'
  console.log(person.name) // 控制台显示 李四 页面数据姓名变更为李四
}

// 点击事件函数
function changeAge() {
  person.age += 1
  console.log(person.age) // 控制台显示 19 页面数据年龄变更为19
}

// 点击事件函数
function changeObj() {
  person = reactive({
      name: '王五',
      age: 30,
      tel: '15999999999'
    }）
  console.log(person) // 控制台显示 Proxy(Object){name: '王五', age: 30, tel: '15999999999'}，页面数据不变更，原因是该点击后，person指向的对象不是原对象了，而是新的对象，但是页面数据跟原对象形成绑定关系，原对象的数据没有变更，所以页面数据不变更
}

</script>
```



