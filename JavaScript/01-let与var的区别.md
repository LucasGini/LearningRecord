# let与var的区别

## 作用域不一样，var是全局作用域，let是块作用域
```Javascript 
if (true) {
    var a = '10';
}
console.log(a);  // 结果：10

if (true) {
    let a1 = '10';
}
// console.log(a1);   结果：Uncaught ReferenceError: a1 is not defined
```

## let没有变量提升
```Javascript
console.log(a2);   // 结果：undefined
var a2 = '10';

// console.log(a3);    结果Uncaught ReferenceError: Cannot access 'a3' before initialization
let a3 = '10';
```

## let变量不能重复声明
```Javascript
var a4 = 1;
var a4 = 10;
console.log(a4);    // 结果：10

let a5 = 1;
// let a5 = 10;
// console.log(a5);     结果：Uncaught SyntaxError: Identifier 'a5' has already been declared
```

## 循环作用域
```Javascript
var i = 1;
for (var i = 0; i < 10; i++) {
}
console.log(i);     // 结果：10

let i1 = 1;
for (let i1 = 0; i < 10; i++) {
}
console.log(i1);    // 结果：1

for (var i2 = 0; i2 < 10; i2++) {
    setTimeout(function () {
        console.log(i2);     // 结果：10, 10, 10, 10, 10, 10, 10, 10, 10, 10， 因为var声明的变量是全局的，包括循环体内和循环体外
    }, 300);
}

// 如果想要i的值是0、1、2、3、4、5、6、7、8、9
// 方法一:使用let块作用域的特性
for (let i3 = 0; i3 < 10; i3++) {
    setTimeout(function () {
        console.log(i3);    // 结果：0、1、2、3、4、5、6、7、8、9
    })
}

// 方法二：使用闭包
for (var i4 = 0; i4 < 10; i4++) {
    (function ($i4) {
        console.log($i4);   // 结果：0、1、2、3、4、5、6、7、8、9
    })(i4)
}
```

