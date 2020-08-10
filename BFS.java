package Homework7;

import java.util.*;
/**
 * @author Junjian Zhu R05
 *    e-mail: junjian.zhu@Stonybrook.edu
 *    Stony Brook ID: 111384808
 **/
public class BFS {

    static class BFSNode {
        String name;
        BFSNode previous;

        public BFSNode(String name) {
            this.name = name;
            previous = null;
        }

        public BFSNode(String name, BFSNode previous) {
            this.name = name;
            this.previous = previous;
        }

        public boolean visited(String name) {
            if (this.name.equals(name)) {
                return true;
            }
            if (previous == null) {
                return false;
            }
            return previous.visited(name);
        }

        public String toPath() {
            if (previous == null) {
                return name;
            }
            String subPath = previous.toPath();
            return subPath + " -> " + name;
        }
    }

    /**
     * get all path from start to end use BFS algorithm
     * @param start
     * @param end
     * @param users
     * @param connections
     * */
    public static List<String> allPath(String start, String end, ArrayList<User> users, boolean[][] connections) {
        Map<String, User> map = new HashMap<>();
        for (User user: users) {
            map.put(user.getUserName(), user);
        }
        Queue<BFSNode> queue = new LinkedList<>();
        queue.add(new BFSNode(start));
        List<String> paths = new ArrayList<>();
        if (!map.containsKey(start) || !map.containsKey(end)) {
            return paths;
        }
        while (!queue.isEmpty()) {
            BFSNode node = queue.poll();
            if (node.name.equals(end)) {
                paths.add(node.toPath());
                continue;
            }
            User user = map.get(node.name);
            for (int i = 0;i < connections.length; ++i) {
                if (connections[user.getIndexPos()][i]) {
                    User following = users.get(i);
                    if (!node.visited(following.getUserName())) {
                        queue.add(new BFSNode(following.getUserName(), node));
                    }
                }
            }
        }
        return paths;
    }

    /**
     * check if two paths are equal
     * @param path1
     * @param path2
     * */
    public static boolean pathEqual(String path1, String path2) {
        Set<String> pathset1 = new HashSet<>();
        String[] attrs1 = path1.split("->");
        for (String attr: attrs1) {
            pathset1.add(attr.trim());
        }
        Set<String> pathset2 = new HashSet<>();
        String[] attrs2 = path2.split("->");
        for (String attr: attrs2) {
            pathset2.add(attr.trim());
        }
        return pathset1.equals(pathset2);
    }

    /**
     * get all loops in graph start from someone use BFS algorithm
     * @param start
     * @param users
     * @param connections
     * */
    public static List<String> allLoop(String start, ArrayList<User> users, boolean[][] connections) {
        Map<String, User> map = new HashMap<>();
        for (User user: users) {
            map.put(user.getUserName(), user);
        }
        Queue<BFSNode> queue = new LinkedList<>();
        queue.add(new BFSNode(start));
        List<String> paths = new ArrayList<>();
        if (!map.containsKey(start)) {
            return paths;
        }
        while (!queue.isEmpty()) {
            BFSNode node = queue.poll();
            User user = map.get(node.name);
            for (int i = 0;i < connections.length; ++i) {
                if (connections[user.getIndexPos()][i]) {
                    User following = users.get(i);
                    if (!node.visited(following.getUserName())) {
                        queue.add(new BFSNode(following.getUserName(), node));
                    } else if (following.getUserName().equals(start)) {
                        // loop
                        // System.out.println("what " + following.getIndexPos() + " " + following.getUserName());
                        paths.add(node.toPath());
                    }
                }
            }
        }
        return paths;
    }
}
