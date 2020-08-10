package Homework7;

import java.util.Comparator;
/**
 * @author Junjian Zhu R05
 *    e-mail: junjian.zhu@Stonybrook.edu
 *    Stony Brook ID: 111384808
 **/
public class FollowingComparator implements Comparator<User> {
    private boolean[][] connections;
    FollowingComparator(boolean[][] connections) {
        this.connections = connections;
    }
    @Override
    public int compare(User o1, User o2) {
        int following1 = 0, following2 = 0;
        for (int i = 0;i < connections.length; ++i) {
            if (connections[o1.getIndexPos()][i]) {
                ++following1;
            }
        }
        for (int i = 0;i < connections.length; ++i) {
            if (connections[o2.getIndexPos()][i]) {
                ++following2;
            }
        }
        if (following1 < following2) {
            return 1;
        }
        if (following1 > following2) {
            return -1;
        }
        return 0;
    }
}