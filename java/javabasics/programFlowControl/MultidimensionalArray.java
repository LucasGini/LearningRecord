package javabasics.programFlowControl;

/**
 * 多维数组
 * 二维数组[][]：数组中的数组
 * 格式1（动态初始化）：int[][] arr = new int[3][2];
 * 定义了名称为arr的二维数组
 * 二维数组中有3个一维数组
 * 每个一维数组中有2个元素
 * 一维数组的名称分别为arr[0],arr[1],arr[2]
 * 给第一个一维数组1脚标位赋值为78写法是：arr[0][1] = 78;
 *
 * 格式2（动态初始化）：int[][] arr = new int[3][];
 * 二维数组中有3个一维数组
 * 每个一维数组都是默认初始化值null
 * 可以对这三个一维数组分辨进行初始化
 * arr[0] = new int[3]; arr[1] = new int[1]; arr[2] = new int[2];
 * 注意：int[][] arr = new int[][3]; //非法
 *
 * 格式2（静态初始化）：int[][] arr = new int[][]{{3, 8, 2}, {2, 7}, {9, 0, 1 ,6}}
 * 定义了名称为arr的二维数组，二维数组中有3个一维数组
 * 每一个一维数组中具体元素也都已经初始化
 * 第一个一维数组arr[0] = {3, 8, 2};
 * 第二个一维数组arr[1] = {2, 7};
 * 第三个一维数组arr[2] = {9, 0, 1 ,6};
 * 第三个一维数组的长度表示方式：arr[2].length;
 *
 * 注意：
 * 特殊写法情况：int[] x, y[]; x是一维数组，y是二维数组。
 * Java中多维数组不必都是规律矩阵形式
 */
public class MultidimensionalArray {
    public static void main(String[] args) {
        //二维数组可以理解为：一维数组中的每个元素都是一个数组
        //静态初始化
        int[][] ii0 = new int[][]{
                {1, 8},
                {4, 7},
                {3, 5, 0, 9}
        };
        //二维数组取数
        //获取二维数组第二个数组的第一个元素
        System.out.println(ii0[1][0]);

        //动态初始化方法1
        int[][] ii1 = new int[2][3];//第一维部分的长度是2，第二维也就是第一维的每个元素的长度是3

        //动态初始化方法2
        int[][] ii2 = new int[3][];//只定义第一维的长度，第二维不定义

        int[] x, y[]; //特殊写法，x是一维数组，y是二维数组


        //练习
        //获取arr数组中所有元素的和。
        int[][] arr = new int[][]{{3, 8, 2}, {2, 7}, {9, 0, 1 ,6}};
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                sum += arr[i][j];
            }
        }
        System.out.println(sum);
    }
}
