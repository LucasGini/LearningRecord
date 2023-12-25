package javabasics.programFlowControl;

/**
 * 一维数组
 * 1、一维数组声明方式：
 * type var[] 或 type[] var;
 * 例如：
 * int a[];
 * int[] a;
 * double b[];
 * Mydate[] c; //对象数组
 *
 * 2、一维数组初始化：
 * 动态初始化：数组声明且为数组元素分配空间与赋值的操作分开进行
 * int[] arr = new int[3];
 * arr[0] = 3;
 * arr[1] = 9;
 * arr[2] = 8;
 *
 * 静态初始化：在定义数组的同时就为数组元素分配空间并赋值。
 * int a[] = new int[]{3,9,8};
 * int[] a = {3,9,8};
 *
 * 3、数组元素的引用：
 * 定义并用元素符new为之分配空间后，才可以引用数组中的每个元素；
 * 数组元素的引用方式：数组名[数组下标]
 *    数组元素下标可以是整型常量或整型表达式。如a[3],b[i],c[6*i];
 *    数组元素下标从0开始；长度为n的数组合法下标取值范围：0-->n-1；如int a[] = new int[3]；可引用的数组元素为a[0]、a[1]、a[2]
 * 每个数组都有一个属性length指明它的长度，例如:a.length指明数组a的长度（元素个数）
 *    数组一旦初始化，其长度是不可变的
 *
 * 4、数组元素的默认初始化
 * 数组是引用类型，他的元素相当于类的成员变量，因此数组一经分配空间，其中的每个元素也被按照成员变量同样的方式被隐式初始化
 * 例如：
 * public class Test {
 *     public static void main(String[] args) {
 *         int a[] = new int[5];
 *         System.out.println(a[3]); //a[3]的默认值为0
 *     }
 * }
 */
public class UnidimensionalArray {
    public static void main(String[] args) {
        int i = 0;
        int k = 1;
        int m = 2;
        //想把多个多数据放到一个变量里？使用数组，就是存放多个数据多集合
        //例如，存放多个int类型的数据
        int[] ii;//声明一个int的数组

        //动态初始化
        int[] arr = new int[4];//声明一个能放四个int类型数据的数组

        //静态初始化
        int[] brr = new int[] {1, 2, 3, 4};//声明一个存放了四个数字的数组
        int[] crr = {1, 2, 3, 4};

        //数组引用
        String[] strs = new String[]{"c", "a", "b"};//数组内的元素都有一个引用的元素下标，这个的下标是个数字，数字是从左到右从0开始
        System.out.println(strs[0]);//取出数组strs数组下标为0的元素
        System.out.println(strs.length);//获取数组strs长度

        //数组元素默认初始化
        int[] a = new int[5];//使用动态初始化的时候，数组的元素会有默认值，数组类型的默认值是0，对象的默认类型是null
        System.out.println(a[3]); //a[3]的默认值为0

        //数组赋值
        int[] drr = new int[4];
        System.out.println(drr[0]);
        drr[0] = 2;
        System.out.println(drr[0]);

    }
}
