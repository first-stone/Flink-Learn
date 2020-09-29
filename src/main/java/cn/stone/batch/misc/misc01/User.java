package cn.stone.batch.misc.misc01;

/**
 * @Author: cn.stone
 * @Description:
 * @Date: Created in 9:59 2020/9/19
 * @Modified By:
 */
public class User {
    public int userIdentifier;
    public String name;

    public User() {
    }

    public User(int userIdentifier, String name) {
        this.userIdentifier = userIdentifier;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "userIdentifier=" + userIdentifier +
                ", name='" + name + '\'' +
                '}';
    }
}
