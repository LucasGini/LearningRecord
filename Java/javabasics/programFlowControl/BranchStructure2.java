package javabasics.programFlowControl;

/**
 * 分支结构2：switch
 * 1、结构
 * switch(变量) {
 * case 常量1:
 *      语句1;
 *      break;
 * case 常量2:
 *      语句2;
 *      break;
 * ... ...
 * case 常量N:
 *      语句N;
 *      break;
 * default:
 *      语句;
 *      break;
 * }
 * 2、switch语句有关规则
 * switch(表达式)中表达式的返回值必须是下述几种类型之一：byte，short，char，int，枚举，String；
 * case子句中的值必须是常量，且所有case子句的值应是不同的；
 * default子句是可任选的，当没有匹配当case时，执行default
 * break语句用来在执行完一个case分支后使程序跳出switch语句块；如果没有break，程序会顺序执行到switch结尾
 * 3、if和switch语句很像，具体什么场景下，应用哪个语句呢？
 * 如果判断的具体数值不多，而且符合byte、short、int、char这四种类型。虽然两个语句都可以使用，建议使用switch语句。因为效率稍高。
 * 其他情况：对区间判断，对结果为boolean类型判断，使用if，if的使用范围更广。当不知道应该使用if还是switch，都可以使用if就可以，日常编程主要使用if
 */
public class BranchStructure2 {
    public static void main(String[] args) {
        //如果是数字1，输出星期一，数字二输出星期二，不是1和2的时候，输出不知道星期几
        int i = 1;
        switch (i) {
            case 1://相当与if(i == 1)
                System.out.println("星期一");
                break;
            case 2://相当与if(i == 2)
                System.out.println("星期二");
                break;
            default://相当与else
                System.out.println("不知道星期几");
                break;
        }

        //例题1
        //使用switch把小写类型的char型转为大写。只转换啊，a, b, c ,d e。其他的输出"other"
        char c = 'a';
        switch (c) {
            case 'a':
                c = 'A';
                System.out.println(c);
                break;
            case 'b':
                c = 'B';
                System.out.println(c);
                break;
            case 'c':
                c = 'C';
                System.out.println(c);
            case 'd':
                c = 'D';
                System.out.println(c);
            case 'e':
                c = 'E';
                System.out.println(c);
            default:
                System.out.println("other");
        }

        //例题2
        //对学生成绩大于60分的，输出"合格"。低于60分的，输出"不合格"
        int score = 80;
        if (score > 60) {
            System.out.println("合格");
        }
        else if (score < 60) {
            System.out.println("不合格");
        }

        int s = score % 60;
        switch (s) {
            case 1:
                System.out.println("合格");
                break;
            case 0:
                System.out.println("不合格");
                break;
        }

        //例题3
        //根据用于指定月份，大于该月份所属的季节。3，4，5春季 6，7，8夏季 9，10，11秋季 12，1，2冬季
        int x = 3;
        if (x == 3 || x == 4 || x == 5) {
            System.out.println("春季");
        }
        else if (x == 6 || x == 7 || x == 8) {
            System.out.println("夏季");
        }
        else if (x == 9 || x == 10 || x == 11) {
            System.out.println("秋季");
        }
        else if (x == 12 || x == 1 || x == 2) {
            System.out.println("冬季");
        }


        switch (x) {
            case 1:
                System.out.println("冬季");
                break;
            case 2:
                System.out.println("冬季");
                break;
            case 3:
                System.out.println("春季");
                break;
            case 4:
                System.out.println("春季");
                break;
            case 5:
                System.out.println("春季");
                break;
            case 6:
                System.out.println("夏季");
                break;
            case 7:
                System.out.println("夏季");
                break;
            case 8:
                System.out.println("夏季");
                break;
            case 9:
                System.out.println("秋季");
                break;
            case 10:
                System.out.println("秋季");
                break;
            case 11:
                System.out.println("秋季");
                break;
            case 12:
                System.out.println("冬季");
                break;
        }
    }
}
