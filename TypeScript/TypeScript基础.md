# TypeScript基础



## 基础类型

``` typescript
// 数值
let num: number = 1
// 字符串
let str: string = 'aa'
// 布尔值
let bool: boolean = true
// null
let nu: null = null
// undefined
let un: undefined = undefined
```



## 联合类型

``` typescript
// 类型限制
// 可以给变量v4分配字符串类型和数值类型
let v4: string | number
// 具体值限制
let v5: 1 | 3 | 5
```



## 数组/元组/枚举

``` typescript
// 数组
// 数值数组
let numArr1: number[] = [1, 2, 3, 4]
let numArr2: Array<number> = [1, 2, 3, 4]
// 字符串数组
let strArr1: string[] = ['aa', 'bb', 'cc']
let strArr2: Array<string> = ['aa', 'bb', 'cc']
// 布尔值数组
let boolArr1: boolean[] = [true, false, true]
let boolArr2: Array<boolean> = [true, false, true]

// 元组
// ?表示可选值,下标2的值可写可不写
let t1: [number, string, number?] = [1, 'a', 2] // 或者 [1, 'a']

// 枚举
enum MyEnum {
  A,
  B,
  C,
}
// 可以通过以下方式获取值
console.log(MyEnum.A)
console.log(MyEnum[0])

```



## 类型断言(不建议)

```typescript
let numArr: number[] = [1, 2, 3]
// 由于result 有可能是undefined，会导致报错，所以除非能完全确定该方法会返回number类型，否则不建议这么用
const result = numArr.find(item => item > 2) as number 
result * 5
```



## 函数分配类型

``` typescript
// void表示没有返回值
function MyFun1(a: number, b: string): void {
  a + b
}

// 数值加字符串返回的是字符串
// ?表示可选的,函数可入参b或者不入参b,c则是带有默认值
// ...rest表示剩余参数,是一个数组结构
function MyFun2(a: number, b?: string, c: number = 10, ...rest: number[]): string {
  return a + b + c
}
```



## 接口

``` typescript
// 定义一个接口
interface Obj = {
  name: string,
  age: number
}

// 对象的实现要受到接口的约束
const obj1: Obj = {
  name: 'a',
  age: 10
}

const obj2: Obj = {
  name: 'b',
  age: 3
}
```



## 类型别名 -- type

``` typescript
// 关键字type 
// 定义一个类型别名
type MyUserName = string | number
// a可以是数值类型也可以是字符串类型
let a: MyUserName
```



## 泛型 -- '<T>'

``` typescript
// 定义一个泛型函数
function myFun<T>(a:T, b:T): T[] {
 return [a, b]
}

// 使用
// 表示使用数值类型来处理
myFun(1, 2)
myFun<number>(1, 3)
// 表示使用字符串类型来处理
myFun('a', 'b')
myFun<string>('a', 'b')
```

