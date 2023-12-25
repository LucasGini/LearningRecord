package work;

/**
 * 动物的类
 * @author lee
 *
 */
public class Animal {
    String name; //动物名称
    int eye;  //眼睛的数量
    int legs; //有几条腿

    /**
     * 输出动物吃的食物
     * @param food 食物
     */
    public void eat(String food){
        System.out.println("此种动物的食物是：" + food);
    }

    /**
     * 动物的移动方式
     * @param moveType 移动方式
     */
    public void move(String moveType) {
        System.out.println("此种动物的移动方式是：" + moveType);
    }
}
