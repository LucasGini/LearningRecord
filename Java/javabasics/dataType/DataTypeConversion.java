package javabasics.dataType;

/**
 * 数据类型转换
 */
public class DataTypeConversion {
    public static void main(String[] args) {
        //数据类型转换
        int i = 1;
        byte b = 2;
        short s = 3;
        int res = i + b + s;//在计算过程中，int类型的范围最大，所以s和b都分布先转换成int类型然后进行加的运算，最终结果是int数据类型
        //数据转换关系：byte/short/char --> int --> long --> float --> double ,byte/short/char之间不可以相互转换,它们之间计算会首先转换成int类型
        System.out.println(res);

        char c = 'a';
        byte b0 = 2;
        int k = c + b0; //char类型的数据在与数字进行运算的时候，会先转换为ascii码的值然后进行计算
        System.out.println(k);

        String  str = "Hello World!";
        double d = 1.22;
        float f = 1.2323f;
        long l = 3L;
        int i1 = 23;
        char c0 = 'b';
        short s0 = 33;
        byte b1 = 22;
        //把任何基本数据类型的值和字符串进行链接运算时，基本类型的值将自动转化为字符串类型
        String str0 = str + f + d + l + i1 + c0 + s0 + b1;
        System.out.println(str0);

        //隐式转换数据类型
        byte b2 = 9;
        int k1 = b2;
        System.out.println(k1);

        //强制转换数据类型,注意强制转换会导致精度丢失
        int k2 = 7;
        byte b3 = (byte)k2;//转换的数据类型要使用英文的小括号括起来
        System.out.println(b3);

        //通常，字符串类型不能直接转换为基本类型，但通过基本类型对应的包装饰类，可以实现把字符串类型转换成基本类型
        String a = "43";
        int i3 = Integer.parseInt(a);
        System.out.println(i3);

        //boolean类型不可以转换成其他数据类型

    }
}
