package javabasics.operator;

/**
 * 赋值运算符
 */
public class AssigningOperator {
    public static void main(String[] args) {
        /*
        符号：=
        （1）当"="两侧数据类型不一致时，可以使用自动类型转换或使用强制类型转换原则进行处理
        （2）支持连续赋值
        */
        int i = 1;
        i = 2;//自动类型转换
        short s = (short)i;//强制类型转换
        System.out.println(s);

        //连续赋值
        int i0 = 0;
        int i1 = 0;
        int i2 = 0;
        i0 = i1 = i2 = 1;
        System.out.println(i0 + " " + i1 + " " + i2);

        //扩展赋值运算符：+=，-=，/=，*=，%=
        int i3 = 1;
        i3 += 2;//等同于i3 = i3 + 2;
        i3 -= 2;//等同于i3 = i3 - 2;
        System.out.println(i3);
        //字符串也可以使用"+="进行拼接
        String str = "hel";
        str += "lo";
        System.out.println(str);

        byte b = 1;
        b = (byte) (b + 1);//变量参与运算时候，Java程序不知道具体的这个变量在做完运算会不会超出当前变量的范围
                            //所以会先把变量转换为一个更大长度，这个例子中，short是一个短整型数据，会转化为默认的int
        b += 3; //再是使用扩展赋值运算符时，变量在参与运算时会把结果强制转换为当前变量的类型

        int i5 = 1;
        i5 *= 0.1;
        System.out.println(i5);
        i5++;
        System.out.println(i5);





    }
}
