package javabasics.objectOrientedProgramming;

/**
 * 方法的重载（overload）
 * 重载的概念
 * 在同一个类中，允许存在一个以上的同名方法，只要它们的参数个数或者参数类型不同即可。
 *
 * 重载的特点：
 * 与返回值类型无关，只看参数列表，，且参数列表必须不同。（参数的个数或参数类型）。调用时，根据方法参数列表的不同来区别。
 * 重载示例：
 * //返回两个整数的和
 * int add(int x, int y){return x+y}
 * //返回三个数的和
 * int add(int x, int y, int z){return x+y+z}
 * //返回两个小数的和
 * double add(double x, double y) {return z+y}
 *
 */
public class MethodOverloading {
    //重载示例
    public int add(int x, int y) {
        return x + y;
    }
    //多个相同名称的方法如果想在一个类中共存，那么这些同名方法一定是参数的个数或者参数的数据类型不一样
    //这样的同名方法就叫做重载
    public int add(int x, int y, int z) {
        return x + y + z;
    }

    public double add(double x, double y) {
        return x + y;

    }

    public double add(double x, int y) {
        return x + y;

    }
    //参数类型顺序不同也可以构成重载
    public double add(int x, double y) {
        return x + y;

    }

}
