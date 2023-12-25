package work;

/**
 * 数组作业
 */
public class TestArray {
    public static void main(String[] args) {
        int[] array1;//声明array1数组
        int[] array2;//声明array2数组

        //初始化array1
        array1 = new int[]{2, 3, 5, 7, 11, 13, 17, 19};

        //显示array1的内容
        for (int i = 0; i < array1.length; i++) {
            System.out.println(array1[i]);
        }

        //赋值array2变量等于array1
        array2 = array1;

        //修改array2中的偶索引元素，使其等于索引值
        for (int i = 0; i <array2.length; i++) {
            if (i % 2 == 0) {
                array2[i] = i;
            }
        }
        //显示array2的内容
        for (int i = 0; i < array2.length; i++) {
            System.out.println(array2[i]);
        }

        //复制array2
        int[] array = new int[array1.length];
        for (int i = 0;i < array1.length; i++) {
            array[i] = array1[i];
        }
    }
}
