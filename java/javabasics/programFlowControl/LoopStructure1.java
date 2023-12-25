package javabasics.programFlowControl;

/**
 * 循环结构：for循环
 * 循环语句功能：在某些条件满足的情况下，反复执行特定代码的功能
 * 循环语句的四个组成部分：初始化部分、循环条件部分、循环体部分、迭代部分
 * 循环语句分类：for循环、while循环、do/while循环
 * 语法格式：
 * for (初始化表达式;布尔值测试表达式;更改表达式) {
 *     语句或语句块
 * }
 */
public class LoopStructure1 {
    public static void main(String[] args) {
        //连续输出5次hello world
        for (int i = 0; i < 5; i++) { //初始化表达式;布尔值测试表达式;更改表达式
            System.out.println(i);
            System.out.println("Hello World!");
        }
        //初始化变量，这里变量是i，判断变量i是不是一个小于5的值，如果是，执行大括号里面的代码快，执行更改表达式i++给i重新赋值，值就变成了1
        //再判断变量i<5，当前的i是1，执行代码快，执行更改表达式i++给i重新赋值i变为2，以此类推，直到i<5不成立。

        int result = 0;
        for (int i = 1; i <= 100; i++) {
            result += i;
        }
        System.out.println(result);

        //例题1
        //编写程序，从1循环到150，并在每行打印一个值，另外在每个3的倍数行上打印出"foo"，在每个5的倍数行上打印"biz"，在没个7的倍数行上打印输出"baz"

        for (int i = 1; i <= 150; i++) {
            String str = "";
            str += i;
            if (i % 3 == 0) {
                str += " foo";
            }
            if (i % 5 == 0) {
                str += " biz";
            }
            if (i % 7 == 0) {
                str += " baz";
            }
            System.out.println(str);
        }

        //例题2
        //打印1～100之间所有奇数的和
        int sum = 0;
        for (int i = 1; i <= 100; i++) {
            if (i % 2 == 1) {
                sum += i;
            }
        }
        System.out.println(sum);

        //例题2
        //打印1～100之间所有是7的倍数的整数的个数及总和
        int sum1 = 0;
        int count = 0;
        for (int i = 1; i <= 100; i++) {
            if (i % 7 == 0) {
                sum1 += i;
                count += 1;
            }
        }
        System.out.println(count + " " + sum1);

        //例题3
        //输出所有的水仙花数，所谓水仙花数是指一个3位数，其各个位数上的数字立方和等于其本身。
        for (int i = 100; i <= 999; i++) {
            int m = (i / 100); // 获取百位数
            int n = i % 100 / 10; //获取十位数
            int k = i % 100 % 10; //获取个位数
            int res = m * m * m + n * n * n + k * k * k; //获得各个位数上的数字立方
            if (i == res) { //判断各个位数上的数字立方和等于其本身
                System.out.println(i);  //输出水仙花数
            }
        }
    }
}
