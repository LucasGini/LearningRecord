package javabasics.objectOrientedProgramming;//指定该文件定义的类所在的包
import work.*;//引用包work下面的类

/**
 * 包package和引用import
 *
 * 软件包：
 * 包帮助管理大型软件系统：将语义近似的类组织到包中；解决类名冲突问题。
 * 包可以包含类和子包
 * 例：某航运软件系统包括：一组域对象、GUI和reports子系统
 *
 * 关键字--package
 * package语句作为java源文件的第一条语句，指明该文件定义的类所在的包。
 * 若缺省该语句，则指定为无包名。
 * 格式：package 顶层包名.子包名;
 * 包对应文件系统的目录，package语句中，用"."来指明包（目录）的层次；
 * 包通常用小写单词，类名首字母通常大写。
 *
 * 关键字--import
 * 为使用定义在不同包中的java类，需要import语句来引入指定包层次下所需要的类或全部类(.*)。import语句告诉编译器到哪里其寻找类。
 * 语法格式：import 包名[.子包名...].<类名｜*>
 * 注意：
 * 若引入的包为：java.lang，则编译器默认获取此包下的类，不需要再显示声明
 * import语句出现在package语句之后、类定义之前
 * 一个源文件中可包含多个import语句
 * 可以使用import lee.*;语句，表面导入lee包下的所有类。而lee包下sub子包内的类则不会被导入。import lee.sub.*;
 * import语句不是必须的，可坚持在类里使用其他类的全名
 * JDK 1.5加入import static语句
 *
 * JDK中主要的包介绍
 * 1、java.lang----包含一些java语言的核心类，如String、Math、Integer、System和Thread，提供常用功能。
 * 2、java.net----包含执行与网络相关操作的类和接口
 * 3、java.io---包含提供多种输入/输出功能的类。
 * 4、java.util----包含一些实用工具类，如定义系统特性、接口的集合框架类、使用与日期日历相关的函数。
 * 5、java.text----包含了java格式化相关的类
 * 6、java.sql----包含了java进行JDBC数据库编程的相关类/接口
 * 7、java.awt----包含类构成抽象窗口工具集（abstract Windows toolkits）的多个类，这些类被用来构建和管理应用程序的图形用户界面（GUI
 * 8、java.applet----包含applet运行所需的一些类。
 */
public class PackageAndImport {
    Person p = new Person();
}
