# async/await/Promise让异步变成同步

Promise可以解决“回调地狱”问题，但是并不完美，如果接口是链式关联的，那么Promise的then()方法也有一个小回调，维护起来还不是很方便

async表示异步，await表示等待。async用于声明一个异步函数，await用于等待一个异步方法执行完成

## 自定义一个函数且必须返回一个Promise
```Javascript
function getTime () {
    // 返回Promise
    return new Promise(function (resolve, reject) {
        setTimeout(function () {
            resolve(10);
        })
    })
}

// 自定义一个异步函数function，前面加上async
async function getAsync () {
    let num = await getTime();
    console.log(num)
}
// 执行异步函数
getAsync();
```


## 重点：使用async和await固定步骤
   * 自定义一个函数且必须返回一个Promise
   * 自定义一个异步函数function，前面加上async
   * 在调用返回Promise的函数，签名加上await，这样就可以获取到resolve()方法传递的实参
