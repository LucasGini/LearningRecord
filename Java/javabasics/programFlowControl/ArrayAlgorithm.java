package javabasics.programFlowControl;

/**
 * 数组常见算法
 * 1.求数组元素的最大值、最小值、总和、平均数
 * 2.数组的复制、反转
 * 3.数组元素的排序：
 * （1）插入排序：
 *      直接插入排序、折半插入排序、shell排序
 * （2）交换排序：
 *      冒泡排序、快速排序（或分区交换排序）
 * （3）选择排序：
 *      简单选择排序、堆排序
 * （4）归并排序
 * （5）奇数排序
 *
 * 数组操作常见问题：
 * 1.数组下标越界异常(ArrayIndexOutOfBoundsException)
 * int[] arr = new int[2];
 * System.out.println(arr[2]);
 * 访问到了数组中不存在的脚标时发生
 *
 * 2.空指针异常(NullPointerException)
 * int[] arr = null;
 * System.out.println(arr[0])
 * arr引用没有指向实体，却在操作实体中的元素时。
 *
 */
public class ArrayAlgorithm {
    public static void main(String[] args) {
        int[] arr = new int[]{4, 2, 7, 1, 3, 5};
        //求数组最大值
        int max = arr[0];//假设arr[0]是最大值
        for (int i = 0; i < arr.length; i++ ) {
            if (max < arr[i]) {
                max = arr[i];//存放目前最大值
            }
        }
        System.out.println(max);

        // 求数组最小值
        int min = arr[0];//假设arr[0]是目前最小值
        for (int i = 0;i < arr.length; i++) {
            if (min > arr[i]) {
                min = arr[i];//存放目前最小的值
            }
        }
        System.out.println(min);

        //求数组的和、平均数
        int sum = 0;
        for (int i = 0; i < arr.length; i++){
            sum += arr[i]; //取出元素相加
        }
        System.out.println(sum);
        double d = sum;//因为怕不能整除，换成double格式
        System.out.println(d/arr.length);

        //复制数组
        int[] copy = new int[arr.length];//初始化一个数组copy，长度与arr一致
        for (int i = 0; i < arr.length; i++) {
            copy[i] = arr[i];//获取arr的元素，给copy对应的下标赋值
        }

        //反转数组
        int[] invert = new int[arr.length];//初始化一个数组copy，长度与arr一致
        for (int i = arr.length-1; i >= 0; i--) {
            //一个数组的最后一个元素，它的下标等于数组长度-1，元素的下标是从0开始
            invert[i] = arr[(arr.length-1)-i];//arr倒序取值，给invert正序下标赋值
        }

        //数组排序--冒泡排序
        //冒泡排序思想：相邻两元素进行比较，如有需要则进行交换，每完成一次循环就将最大元素排在最后（如从小到达排序），下次循环是将其他的数进行类似操作
        //4, 2, 7, 1, 3, 5
        //4, 2, 1, 3, 5, 7 第一轮得到一个最大的数字，放在倒数第一位
        //4, 2, 1, 3, 5, 7 第二轮得到除最后一个数字之外的最大数字，放在倒数第二位
        //2, 1, 3, 4, 5, 7 第三轮得到除最后两个数字之外的最大数字，放在倒数第三位
        //......
        //以此类推，只需重复循环数组长度-1次就可以排好序
        for (int i = 0; i< arr.length - 1; i++){//外层循环轮次，轮次循环的次数是数组长度-1
            for (int j = 0; j < arr.length - i - 1; j++) {
                int m = arr[j];
                if (arr[j] > arr[j + 1]) {//如果两个相邻的元素，前面的大于后面的，前后两个值交换
                    //正序与倒序排序，其他都是一致的，在判断两个相邻的元素在什么情况下做交换不一样
                    //如果是正序，前面的大于后面，交换
                    //如果是倒序，前面的小于后面，交换
                    arr[j] = arr[j + 1];
                    arr[j + 1] = m;
                }
            }
        }



    }
}
