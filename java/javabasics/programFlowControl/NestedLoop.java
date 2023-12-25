package javabasics.programFlowControl;

/**
 * 嵌套循环
 * 将一个循环放在另一个循环体内，就形成了嵌套循环。其中，for，while，do...while均可以作为外层循环和内层循环。
 * 实质上，嵌套循环就是把内层循环当成外层循环当寻欢体。当只有内层循环当循环条件为false时，才会完全跳出内层循环才可以结束外层当当次循环，开始下一次的循环
 * 设外层循环次数为m次，内层为n次，则内层循环体实际上需要执行没m*n=mn次。
 */
public class NestedLoop {
    public static void main(String[] args) {
        //九九乘法表
        for(int x = 1; x <= 9; x++){//每次循环都会执行大括号里面的所有代码
            for (int y = 1; y <= x; y++){//每次循环都会执行大括号里面的所有代码
                System.out.print(y + " x " + x + " = " + x * y + ", ");
            }
            System.out.println();
        }
        //注意：在写嵌套循环的时候，要尽量保证外层循环的循环次数小于内层的循环次数

        //输出1--100之间的所有质数

        for (int m = 1; m <= 100; m++) {
            int count = 0;//变量是在它所在的大括号范围之内生效
            for (int n = 1; n <= m; n++){
                if (m % n == 0) {
                    count++;//能被整除，次数加1
                }
            }
            if (count == 2){
                System.out.println(m);
            }
        }
    }
}
