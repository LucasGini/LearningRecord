package javabasics.operator;

/**
 * 运算符优先级
 * ()小括号的优先级最高，一般写代码的时候会使用小括号进行优先级的分配
 */
public class OperatorPrecedence {
    public static void main(String[] args) {
        System.out.println(4 - 2 * 3);//结果为-2，因为乘号的优先级比减号高，所以先2*3=6 再用4-6=-2
        System.out.println(2 * (3 - 4));//结果为-2，因为小括号的优先级比乘号高，所以先3-4=-1，再用2*-1=-2
    }
}
