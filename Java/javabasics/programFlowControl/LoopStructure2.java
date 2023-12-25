package javabasics.programFlowControl;

/**
 * 循环结构：while、do/while
 * while语法格式：
 * [初始化语句]
 * while(布尔测试表达式) {
 *     语句或语句块;
 *     [更改语句;]
 * }
 *
 * do/while语法格式：
 * [初始化语句]
 * do {
 *     语句或语句块;
 *     [更改语句;]
 * }while(布尔测试表达式);
 */
public class LoopStructure2 {
    public static void main(String[] args) {
        //循环输出1到100到数字
        int i = 1 ;
        while ( i <= 100) {
            //获取变量i <= 100的结果是true还是false，如果是true就执行while的大括号里面的代码，如果是false不执行
            System.out.println(i);
            i++;//不断改变变量i的值
        }

        //等同于
        for (int k = 1; k <= 100; k++) {
            System.out.println(i);
        }

        //等同于
        int m = 1;
        do {
            System.out.println(m);
            m++;
        }
        while (m <= 100);

        //例题1
        //求1到100之间所有偶数到和，用for和while语句分别完成。
        int res = 0;
        for (int n = 1; n <= 100; n++) {
            if (n % 2 == 0) {
                res += n;
            }
        }
        System.out.println(res);

        int sum = 0;
        int k = 1;
        while (k <= 100) {
            if (k % 2 == 0){
                sum += k;
            }
            k++;
        }
        System.out.println(sum);

        //使用for (;;) {}表示for循环的无限循


        //使用while (true) {}表示while循环的无限循环


    }
}
