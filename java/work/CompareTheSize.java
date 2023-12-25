package work;

/**
 * 比较大小
 */
public class CompareTheSize {
    public void max(int x, int y) {
        if (x > y) {
            System.out.println("最大值是：" + x);
        }
        else {
            System.out.println("最大值是：" + y);
        }
    }

    public void max(double x, double y) {
        if (x > y) {
            System.out.println("最大值是：" + x);
        }
        else {
            System.out.println("最大值是：" + y);
        }
    }
    public void max(double x, double y, double z) {
        double i = (x > y && x > z)? x : (y > z ? y : z);
        System.out.println("最大值是：" + i);
    }
}
