package Homework7;
import java.io.File;
import java.io.Serializable;
import java.util.*;
/**
 * @author Junjian Zhu R05
 *    e-mail: junjian.zhu@Stonybrook.edu
 *    Stony Brook ID: 111384808
 **/
public class FollowGraph implements Serializable {
    private ArrayList<User> users;
    private static final int MAX_USERS = 100;
    private boolean[][] connections;

    public boolean[][] getConnections() {
        return connections;
    }

    public FollowGraph() {
        users = new ArrayList<>();
        connections = new boolean[MAX_USERS][MAX_USERS];

        for (int i = 0;i < MAX_USERS; ++i) {
            for (int j = 0;j < MAX_USERS; ++j) {
                connections[i][j] = false;
            }
        }
    }

    /**
     * add user to users
     * @param userName
     * */
    public void addUser(String userName) {
        for (User user: users) {
            if (user.getUserName().equalsIgnoreCase(userName)) {
                return ;
            }
        }
        User user = new User(userName);
        users.add(user);
        System.out.println(userName + " has been added");
    }

    /**
     * remove user from users
     * @param userName
     * */
    public void removeUser(String userName) {
        System.out.println("This feature can't be implemented easily");
    }

    /**
     * add connection between userFrom and userTo
     * @param userFrom
     * @param userTo
     * */
    public void addConnection(String userFrom, String userTo) {
        User userFromEntity = null, userToEntity = null;
        for (User user: users) {
            if (user.getUserName().equalsIgnoreCase(userFrom)) {
                userFromEntity = user;
                break;
            }
        }
        for (User user: users) {
            if (user.getUserName().equalsIgnoreCase(userTo)) {
                userToEntity = user;
                break;
            }
        }
        if (userFromEntity != null && userToEntity != null) {
            System.out.println(userFrom + ", " + userTo + " added");
            //System.out.println("WARNING " + userFromEntity.getIndexPos() + " with " + userToEntity.getIndexPos() + " Set True");
            connections[userFromEntity.getIndexPos()][userToEntity.getIndexPos()] = true;
        }
    }

    /**
     * remove connection between userFrom and userTo
     * @param userFrom
     * @param userTo
     * */
    public void removeConnection(String userFrom, String userTo) {
        User userFromEntity = null, userToEntity = null;
        for (User user: users) {
            if (user.getUserName().equalsIgnoreCase(userFrom)) {
                userFromEntity = user;
                break;
            }
        }
        for (User user: users) {
            if (user.getUserName().equalsIgnoreCase(userTo)) {
                userToEntity = user;
                break;
            }
        }
        if (userFromEntity == null || userToEntity == null) {
            System.out.println("No user exists");
        } else {
            connections[userFromEntity.getIndexPos()][userToEntity.getIndexPos()] = false;
        }
    }

    /**
     * calculate the shortest path between userFrom and userTo
     * @param userFrom
     * @param userTo
     * */
    public String shortestPath(String userFrom, String userTo) {
        User userFromEntity = null, userToEntity = null;
        for (User user: users) {
            if (user.getUserName().equalsIgnoreCase(userFrom)) {
                userFromEntity = user;
                break;
            }
        }
        for (User user: users) {
            if (user.getUserName().equalsIgnoreCase(userTo)) {
                userToEntity = user;
                break;
            }
        }
        if (userFromEntity == null || userToEntity == null) {
            System.out.println("No user exists");
            return "";
        }
        Map<Integer, String> map = new HashMap<>();
        for (User user: users) {
            map.put(user.getIndexPos(), user.getUserName());
        }

        int V = users.size();
        int[][] dist = new int[V][V];
        int[][] next = new int[V][V];
        for (int i = 0;i < V; ++i) {
            for (int j = 0;j < V; ++j) {
                dist[i][j] = Integer.MAX_VALUE;
                next[i][j] = -1;
            }
        }
        for (int u = 0;u < V; ++u) {
            for (int v = 0;v < V; ++v) {
                if (connections[u][v]) {
                    dist[u][v] = 1;
                    next[u][v] = v;
                }
            }
        }
        for (int k = 0;k < V; ++k) {
            for (int i = 0;i < V; ++i) {
                for (int j = 0;j < V; ++j) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
        int u = userFromEntity.getIndexPos(), v = userToEntity.getIndexPos();
        if (next[u][v] == -1) {
            return "";
        }
        String path = map.get(u);
        while (u != v) {
            //System.out.print(u + " => " + v + " = ");
            u = next[u][v];
            if (u == -1) {
                break;
            }
            path = path + " -> " + map.get(u);
        }
        return path + " -> " + map.get(v);
    }

    /**
     * get all path from userFrom to userTo
     * @param userFrom
     * @param userTo
     * */
    public List<String> allPaths(String userFrom, String userTo) {
        return BFS.allPath(userFrom, userTo, users, connections);
    }

    /**
     * display user by comparator
     * @param comparator
     * */
    public void printAllUsers(Comparator<User> comparator) {
        System.out.println("Users:");
        System.out.println("User Name               Number of Followers      Number Following");
        ArrayList<User> tmpUser = new ArrayList<>();
        for (User user: users) {
            tmpUser.add(user);
        }
        tmpUser.sort(comparator);
        for (User user: tmpUser) {
            int followers = 0, following = 0;
            for (int i = 0;i < MAX_USERS; ++i) {
                if (connections[user.getIndexPos()][i]) {
                    ++following;
                }
            }
            for (int i = 0;i < MAX_USERS; ++i) {
                if (connections[i][user.getIndexPos()]) {
                    ++followers;
                }
            }
            System.out.printf("%-25s", user.getUserName());
            System.out.print("         ");
            System.out.printf("%-16d", followers);
            System.out.print("        ");
            System.out.println(following);
        }
    }

    /**
     * display all followers
     * @param userName
     * */
    public void printAllFollowers(String userName) {
        System.out.println("Followers:");
        User target = null;
        for (User user: users) {
            if (user.getUserName().equalsIgnoreCase(userName)) {
                target = user;
                break;
            }
        }
        if (target == null) {
            System.out.println("No such user exists");
            return ;
        }
        for (int i = 0;i < MAX_USERS; ++i) {
            if (connections[i][target.getIndexPos()]) {
                System.out.println(users.get(i).getUserName());
            }
        }
    }

    /**
     * display all followings
     * @param userName
     * */
    public void printAllFollowing(String userName) {
        System.out.println("Following:");
        User target = null;
        for (User user: users) {
            if (user.getUserName().equalsIgnoreCase(userName)) {
                target = user;
                break;
            }
        }
        if (target == null) {
            System.out.println("No such user exists");
            return ;
        }
        for (int i = 0;i < MAX_USERS; ++i) {
            if (connections[target.getIndexPos()][i]) {
                System.out.println(users.get(i).getUserName());
            }
        }
    }

    /**
     * find all loops in graph
     * */
    public List<String> findAllLoops() {
        List<String> allLoops = new ArrayList<>();
        for (User user: users) {
            List<String> loops = BFS.allLoop(user.getUserName(), users, connections);
            for (String loop: loops) {
                boolean exist = false;
                for (String l: allLoops) {
                    if (BFS.pathEqual(l, loop)) {
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    allLoops.add(loop);
                }
            }
        }
        return allLoops;
    }

    /**
     * load users
     * @param filename
     * */
    public void loadAllUsers(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNext()) {
                String name = scanner.nextLine().trim();
                if (name.equals("")) {
                    continue;
                }
                addUser(name);
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("No file exists!");
        }
    }

    /**
     * load connections
     * @param filename
     * */
    public void loadAllConnections(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNext()) {
                String line = scanner.nextLine().trim();
                if (line.equals("")) {
                    continue;
                }
                String[] attrs = line.split(",");
                String userFrom = attrs[0].trim();
                String userTo = attrs[1].trim();
                addConnection(userFrom, userTo);
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("No file exists!");
        }
    }
}
