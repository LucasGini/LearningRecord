# const常量的声明

## const 声明的值，保证变量指向的内存地址保存的数据不改变
```Javascript
const NAME= '张三';
console.log(NAME);      // 结果“张三

const NANE = '张三';
// NANE = '李四';     结果： Uncaught TypeError: Assignment to constant variable


const ARR = [1, 2, 3];
ARR.push(4);
console.log(ARR);   // 结果：[1, 2, 3, 4]

const OBJ = {book: 'HTML5'};
OBJ.book = 'Vue';
console.log(OBJ);   // 结果：{book: 'Vue'}
```

