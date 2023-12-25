package javabasics.operator;

/**
 * 算术运算符
 */
public class ArithmeticOperator {
    public static void main(String[] args) {
        //负号
        int i = -1;
        System.out.println(i);
        //正号
        int i1 = +1;
        System.out.println(i1);
        //加号
        int res = i + i1;
        System.out.println(res);
        //减号
        int res1 = res - i;
        System.out.println(res1);
        //乘号
        int res2 = res1 * i1;
        System.out.println(res2);
        //除号
        //当整数除以整数的时候，会把结果的小数部分舍弃，只保留整数
        int res3 = 7 / 3;
        System.out.println(res3);
        //取模
        //如果对负数取模，可以把模数负号忽略不计，如5%-2=1，但被模数是负数则不可忽略。取模结果不一定总是整数
        int res4 = 7 % 5;
        System.out.println(res4);
        //自增(前)：先运算后取值
        int a = 2;
        int b = ++a;
        System.out.println(a);
        System.out.println(b);
        //自增(后)：先取值后运算
        int a1 = 2;
        int b1 = a++;
        System.out.println(a);
        System.out.println(b);
        //自减(前)：先运算后取值
        int a2 = 2;
        int b2 = --a;
        System.out.println(a);
        System.out.println(b);
        //自减(后)：先取值后运算
        int a3 = 2;
        int b3 = a--;
        System.out.println(a);
        System.out.println(b);
        //字符串连接
        //"+"除字符串相加功能外，还能把非字符串转换成字符串。
        String str = "He" + "llo" + 2;
        System.out.println(str);
    }
}
