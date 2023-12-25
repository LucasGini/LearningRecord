package javabasics.objectOrientedProgramming;

/**
 *可变个数的形参
 * 说明：
 * 1、可变参数：方法参数部分指定类型的参数个数是可变多个
 * 2、声明方式：方法名(参数的类型名...参数名)
 * 3、可变参数方法的使用与方法参数部分使用数组是一致的
 * 4、方法的参数部分有可变形参，需要放在形参声明的最后
 */
public class VariableNumberParameter {

    //下面采用数组形参来定义方法
    //用数组的方式来传递可变格式的参数
    //调用方法的时候，如果没有参数，就要定义一个空数组或者是null
    public static void test(String[] books) {

    }
    //下面采用可变个数形参来定义方法
    //用java特有的...的方式来传递可变个数的参数，这种参数在使用时与数组的使用方式相同
    //如果没有参数可以不填
    //这种...代表可以传递0到多个参数
    //如果一个方法有多个的形参，可变的形参（...）一定要放在所有形参的最后
    public static void test(int a, String...books) {

    }
    public static void test(int... i){

    }
}
