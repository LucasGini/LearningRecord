# ref对比reactive

## 对比

**宏观角度看：**

1. ref用来定义：**基本类型数据、对象类型数据**
2. reactive用来定义：**对象类型数据**

**区别：**

1. ref创建的变量必须使用.value（可以使用volar插件自动添加.value）。
2. reactive重新分配一个新对象，会失去响应式(可以使用Object.assign去整体替换)。

**使用原则：**

1. 若需要一个基本类型的响应式数据，必须使用ref。
2. 若需要一个响应式对象，层级不深，ref、reactive都可以。
3. 若需要一个响应式对象，且层级较深，推荐使用reactive。

``` vue
<template>
	<div>
    <h2>人物信息</h2>
    <span>姓名:{{person.name}}</span>
    <span>年龄:{{person.age}}</span>
    <span>电话:{{person.tel}}</span>
    <button @click="changeName">变更姓名</button>
    <button @click="changeAge">变更年龄</button>
    <button @click="changePerson1">变更整个人1</button>
    <button @click="changePerson2">变更整个人2</button>
    <br>
    <h2>车辆信息</h2>
    <span>品牌:{{car.brand}}</span>
    <span>价格:{{car.price}}</span>
    <button @click="changeBrand">变更品牌</button>
    <button @click="changePrice">变更价格</button>
    <button @click="changeCar">变更整个车</button>
  </div>
</template>
<script setup>
import {reactive} from 'vue'

// reactive创建对象类型响应式数据
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
function changePerson1() {
  person = reactive({
      name: '王五',
      age: 30,
      tel: '15999999999'
    }）
  console.log(person) // 控制台显示 Proxy(Object){name: '王五', age: 30, tel: '15999999999'}，页面数据不变更，原因是该点击后，person指向的对象不是原对象了，而是新的对象，但是页面数据跟原对象形成绑定关系，原对象的数据没有变更，所以页面数据不变更
}

// 点击事件函数
function changePerson2() {
  person = Object.assign(person, {
      name: '王五',
      age: 30,
      tel: '15999999999'
    })
  console.log(person) // 控制台显示 Proxy(Object){name: '王五', age: 30, tel: '15999999999'}，页面数据变更，原因是该点击后，person还是指向原对象，所以页面数据也会变化
}
  
// ref创建对象类型响应式数据
let car = ref({
  brand: '奔驰',
  price: 180
})

// 点击事件函数
function changeBrand() {
  car.value.brand = '宝马'
  console.log(car.value.brand) // 控制台显示 宝马 页面数据姓名变更为宝马
}

// 点击事件函数
function changePrice() {
  car.value.price += 10
  console.log(car.value.price) // 控制台显示 190 页面数据年龄变更为190
}

// 点击事件函数
function changeCar() {
  car.value = reactive({
      brand: '宝马',
      price: 190
    }）
  console.log(car.value) // 控制台显示 Proxy(Object){brand: '宝马', price: 190}，页面数据也变更
  // 为什么与reactive定义的对象不一样呢？原因是该点击后，变更的是person的vlaue属性，person指向的对象没有变化，所以页面数据也会变化
}

</script>
```

