# readonly与shallowReadonly



# readonly

* 作用：用于创建一个对象的深只读副本

* 用法

  ```vue
  <script setup>
    const original = reactive({......})
    const readonlyCopy = redonly(original)
  </script>
  ```

* 特点:

  * 对象的所有嵌套属性都变为只读
  * 任何尝试修改这个对象的操作都会被阻止（在开发模式下，还会在控制台中发出警告）

* 应用场景：

  * 创建不可变状态快照
  * 保护全局状态或配置不被修改



## shallowReadonly

* 作用：与readonly类似，但是只作用于对象的顶层属性

* 用法

  ```vue
  <script setup>
    const original = reactive({......})
    const shallowReadonlyCopy = shallowReadonly(original)
  </script>
  ```

* 特点:

  * 只将对象的顶层属性设置为只读，对象内部的嵌套属性仍然是可以变更的
  * 适用于只需保护对象顶层属性的场景



## readonly例子

@/App.vue

```vue
<template>
	<div>
    <h2>求和信息</h2>
    <span>sum1：{{sum}}</span>
    <span>sum2:{{readonlySum}}</span>
    <button @click="changeSum">修改sum1</button>
    <button @click="changeReadonlySum">修改sum2</button>

  </div>
</template>
<script setup>
import {readonly, ref} from 'vue'

// 创建基本类型响应式数据
let sum = ref(1)

// 创建只读数据
const readonlySum = readonly(sum)

// 点击事件函数
function changeSum() {
  sum.value += 1
  console.log(sum.value) // 控制台显示 2 页面sum1变为2，sum2也变为2, 因为readonlySum是基于sum的，所以sum变更readonlySum也变更
}
</script>
```



## shallowReadonly例子

@/App.vue

```vue
<template>
	<div>
    <h2>汽车信息</h2>
    <span>car1：{{car.barnd}}</span>
    <span>car2:{{shallowReadonlyCar}}</span>
    <button @click="changeBrand1">变更car1汽车品牌</button>
    <button @click="changeColor1">变更car1汽车颜色</button>
    <button @click="changeEngine1">变更car1汽车发动机</button>
    <button @click="changeOptions1">变更car1汽车配置</button>
    <button @click="changeCar1">变更car1整个汽车</button>
    <button @click="changeBrand2">变更car2汽车品牌</button>
    <button @click="changeColor2">变更car2汽车颜色</button>
    <button @click="changeEngine2">变更car2汽车发动机</button>
    <button @click="changeOptions2">变更car2汽车配置</button>
    <button @click="changeCar2">变更car2整个汽车</button>
  </div>
</template>
<script setup>
import {reactive, shallowReadonly} from 'vue'

// 创建对象类型响应式数据
let car = reactive({
  barnd: '宝马',
  options: {
    color: '红色',
    engine: 'v8'
  }
})

// 创建浅层只读数据
const shallowReadonlyCar = shallowReadonly(car)

// 点击事件函数
function changeBrand1() {
  car.barnd = "奥迪"
  console.log(car.barnd) // 控制台显示 奥迪 页面数据car1汽车品牌变更为奥迪, car2汽车品牌也变更为奥迪
}

// 点击事件函数
function changeColor1() {
  car.options.color = '黑色'
  console.log(car.options.color) // 控制台显示 黑色 页面数据car1汽车颜色更为黑色, car2汽车颜色更为黑色
}

// 点击事件函数
function changeEngine1() {
  car.options.engine = 'v4'
  console.log(car.options.engine) // 控制台显示 v4 页面数据car1汽车发动机更为黑色, car2汽车发动机更为黑色
}

// 点击事件函数
function changeOptions1() {
  car.options = {
    color: '蓝色',
    engine: 'v12'
  }
  console.log(car.options) // 控制台显示 { color: '蓝色', engine: 'v12' }，页面数据car1和car2汽车颜色、汽车发动机都变更
}

// 点击事件函数
function changeCar1() {
  car = {
  barnd: '奔驰',
  options: {
    color: '白色',
    engine: 'v3'
  }
}
  console.log(car) // 控制台显示 {barnd: '奔驰',options: {color: '白色',engine: 'v3'}}，页面数据无变更
}
  
// 点击事件函数
function changeBrand2() {
  car.barnd = "奥迪"  // 控制台报错，只读属性无法修改
}
  
// 点击事件函数
function changeColor2() {
  shallowReadonlyCar.options.color = '黑色'
  console.log(shallowReadonlyCar.options.color) // 控制台显示 黑色 页面数据car1汽车颜色更为黑色, car2汽车颜色更为黑色
}

// 点击事件函数
function changeEngine2() {
  shallowReadonlyCar.options.engine = 'v4'
  console.log(shallowReadonlyCar.options.engine) // 控制台显示 v4 页面数据car1汽车发动机更为黑色, car2汽车发动机更为黑色
}

// 点击事件函数
function changeOptions2() {
  shallowReadonlyCar.options = {
    color: '蓝色',
    engine: 'v12'
  }
  // 控制台报错，只读属性无法修改
}

// 点击事件函数
function changeCar2() {
  shallowReadonlyCar = {
  barnd: '奔驰',
  options: {
    color: '白色',
    engine: 'v3'
  }
}
 // 控制台报错，只读属性无法修改
}
</script>
```

