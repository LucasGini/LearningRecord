# 全局API转移到应用对象

* app.component
* app.config
* app.directive
* app.mount
* app.unmount
* app.use



## 定义全局组件(app.component)

@/Child.vue

```vue
<template>
  <div class="child">
    <h2>子组件</h2>
    <h3>求和为：{{sum}}</h3>
  </div>
</template>
<script setup>
  import {ref} from 'vue'
  
  let sum = ref(333)
</script>
```



@/main.js

```javascript
import { createApp } from 'vue'
import App from './App.vue'
import Child from '@/Child.vue'

const app = createApp(App)

// 定义全局组件
app.component('Child', Child)

app.mount('#app')
```



由于Child.vue定义了全局组件，可以不用引入即可使用

@/App.vue

```vue
<template>
	<div class="app">
    <h2>父组件</h2>
    <Child/>
  </div>
</template>
<script setup>
  
</script>
```



## 扩展全局配置(app.config)

@/main.js

```javascript
import { createApp } from 'vue'
import App from './App.vue'
import Child from '@/Child.vue'

const app = createApp(App)

// 定义全局属性
app.config.globalProperties.x = 9

app.mount('#app')
```



由于定义了全局属性x，可以直接使用

@/App.vue

```vue
<template>
	<div class="app">
    <h2>父组件</h2>
    <span>{{x}}</span>
  </div>
</template>
<script setup>
  
</script>
```



## 注册全局指令(app.directive)

@/main.js

```javascript
import { createApp } from 'vue'
import App from './App.vue'
import Child from '@/Child.vue'

const app = createApp(App)

// 注册全局指令
app.directive('beauty', (element, {value}) => {
  element.innerText += value
  element.style.color = 'green'
  element.style.backgroundColor = 'yellow'
})

app.mount('#app')
```



@/App.vue

```vue
<template>
	<div class="app">
    <h2>父组件</h2>
    <span v-beauty></span>
  </div>
</template>
<script setup>
  
</script>
```



## 挂载应用(app.mount)

@/main.js

```javascript
import { createApp } from 'vue'
import App from './App.vue'
import Child from '@/Child.vue'

const app = createApp(App)
// 挂载应用
app.mount('#app')
```



## 卸载应用(app.unmount)

@/main.js

```javascript
import { createApp } from 'vue'
import App from './App.vue'
import Child from '@/Child.vue'

const app = createApp(App)
// 卸载应用
app.mount('#app')
```



## 安装插件

@/main.js

```javascript
import { createApp } from 'vue'
import App from './App.vue'
import router from '@/router'

const app = createApp(App)
// 安装路由
app.use(router)
app.mount('#app')
```

