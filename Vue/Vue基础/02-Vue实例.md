# Vue实例

## 创建实例
先引入Vue，创建HTML标签\<div id="app"></div>,在\<script>内部添加new Vue({}),通过el属性绑定要渲染的View，也可以使用$mount()方法手动挂载Vue实例
```HTML
<div id="app"></div>
<script src="https://cdn.jsdelivr.net/npm/vue@2.6.12/dist/vue.min.js"></script>
<script>
    new Vue({
        el: '#app',
        data: data
    })
    // 或 new Vue({}).$mount("#app");
</script>
```

# 数据与方法

下面的代码自定义了一个对象let data，加入Vue实例中的data。当这些数据改变时，视图会进行重渲染，注意：只有当实例b诶创建时已经存在于data中的属性才是响应式的
```HTML
<div id="app"></div>
<script src="https://cdn.jsdelivr.net/npm/vue@2.6.12/dist/vue.min.js"></script>
<script>
    // 数据对象
    let data = {a: '张三'};
    // 该对象被加入一个vue实例中
    let vue = new Vue({
        el: '#app',
        data() {
            return data
        }
    })
    // 获取这个实例上的属性
    // 返回源数据中对应的字段
    console.log(vue.a === data.a);      // 结果：true

    // 设置属性也会影响原始属性
    vue.a = '李四';
    console.log(data.a);    // 结果：李四

    data.a = '王五';
    console.log(vue.a);    // 结果：王五
</script>
```

上述代码Vue实例里面data为什么要使用函数初始化数据而不是对象？

data写成函数形式，数据以函数返回的形式定义，这样每次复用组件时，都会返回一份新的data，相当于每个组件实例都有自己的私有空间，只负责维护各自的数据，不会造成混乱，
而写成对象形式，所有组件实例共用一个data，这样改一个就全部都改了，可以理解成函数相当于局部作用域，对象相当于全局作用域

# 生命周期
每个Vue实例(组件)都是独立的，都有一个属于它的生命周期，从创建实例(组件)、数据初始化、挂载、更新到销毁实例，这就是一个实例(组件)所谓的生命周期。
同时在生命周期的各个阶段也会运行一下叫做生命周期的钩子函数，这给了用户在Vue实例(组件)生命周期不同阶段添加代码的机会

1. 钩子函数
    ```HTML
    <div id="app"></div>
    <script src="https://cdn.jsdelivr.net/npm/vue@2.6.12/dist/vue.min.js"></script>
    <script>
    let vue = new Vue ({
        el: '#app',
        beforeCreate(){
            console.log('beforeCreate');
        },
        created(){
            console.log('created');
        },
        beforeMount(){
            console.log('beforeMount');
        },
        mounted(){
            console.log('mounted');
        },
        beforeUpdate(){
            console.log('beforeUpdate');
        },
        updated(){
            console.log('updated');
        },
        beforeDestroy(){
            console.log('beforeDestroy');
        },
        destroyed(){
            console.log('updated');
        },
        activated(){
            console.log('activated');
        },
        deactivated(){
            console.log('deactivated');
        },
    })
    </script>
    ```
   
2. 钩子函数的作用和实际使用场景
   1. `beforeCreate`：初始化之前 
   2. `created`：初始化完成之后（不能获取DOM，一般用于获取ajax数据） 
   3. `beforeMount`：挂载之前 
   4. `mounted`：挂载完成之后（可以获取DOM）
   5. `beforeUpdate`：数据更新之前
   6. `updated`：数据更新之后。应用场景：可以数据更新后获取焦点，也可以获取DOM的动态属性，更改数据时对DOM进行操作
   7. `beforeDestroy`：在Vue实例销毁之前调用。在这一步实例仍然可用（页面离开） 
   8. `destroyed`：在Vue实例销毁之后调用。调用后，Vue实例指示的所有东西都会解除绑定，所有的事件监听器会被移除，所有的子实例也会被销毁（页面离开） 
   9. `activated`：keep-live组件激活时调用。该钩子函数在服务器端渲染期间不被调用，用于性能优化缓存DOM和数据 
   10. `deactivated`：keep-live组件停用时调用。该钩子函数在服务器端渲染期间不被调用
