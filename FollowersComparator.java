package Homework7;

import java.util.Comparator;
/**
 * @author Junjian Zhu R05
 *    e-mail: junjian.zhu@Stonybrook.edu
 *    Stony Brook ID: 111384808
 **/
public class FollowersComparator implements Comparator<User> {
    private boolean[][] connections;
    FollowersComparator(boolean[][] connections) {
        this.connections = connections;
    }
    /**
     * compare follower count
     * */
    @Override
    public int compare(User o1, User o2) {
        int followers1 = 0, followers2 = 0;
        for (int i = 0;i < connections.length; ++i) {
            if (connections[i][o1.getIndexPos()]) {
                ++followers1;
            }
        }
        for (int i = 0;i < connections.length; ++i) {
            if (connections[i][o2.getIndexPos()]) {
                ++followers2;
            }
        }
        if (followers1 < followers2) {
            return 1;
        }
        if (followers1 > followers2) {
            return -1;
        }
        return 0;
    }
}
