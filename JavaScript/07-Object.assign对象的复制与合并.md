# Object.assign对象的复制与合并

## 基本用法
```Javascript
let target = {a: 1};
let source1 = {b: 2};
let source2 = {c: 3};
Object.assign(target, source1, source2);
console.log(target);    // 结果：{a: 1, b: 2, c: 3}，Object.assign方法的第一个参数是目标对象，后面的参数是源对象

// 目标对象与源对象有同名属性，或者多个源对象有同名属性，则后面的属性会覆盖前面的属性
let target1 = {a: 1, b: 2};
let source11 = {b: 3, c:3};
let source12 = {c: 4};
Object.assign(target1, source11, source12);
console.log(target1);    // 结果：{a: 1, b: 3, c: 4}

// 方法只有一个目标对象参数，会直接返回参数
let target3 = {a : 1};
Object.assign(target3);
console.log(target3);   // 结果：{a: 1}

// 如果该参数不是对象，则会先转为对象，然后返回
let abc = Object.assign('abc');
console.log(abc);   // 结果：String {'abc'}
console.log(abc[0], abc[1], abc[2]);    // 结果：
console.log(abc[0], abc[1], abc[2]);    // 结果：a b c

// undefined和null无法转换为对象
// Object.assign(undefined);   结果：Uncaught TypeError: Cannot convert undefined or null to object
// Object.assign(null);    结果：Uncaught TypeError: Cannot convert undefined or null to object
```

## 常见用途

### 为对象添加属性
```Javascript
function Person(name, age) {
    Object.assign(this, {name, age});   // 相当于this.name = name; this.age = age;
}
let person = new Person('张三', 20);
console.log(person);   // 结果：Person {name: '张三', age: 20}
```

### 为对象添加方法
```Javascript
function NewPerson(name, age) {}
Object.assign(NewPerson.prototype, {    //NewPerson.prototype是原型，第二个参数定义的是方法
    say() {
        console.log('say()方法');
    },
    run() {
        console.log('run()方法');
    }
});
let new_person = new NewPerson();
new_person.say();   // 结果：say()方法
new_person.run();   // 结果：run()方法

//上面的方法等同于
function NewPerson1(name, age) {}
NewPerson1.prototype.say = function () {console.log('say()方法');}
NewPerson1.prototype.run = function () {console.log('run()方法')}
let new_person1 = new NewPerson1();
new_person1.say();   // 结果：say()方法
new_person1.run();   // 结果：run()方法
```

