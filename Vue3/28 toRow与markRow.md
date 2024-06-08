# toRow与markRow



## toRow

* 作用：用于获取一个响应式对象的原始对象，toRow返回的对象不再是响应式的，不会触发视图更新
  * 官网描述：这是一个可以用于临时读取而不引起代理访问/跟踪开销，或是写入而不触发更改的特殊方法。不建议保存对原始对象的持久引用。请谨慎使用
  * 使用场景：在需要将响应式对象传递给非vue的库或外部系统时，使用toRow可以确保它们收到的是普通对象



## markRow

* 作用：标记一个对象，使其永远不会变成响应式的
  * 例如使用mockjs时，为了防止误把mockjs变成响应式对象，可以使用，markRow去标记mockjs



## toRow例子

@/App.vue

```vue
<template>
	<div>
    <h2>人物信息</h2>
    <span>姓名:{{rowPerson.name}}</span>
    <span>年龄:{{rowPerson.age}}</span>
    <button @click="changeName">变更姓名</button>
    <button @click="changeAge">变更年龄</button>
    <button @click="changeObj">变更整个对象</button>
  </div>
</template>
<script setup>
import {reactive, toRow} from 'vue'
  

// 创建对象类型浅层响应式数据
let person = reactive({
  name: '张三',
  age: 18
})

// 获取一个响应式对象的原始对象
let rowPerson = toRow(person)



// 点击事件函数
function changeName() {
  rowPerson.name = '李四'
  console.log(rowPerson.name) // 控制台显示 李四 页面数据姓名无变更
}

// 点击事件函数
function changeAge() {
  rowPerson.age += 1
  console.log(rowPerson.name) // 控制台显示 19 页面数据年龄无变更
}

// 点击事件函数
function changeObj() {
  rowPerson = {
      name: '王五',
      age: 30,
    }
  console.log(rowPerson) // 控制台显示 {name: '王五', age: 30}，页面数据无变更
}

</script>
```



## markRow例子

@/App.vue

```vue
<template>
	<div>
    <h2>汽车信息</h2>
    <span>品牌：{{car2.barnd}}</span>
    <span>颜色:{{car2.options.color}}</span>
    <span>发动机:{{car2.options.engine}}</span>
    <button @click="changeBrand">变更汽车品牌</button>
  </div>
</template>
<script setup>
import {markRow, reactive} from 'vue'

// 创建普通对象类型数据
let car1 = markRow({
  barnd: '宝马',
  options: {
    color: '红色',
    engine: 'v8'
  }
})

// 使用reactive和car生成一个响应式对象，但实际无法生成，由于car被markRow绑定了
let car2 = reactive(car)

// 点击事件函数
function changeBrand() {
  car.barnd = "奥迪"
  console.log(car.barnd) // 控制台显示 安迪 页面数据无变更
}
</script>
```

