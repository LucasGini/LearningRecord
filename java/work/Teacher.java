package work;

/**
 * 老师类
 */
public class Teacher {

    public String name = "lisi";
    public String specialty = "计算机科学";
    public String course = "Java";
    public int teachingAge = 16;

    public void showPersonalInformation(){
        System.out.println("姓名：" + name);
        System.out.println("专业：" + specialty);
        System.out.println("教授的课程：" + course);
        System.out.println("教龄：" + teachingAge);
    }

}
