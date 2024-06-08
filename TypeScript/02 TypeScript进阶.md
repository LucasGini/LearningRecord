# TypeScript进阶

## 函数重载

``` typescript
function MyFun(name: string): string
function MyFun(age: number): string
function MyFun(value: number | string): string {
  if (typeof value === 'string') {
    return '你好，我的名字是' + value
  } else if (typeof value === 'number') {
    return `你好, 我的年龄是${value}`
  } else {
    return '非法格式'
  }
}
// 使用
MyFun('abc')
MyFun(18)
```



## 接口继承

``` typescript
interface Parent {
  prop1: string
  prop2: number
}
interface Child extends Parent {
  prop3: string
}

// 由于Child继承了接口Parent，所以myObj对象需要实现Child、Parent的属性
const myObj: Child = {
  prop1: 'abc',
  prop2: 33,
  prop3: 'add'
}
```



## 类（类的修饰符）

```typescript
class Article {
  public title: string, // 公开的属性，可以在类外部访问
  content: string,  // 公开的属性，可以在类外部访问，不写其他修饰符，默认为public
  aaa?: string // 可选属性，不需要进行初始化
  bbb: number = 10 // 设置默认值
  private tempData?: string  // 私有属性，不可以在类外部访问，只能在类内部访问
  protected innerData?: string // 保护属性，只能在类内部或者子类进行访问

	static author: string  // 静态属性，作为类的本身，不需要实例化即可使用  如Article.author
  
  // 修饰符组合使用
  privat static readonly cc: string // 私有的静态的只读属性，不可修改
  
  // 初始化使用到的参数需要是类的属性
  constructor (title: string, content: string) {
    this.title = title
    this.content = content
  }
}

// 实例化
const a = new Article('标题', 'aaa')
```



## 存取器（常用于私有值）

``` typescript
// 类似于java的getter和setter
class User {
  private _password: string = '' // 私有属性
  
  get password () {
    retrun '******'
  }
  set password(newPass: string) {
    this._password = newPass
  }
}

const u = new User()
console.log(user.password) // 调用 get方法，返回值
user.password = '123456'  // 调用set方法，更改值
```



## 抽象类(不具备实例化的类)

``` typescript
abstract class Animal {
  abstract name: string  // 抽象属性，子类继承该父类是，必须需要实现
  abstract maskSound(): void  // 抽象方法，子类继承该父类是，必须需要实现
  move (): void {     // 该方法子类不用必须实现
    console.log('移动')  
  }
} 

// 子类继承
class Cat extends Animal {
  name: string = '小猫'
  maskSound(): void {
    
  }
}
```



## 类实现接口

``` typescript
interface Animal {
  name: string,
  get sound(): string
  makeSound(): void
}
  
interface Behavior {
  behavior: string
}
  
 // 类可以继承多个接口，但是只能继承一个类
class Dog implements Animal, Behavior {
  name: string = '小狗'
  behavior: sting = '跑'
  get sound(): string {
    return ''
  }
  makeSound(): void {
    
  }
}
```



## 泛型类(与函数泛型类似)

``` typescript
class MyClass<T> {
  value: T
  constructor (value: T) {
    this.value = value
  }
  
  do (input: T): T {
  console.log('处理数据', this.value)
  return input
}
}

// 使用
const myStr = new MyClass<string>('Hello')
myStr.do('weweqw')

const myNum = new MyClass<number>(123123)
myStr.do(123123)
```

