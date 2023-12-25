package javabasics.objectOrientedProgramming;

/**
 * 类的方法
 *
 * 语法格式：
 * 修饰符 返回值类型 方法名(参数列表){
 *     方法体语句;
 * }
 * 说明：修饰符：public,private,protected等。
 *      返回值类型：return语句传递返回值。没有返回值：void。
 *
 * 举例：
 * public class Person{
 *     private int age;
 *     public int getAge(){//声明方法getAge
 *         return age;
 *     }
 *     public void setAge(int i){ //声明方法setAge
 *         age = i; //将参数i的值赋给类的成员变量age
 *     }
 * }
 *
 * 方法（method）
 * 方法是类或对象行为特征的抽象，也称为函数。
 * Java里的方法不能独立存在，所有的方法必须定义在类里。
 *
 * 修饰符 返回值类型 方法名(参数类型 形参1, 参数类型 形参2,...){
 *     程序代码
 *     return 返回值;
 * }
 * 其中：
 * 形式参数：在方法被调用时用于接收外部传入的数据的变量。
 * 参数类型：也就是该形式参数的数据类型。
 * 返回值：方法在执行完毕后返回给调用它的程序的数据。
 * 返回值类型：方法要返回的结果的数据类型。
 * 实参：调用方法时实际传给函数形式参数的数据。
 *
 * 方法的调用
 * 方法只有被调用才会被执行
 *
 * 注意：
 * 没有具体返回值的情况，返回知道类型用关键字void表示，那么该函数中的return语句如果在最后一行可以省略不写。
 * 定义方法时，方法的结果应该返回给调用者，交由调用者处理
 * 方法中只能调用方法，不可以在方法内部定义方法。
 *
 */
public class ClassMethod {

    private int age;
    public int getAge(){//声明方法getAge
        return age;
    }
    public void setAge(int i){ //声明方法setAge
        age = i; //将参数i的值赋给类的成员变量age
    }

    public int getArea(int x, int y) {
        return x*y;
    }

}
