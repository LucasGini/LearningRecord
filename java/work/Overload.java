package work;

/**
 * 重载
 */
public class Overload {

    public void mOL(int x){
        int i = x * x;
        System.out.println(i);
    }

    public void mOL(int x, int y) {
        int i = x * y;
        System.out.println(i);
    }

    public void mOL(String x) {
        System.out.println(x);
    }
}
