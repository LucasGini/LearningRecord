package work;

/**
 * 练习
 */
public class Person1 {
    public String name;
    public int age;
    public int sex;

    /**
     * 学习
     */
    public void study() {
        System.out.println("studying");
    }

    /**
     * 输出年龄
     */
    public void showAge() {
        System.out.println(age);
    }

    /**
     * 年龄加2
     * @return
     */
    public int addAge(int i) {
        age +=i;
        return age;
    }
}
