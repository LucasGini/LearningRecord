package javabasics.operator;

/**
 * 逻辑运算符
 */
public class LogicalOperator {
    public static void main(String[] args) {
        boolean a = true;
        boolean b = true;
        //&--逻辑与,当a与b同时为true时才会输出true，其中有一个为false，则输出false
        System.out.println(a & b);
        //｜--逻辑或，当a与b其中有一个为true时会输出true，同时为false时才会输出false
        System.out.println(a | b);
        //!--逻辑非，当a为true，则会输出false，当a为false时，会输出true
        System.out.println(!a);
        //&&--短路与，当a与b同时为true时才会输出true，其中有一个为false，则输出false
        //与逻辑与的区别是，如果左边为真，右边参与运算，如果左边为假，那么右边不参与运算
        System.out.println(a && b);
        //||--短路或，当a与b其中有一个为true时会输出true，同时为false时才会输出false
        //与逻辑或的区别是，如果左边为假，右边参与运算，如果左边为真，那么右边不参与运算
        System.out.println(a || b);
        //^--逻辑异或，当a和b同时为true或者false时会输出false，a与b不相同，则会输出true
        //异或，追求的是异
        System.out.println(a ^ b);

        int x = 1;
        int y = 1;
        if (x++ == 2 & ++y == 2) {
            //x++==2,x先取值后计算，1==2，结果是false，再计算++后x的值是2
            //++y==2，y是先计算后取值，先计算++得到y的值为2，再逻辑判断2==2，结果是true
            //false & true，得到的结论是fales，不会执行打括号里面的赋值
            x = 7;
        }
        System.out.println("x=" + x + ",y=" + y);

        int x1 = 1;
        int y1 = 1;
        if (x1++ == 2 && ++y1 == 2) {
            //由于x1++==2为false，&&的时候不会去执行右边的运算，所以输出x=2，y=1
            x1 = 7;
        }
        System.out.println("x=" + x1 + ",y=" + y1);

        int x2 = 1;
        int y2 = 1;
        if (x2++ == 2 | ++y2 == 2) {
            x2 = 7;
        }
        System.out.println("x=" + x2 + ",y=" + y2);

        int x3 = 1;
        int y3 = 1;
        if (x3++ == 1 || ++y3 == 2) {
            x3 = 7;
        }
        System.out.println("x=" + x3 + ",y=" + y3);
    }
}
