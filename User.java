package Homework7;
import java.io.Serializable;
/**
 * @author Junjian Zhu R05
 *    e-mail: junjian.zhu@Stonybrook.edu
 *    Stony Brook ID: 111384808
 **/
public class User implements Serializable {
    private String userName;
    private int indexPos;
    public static int userCount = 0;

    public User(String userName) {
        this.userName = userName;
        indexPos = userCount;
        ++userCount;
    }

    public User() {
        indexPos = userCount;
        ++userCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getIndexPos() {
        return indexPos;
    }

    public static int getUserCount() {
        return userCount;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", indexPos=" + indexPos +
                '}';
    }
}
