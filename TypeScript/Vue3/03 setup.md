# setup

## setup 概述

setup是Vue3中一个新的配置项，值是一个函数，它是组合式API的舞台，组件中所有用到的：数据、方法、计算属性、监视等等，均配置在setup中

特点如下：

* setup 函数返回的对象中的内容，可以在模板中使用
* setup 中访问this是undefined
* setup函数会在beforeCreate之前调用，它是领先所有钩子执行的

``` vue
--App.vue
.....
<script>
  export default {
    name: 'app',
    setup(){
      // 数据
      let a = 'aa'
      ......
      // 方法
      function changA() {
        ......
      }
      // setup的返回值
      return {a, changA}
    }
  }
</script>
......
  
```



## setup与OptionsAPI

* setup()能与data()、methods()、computed()共存
* data()、methods()、computed()等能访问setup()的数据、方法等，而setup()不能访问data()、methods()、computed()定义的数据、方法。根本原因是setup函数会在beforeCreate之前调用，它是领先所有钩子执行的



## setup语法糖(推荐)

在script标签上标上setup

```vue
--App.vue
.....
<script setup>
 // 数据
 let a = 'aa'
 ......
 // 方法
 function changA() {
   ......
 }
</script>
......
```



## 组件命名

由于setup语法糖标记的script标签块无法使用export default {name: 'app'} 去为组件命名，所以需要引入chaj

``` npm
# 下载插件
npm i vite-plugin-vue-setup-extend
```

``` javascript
// 配置插件
......
import VueSetupExtend from 'vite-plugin-vue-setup-extend'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    VueSetupExtend(),
  ],
......
})

```

``` vue
--App.vue
.....
// 在script标签上使用name给组件命名
<script setup name='componentsName'>
 // 数据
 let a = 'aa'
 ......
 // 方法
 function changA() {
   ......
 }
</script>
......
```

