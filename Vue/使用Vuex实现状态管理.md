# 使用vuex实现状态管理

1. 安装Vuex库
   ```markdown
   npm install vuex -save
   ```
   
2. 新建src/vuex目录，目录下创建store.js文件
   ```Javascript
   // Vuex默认的4种基本对象
   // state：数据存储状态
   // getters：对外输出的数据，可以将其理解为state的计算属性
   // mutations：同步改变状态值，在组件中使用$store.commit('')进行操作
   // actions：异步操作，在组件中使用$store.dispath('')进行操作
   import Vue from 'vue'
   import Vuex from 'vuex'
   
   Vue.use(Vuex)
   
   const state = {
         count: 10
   }
   
   const mutations = {
      mutationAdd (state, n = 0) {
         return state.count += n
   },
      mutationReduce (state, n = 0) {
         return state.count -= n
   }
   }
   
   const actions = {
      actionsAdd (state, n = 0) {
         return state.count += n
   },
      actionsReduce (state, n = 0) {
         return state.count -= n
   }
   }
   
   const getters = {
      getCount (state) {
         return state.count
   }
   }
   
   const store = new Vuex.Store({
      state,
      mutations,
      actions,
      getters
   })
   
   // 将store对象导出，之后可以在new Vue({})处引用
   export default store
   ```

3. 在src/main.js中引入Vuex
   ```Javascript
   ...
   import store from './vuex/store'
   ...
   
   // 关闭生产模式下的提示
   Vue.config.productionTip = false
   
   new Vue({
     el: '#app',
     // 在框架中使用路由功能
     router,
     // 在框架中使用store vuex
     store,
     components: { App },
     template: '<App/>'
   })
   ```

4. 打开文件src/components/HelloWrold.vue编辑如下代码
   ```vue
   <template>
     <div class="hello">
       <h1>{{ msg }}</h1>
       <h1>{{ $store.state.count }}</h1>
       <button @click="addclick(1)">+</button>
       <button @click="reduceclick(1)">-</button>
       <div>异步操作</div>
       <div>
         <button @click="async_add(5)">异步增加</button>
         <button v-on:click="async_reduce(5)">异步减少</button>
       </div>
       <h1>我是getters{{ count }}</h1>
       <div>mapMutations</div>
       <button v-on:click="mapmutclickadd(1)" >+</button>
       <button v-on:click="mapmutclickreduce(1)" >-</button>
       <div>mapActions</div>
       <button v-on:click="mapactadd(5)" >异步增加</button>
       <button v-on:click="mapactreduce(5)" >异步减少</button>
       <h1>我是mapGetters{{ mapcount }}</h1>
   ...
   ...
     </div>
   </template>
   
   <script>
   import { mapActions, mapGetters, mapMutations } from 'vuex'
   export default {
     name: 'HelloWorld',
     methods: {
       addclick (n) {
         this.$store.commit('mutationAdd', n)
       },
       reduceclick (n) {
         this.$store.commit('mutationReduce', n)
       },
       async_add (n) {
         this.$store.dispatch('actionsAdd', n)
       },
       async_reduce (n) {
         this.$store.dispatch('actionsReduce', n)
       },
       // 使用扩张运算符简化编写——通过使用ECMAScript6的展开运算符“...”来提取mapMutations等函数返回的对象属性。其原理是使用复杂函数mapMutations()将组件中的方法映射为store.commit()调用
       ...mapMutations({
         mapmutclickadd: 'mutationAdd'
       }),
       ...mapMutations({
         mapmutclickreduce: 'mutationReduce'
       }),
       ...mapActions({
         mapactadd: 'actionsAdd'
       }),
       ...mapActions({
         mapactreduce: 'actionsReduce'
       })
     },
     computed: {
       count () {
         return this.$store.getters.getCount
       },
       ...mapGetters({
         mapcount: 'getCount'
       })
     },
     data () {
       return {
         msg: 'Welcome to Your Vue.js App'
       }
     }
   }
   </script>
   
   ...
   </style>
   ```




