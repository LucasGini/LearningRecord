# Promise
Promise就是一个对象，用来传递异步操作消息，可以解决回调函数的嵌套问题，也就是所谓的“回调地狱”问题

## 回调地狱案例

通常是在异步执行是使用回调函数解决异步执行顺序时出现，这样一层层嵌套让代码维护起来很不方便
```Javascript
ajax("http://www.lucklnk.com", "get", function (){
    ajax("http://www.lucklnk.com", "get", function () {
        ajax("http://www.lucklnk.com", "get", function () {
        })
    })
});
```


## Promise案例
```Javascript
let code = 200;
let p1 = new Promise((resolve, reject) => {
    // 用setTimeout模拟ajax,实际开发时这里要封装ajax
    setTimeout(() => {
        if (code === 200) {
            // 执行成功调用resolve()函数
            resolve('成功！');
        } else {
            // 执行失败调用reject()函数
            reject('失败！');
        }
    }, 300)
});
// 调用Promise
p1.then((resolve) => {
    console.log(resolve);
}).catch((reject) => {
    console.log(reject);
});     // 结果：成功！
```

上面的代码Promise是通过构造函数创建的。接收函数作为参数，该函数有两个参数分别是resolve()方法和reject()方法

一个Promise有以下几种状态：
   * pending：初始状态，既不是成功状态，也不是失败状态
   * fulfilled：意味着操作成功完成
   * rejected：意味着操作失败
如果异步操作成功resolve()方法将Promise对象的状态从“未完成”变为“成功”（即从pending变为fulfilled）
   * 
如果一般操作失败reject()方法将Promise对象的状态从“未完成”变成“失败”（即从pending变为rejected）

如果执行resolve()方法，对应地会掉用then()方法，then()方法传入一个函数作为参数，该函数的参数的值就是resolve()方法的实参

如果执行reject()方法，对应地会调用catch()方法，catch()方法传入一个函数作为参数，该函数的参数的值就reject()方法的实参

## Promise在实际开发中应用最多的就是封装ajax
```Javascript
// 重点
function request (method, url, data) {
    let request = new XMLHttpRequest(); // 实例化ajax
    return new Promise( (resolve, reject) => {
        request.onreadystatechange = function () {
            if ( request.readyState === 4) {    // 服务器连接已建立
                if ( request.status === 200 ) {     // 连接成功
                    resolve(request.responseText);  // 处理结束后返回的结果
                } else {
                    reject(request.status);     // 处理失败后返回的结果
                }
            }
        };
        request.open(method, url);
        request.send(data);
    } )
}

// 调用request()方法
request("get", "http://vueshop.glbuys.com/api/home/index/nav?token=lec949a15fb709370f").then(
    function (result) {
        console.log(JSON.parse(result));
    }
).catch(
    function (result) {
        console.log(result);
    }
)
```

