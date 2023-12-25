package javabasics.operator;

/**
 * 三元运算符
 * 1、格式
 * (条件表达式)?表达式1: 表达式2
 * 条件为true，运算后的结果是表达式1；
 * 条件为false，运算后的结果表达式2；
 */
public class TernaryOperator {
    public static void main(String[] args) {
        int x = 1;
        int y = 2;
        int k = x > y ? x : y;
        System.out.println(k);

        int i = 3;
        int h = (x > y && x > i) ? x : (y > i ? y : i);
        System.out.println(h);

    }
}
