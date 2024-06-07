# 搭建pinia环境



## 安装pinia

```
// npm安装
npm i pinia

// yarn安装
yarn add pinia
```



## 进行配置

@/main.js

```javascript
import { createApp } from 'vue'
import App from './App.vue'
import { createPinia } from 'pinia'

const pinia = createPinia()
const app = createApp(App)

// 引入pinia
app.use(pinia)

app.mount('#app')
```



