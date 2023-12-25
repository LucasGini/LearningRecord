package javabasics.programFlowControl;

/**
 * 特殊流程控制语句
 * break语句
 * break语句用于终止某个语句块的执行，如终止循环、终止switch语句的case
 * {
 *     ......
 *     break;
 * }
 * 注意：break终止当前所在的循环
 *
 * continue语句
 * continue语句用于跳过某个循环语句块的一次执行
 * continue语句出现在多层嵌套的循环语句体中是，可以通过标签指明要跳过的是哪一层循环
 * 注意：continue是对当前所在对循环起作用，结束当前这次循环，当前循环的下面的代码不执行，直接进入下一循环
 *
 *
 * return语句
 * return并非专门用于结束循环的，功能是结束一个方法。当一个方法执行到一个return时，这个方法将被结束。
 * 与break和continue不同的是，return直接结束整个方法，不管这个retrun处于多次层循环之内
 *
 *
 * 总结：
 * break只能用于switch语句和循环语句中。
 * continue只能用于循环语句中。
 * 二者功能类型，但是continue值终止本次循环，break是终止本层循环。
 * braek、continue之后不能有其他语句，因为程序永远不会执行其后的语句。
 */
public class ProcessContro {
    public static void main(String[] args) {
        //break语句例子
        for (int i = 1; i <= 10; i++) {
            for (int j = i; j <= 10; j++) {
                if (j > 6) {
                    break;//当j>6时，跳出内层循环，外层循环继续执行
                }
            }
            break;//当执行完内层循环时，语句运行到这里的时候，跳出外层循环，同时内层循环也不再执行
        }

        //continue语句例子
        for(int m = 0; m < 9; m++) {
            if (m % 2 == 0){
                continue;//当m % 2 == 0时，执行continue语句，结束当次循环，不会执行System.out.println(i)代码，直接进入下一次循环
            }
            System.out.println(m);
        }

        //retrun语句列子
        for (int k = 0; k < 9; k++){
            for (int l = 0; l < 9; l++){
                if (k == 7) {
                    return;//这块看起来和使用break感觉一样，但是实际上，retrun是把整个方法结束了，break只是终止当前的循环
                }
            }
            System.out.println(k);
        }
    }
}
