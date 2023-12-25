package javabasics.objectOrientedProgramming;

import work.*;

/**
 * 类的创建和使用
 * 使用new+构造器创建一个新的对象；
 * 使用"对象名.对象成员"的方式访问对象成员（包括属性和方法）；
 */
public class ObjectCreationAndUse {
    public static void main(String[] args) {
        //实例化Person类，也就是创建Person对象
        Person person = new Person();//声明一个Person类型的变量，变量名person，实例化Person类并且给person赋值，赋值的值就是当前的实例
        //new Person()就是实例化Person类
        person.name = "张三";//给person对象的name属性赋值
        person.showName();//对象的方法调用
        person.age = 20;//给person对象的age属性赋值
        int i = person.getAge();//有返回值的方法，在调用之后就会有一个值，这个值就是方法中return的那部分
        System.out.println(i);

        //同一个类，通过类实例化出来的对象可以多个，每个对象都是独立，互不干扰
        Person person1 = new Person();
        Person person2 = new Person();
        person1.name = "zhangsan";
        person2.name = "lisi";
        person1.showName();
        person2.showName();

        //实例化一个Animal的对象
        Animal animal = new Animal();
        animal.eat("老鼠");
        animal.move("爬行");
        // 未来可能会学习更加复杂的类，我们会给这些类创建对象，并且使用对象，
        // 对于对象的使用，无外乎就两个地方，操作对象的变量，调用对象方法

        //实例化一个Student对象
        Student student = new Student();
        student.name = "小明";//给Student类name属性赋值
        student.age = 18;//给Student类age属性赋值
        student.course = "语文、数学";//给Student类course属性赋值
        student.interest = "打篮球";//给Student类interest属性赋值
        student.showPersonalInformation();//调用Student类showPersonalInformation()方法

        ClassMethod cm = new ClassMethod();
        int a = cm.getArea(10,9);
        System.out.println(a);

        Person1 p = new Person1();//实例化对象Person1赋值给p
        p.study();//调用study()方法
        p.age = 18;//给属性age赋值
        p.showAge();//调用showAge方法输出年龄
        p.addAge(2);//调用addAge方法增加年龄
        p.showAge();//调用showAge方法输出年龄

        //计算圆的面积
        double area = new Circle().area(2);
        System.out.println("半径为2的圆的面积是：" + area);

        //方法重载
        Overload overload = new Overload();
        overload.mOL(11);
        overload.mOL(11, 12);
        overload.mOL("重载方法mOL");

        //获取最大值
        CompareTheSize c = new CompareTheSize();
        c.max(11, 30);
        c.max(3.24, 6.555);
        c.max(113.34, 234.24, 4324.34);

        Person2 p2 = new Person2();
        String[] ss = new String[] {"zhangsan","11"};
        p2.printInfo(ss);
        String[] ss1 = new String[] {"北京是xxx","133333333333","2343432434243242343443"};
        p2.printInfo(ss1);

        p2.printInfo1("lisi", "23", "男");
        p2.printInfo1(ss1);
    }
}
