package work;

import java.sql.SQLOutput;

public class Person2 {
    /**
     * 用数组的方式来传递可变格式的参数
     * @param args
     */
    public void printInfo(String [] args) {//问题，我们能够打印的信息都要来源于方法的参数，也就是形参传递
        //现在可能给方法要传递不同的参数?如果在这种情况下，我们不知道要给方法传递多少个参数，这种情况要考虑使用可变形参
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
    }

    /**
     * 用java特有的...的方式来传递可变个数的参数，这种参数在使用时与数组的使用方式相同
     * @param args
     */
    public void printInfo1(String... args) {
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
    }
}
