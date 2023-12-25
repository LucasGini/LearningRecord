package javabasics.objectOrientedProgramming;

import work.Student;

/**
 * 关于对象
 * 当一个对象被创建时，会对其中各种类型当成员变量自动进行初始化赋值。除了基本数据类型之外当变量类型都是引用类型
 * 成员变量类型    初始值
 * byte             0
 * short            0
 * int              0
 * long             0L
 * float            0.0F
 * double           0.0D
 * char             '\u0000'(表示为空)
 * boolean          false
 * 引用类型         null
 *
 * 匿名对象
 * 不定义对象当句柄，直接调用这个对象当方法，这样当对象叫匿名对象
 * 如：new Person().shout();
 *
 * 使用情况
 * 如果对过对象只需要进行一次方法调用，就可以使用匿名对象。
 * 通常将匿名对象作为实参传递给一个方法调用。
 *
 * 类的访问机制：
 * 在一个类中的访问机制：类的方法可以直接访问类中的成员变量。（例外：static方法访问非static，编译不通过）
 * 在不同类中访问机制：先创建要访问类的对象，再用对象访问类中定义的成员
 *
 * 面向对象思想"落地"法则（一）
 * 1、关注于类的设计，即设置类的成员：属性、方法
 * 2、类的实例化，即创建类的对象
 * 3、通过"对象.属性"、"对象.方法"执行
 *
 */
public class AboutTheObject {
    public static void main(String[] args) {
        //匿名对象
        new ClassAttributes().showAge();


    }
}
