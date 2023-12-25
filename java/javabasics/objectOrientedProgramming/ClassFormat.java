package javabasics.objectOrientedProgramming;

/**
 * 类的写法：
 * 修饰符 class 类名 {
 *     属性声明;
 *     方法声明;
 * }
 * 说明：修饰符public：类可以被任意访问
 *      类的正文要用{}括起来
 *
 * 举例：
 * public class Person {
 *     public int age;//声明公有变量age
 *     public void showAge(){//声明方法showAge()
 *         System.out.println(age);
 *     }
 * }
 *
 * 创建java自定义类
 * 步骤：
 * 1.定义类（考虑修饰符、类名）
 * 2.编写类的属性（考虑修饰符、属性类型、属性名、初始化值）
 * 3.编写类的方法（考虑修饰负、返回值类型、方法名、形参等）
 *
 */

//这下方就是人的类
public class ClassFormat {

    //属性--成员变量,类的成员变量可以先声明，不用初始化，类成员变量是有默认值的
    String name;   //String的默认值是null
    int age;   //int的默认值是0

    //行为--方法，也叫函数
    /**
     * 打印姓名
     */
    public void showName(){//方法的名称如果是多个单词的，首个单词的首字母小写，其他单词首字母大写，这样就像一个驼峰一样，所以叫驼峰命名法

        System.out.println("姓名： " + name);
    }

    /**
     * 获取年龄
     * @return
     */
    public int getAge() {//如果是一个有返回值的方法，那么方法体的最后一行一定是返回相应的数据，使用的是return关键字，返回的数据类型与方法定义的一致
        return age;
    }
}
