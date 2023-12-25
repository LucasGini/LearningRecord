package work;

/**
 * 学生类
 */
public class Student {
    public String name = "zhangsan";//姓名
    public int age = 18;//年龄
    public String course = "数学";//课程
    public String interest = "唱条rep篮球";//兴趣

    /**
     * 显示学生个人信息
     */
    public void showPersonalInformation() {
        System.out.println("姓名：" + name);
        System.out.println("年龄：" + age);
        System.out.println("课程：" + course);
        System.out.println("兴趣：" + interest);
    }
}
