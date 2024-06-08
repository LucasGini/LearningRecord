# shallowRef与shallowReactive



## shallowRef

* 作用：创建一个响应式数据，但只对顶层属性进行响应式处理

* 用法：

  ```vue
  <script>
    let myVar = shallowRef(initiaValue)
  </script>
  ```

* 特点：只跟踪引用值的变化，不关心值内部的属性变化



## shallowReactive

* 作用：创建一个浅层响应式对象，只会对对象的最顶层属性变成响应式的，对象内部的嵌套属性则不会变成响应式的

* 用法：

  ```vue
  <script>
    let myObj = shallowshallowReactive({......})
  </script>
  ```

* 特点：对象的顶层属性是响应式的，但是嵌套的对象属性不是



## 总结

通过使用shallowRef()和shallowReactive()来绕开深度响应，浅层式API创建的状态只在其顶层是响应式的，对所有深层的对象不做任何处理，避免了对每一个内部属性做响应式所代理的性能成本，这使得属性的访问变的更快，可提升性能



## shallowRef例子

@/App.vue

```vue
<template>
	<div>
    <h2>人物信息</h2>
    <span>求和：{{sum}}</span>
    <span>姓名:{{person.name}}</span>
    <span>年龄:{{person.age}}</span>
    <button @click="add">+1</button>
    <button @click="changeName">变更姓名</button>
    <button @click="changeAge">变更年龄</button>
    <button @click="changeObj">变更整个对象</button>
  </div>
</template>
<script setup>
import {shallowRef} from 'vue'
  
// 创建基本类型浅层响应式数据
let sum = shallowRef(18)

// 创建对象类型浅层响应式数据
let person = shallowRef({
  name: '张三',
  age: 18
})

// 点击事件函数
function add() {
  sum.value += 1
  console.log(person.value.name) // 控制台显示 19 页面数据求和变更为19
}

// 点击事件函数
function changeName() {
  person.value.name = '李四'
  console.log(person.value.name) // 控制台显示 李四 页面数据姓名无变更
}

// 点击事件函数
function changeAge() {
  person.value.age += 1
  console.log(person.value.age) // 控制台显示 19 页面数据年龄无变更
}

// 点击事件函数
function changeObj() {
  person.value = {
      name: '王五',
      age: 30,
    }
  console.log(person.value) // 控制台显示 {name: '王五', age: 30}，页面数据也变更
}

</script>
```



## shallowReactive例子

@/App.vue

```vue
<template>
	<div>
    <h2>汽车信息</h2>
    <span>品牌：{{car.barnd}}</span>
    <span>颜色:{{car.options.color}}</span>
    <span>发动机:{{car.options.engine}}</span>
    <button @click="changeBrand">变更汽车品牌</button>
    <button @click="changeColor">变更汽车颜色</button>
    <button @click="changeEngine">变更汽车发动机</button>
    <button @click="changeOptions">变更汽车配置</button>
    <button @click="changeCar">变更整个汽车</button>
  </div>
</template>
<script setup>
import {shallowReactive} from 'vue'

// 创建对象类型浅层响应式数据
let car = shallowReactive({
  barnd: '宝马',
  options: {
    color: '红色',
    engine: 'v8'
  }
})

// 点击事件函数
function changeBrand() {
  car.barnd = "奥迪"
  console.log(car.barnd) // 控制台显示 奥迪 页面数据汽车品牌变更为奥迪
}

// 点击事件函数
function changeColor() {
  car.options.color = '黑色'
  console.log(car.options.color) // 控制台显示 黑色 页面汽车颜色无变更
}

// 点击事件函数
function changeEngine() {
  car.options.engine = 'v4'
  console.log(car.options.engine) // 控制台显示 v4 页面数据汽车发动机无变更
}

// 点击事件函数
function changeOptions() {
  car.options = {
    color: '蓝色',
    engine: 'v12'
  }
  console.log(car.options) // 控制台显示 { color: '蓝色', engine: 'v12' }，页面数据汽车颜色、汽车发动机都变更
}

// 点击事件函数
function changeCar() {
  car = {
  barnd: '奔驰',
  options: {
    color: '白色',
    engine: 'v3'
  }
}
  console.log(car) // 控制台显示 {barnd: '奔驰',options: {color: '白色',engine: 'v3'}}，页面数据无变更
}
</script>
```

