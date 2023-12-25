package javabasics.programFlowControl;

/**
 * 分支结构1：if-else语句
 * if语句三种格式：
 * 1、if(true) {
 *     执行代码块;
 * }
 * 2、if(条件表达式){
 *     执行代码快;
 * }
 * else {
 *     执行代码块;
 * }
 * 3、if(条件表达式) {
 *     执行代码块;
 * }
 * else if () {
 *     执行代码块;
 * }
 * ......
 * else {
 *     执行代码块;
 * }
 */
public class BranchStructure1 {
    public static void main(String[] args) {
        int x = 1;
        int y = 2;
        //格式1
        if (x > 0) {
            System.out.println(x);
        }

        //格式2
        if (x >  0) {
            System.out.println(x);
        }
        else {
            System.out.println(y);
        }

        //格式3
        if (x < 0) {
            System.out.println(x);
        }
        else if (x == 0) {
            System.out.println(0);
        }
        else {
            System.out.println(y);
        }

        //if-else可以嵌套使用，就是if-else里还可以有多个if-else
        int i = 4;
        int i2 = 1;
        if (i > 2) {
            if (i2 > 2) {
                System.out.println(i + i2);
                System.out.println("atguigu");
            }
        }
        else {
            System.out.println(i2);
        }

        /*
        例题1
        判断小明的期末成绩。
        当成绩为100分时，奖励一辆BMW；
        当成绩为(80, 99]，奖励一台iphone5s；
        当成绩为[60, 80]，奖励一本参考书；
        其他时，什么奖励也没有。
        */
        int score = 55;
        if (score == 100) {
            System.out.println("奖励一辆BMW");
        }
        else if (score > 80 && score <= 99) {
            System.out.println("奖励一台iphone5s");
        }
        else if (score >= 60 && score <= 80) {
            System.out.println("奖励一本参考书");
        }
        else {
            System.out.println("什么奖励也没有");
        }

    }
}
