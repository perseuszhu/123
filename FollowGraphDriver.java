package Homework7;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Scanner;
/**
 * @author Junjian Zhu R05
 *    e-mail: junjian.zhu@Stonybrook.edu
 *    Stony Brook ID: 111384808
 **/

public class FollowGraphDriver {
    /**
     * print user's menu
     * */
    private static void printUsers(FollowGraph followGraph, Scanner in) {
        boolean status = false;
        while (!status) {
            System.out.println();
            System.out.printf("    (SA) Sort Users by Name\n" +
                    "    (SB) Sort Users by Number of Followers\n" +
                    "    (SC) Sort Users by Number of Following\n" +
                    "    (Q) Quit");
            System.out.println();
            System.out.print("Enter a selection: ");
            String command = in.nextLine();
            if (command.equalsIgnoreCase("Q")) {
                status = true;
            }
            if (command.equalsIgnoreCase("SA")) {
                followGraph.printAllUsers(new NameComparator());
            } else if (command.equalsIgnoreCase("SB")) {
                followGraph.printAllUsers(new FollowersComparator(followGraph.getConnections()));
            } else if (command.equalsIgnoreCase("SC")) {
                followGraph.printAllUsers(new FollowingComparator(followGraph.getConnections()));
            } else {
                System.out.println("Wrong input");
            }
        }
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean status = false;
        FollowGraph followGraph = null;
        try {
            FileInputStream file = new FileInputStream("FollowGraph.obj");
            ObjectInputStream inStream = new ObjectInputStream(file);
            followGraph = (FollowGraph)inStream.readObject();
        } catch (Exception e) {
            followGraph = new FollowGraph();
        }
        while (!status) {
            System.out.println();
            System.out.printf("    (U) Add User\n" +
                    "    (C) Add Connection\n" +
                    "    (AU) Load all Users\n" +
                    "    (AC) Load all Connections\n" +
                    "    (P) Print all Users\n" +
                    "    (L) Print all Loops\n" +
                    "    (RU) Remove User\n" +
                    "    (RC) Remove Connection\n" +
                    "    (SP) Find Shortest Path\n" +
                    "    (AP) Find All Paths\n" +
                    "    (Q) Quit");
            System.out.println();
            System.out.print("Enter a selection: ");
            String command = in.nextLine();
            if (command.equalsIgnoreCase("Q")) {
                try {
                    FileOutputStream file = new FileOutputStream("FollowGraph.obj");
                    ObjectOutputStream outStream = new ObjectOutputStream(file);
                    outStream.writeObject(followGraph);
                    System.out.println("FollowGraph object saved into file FollowGraph.obj.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                status = true;
            }
            else if (command.equalsIgnoreCase("U")) {
                System.out.print("Please enter the name of the user: ");
                String name = in.nextLine();
                followGraph.addUser(name);
            } else if (command.equalsIgnoreCase("C")) {
                System.out.print("Please enter the source of the connection to add: ");
                String userFrom = in.nextLine();
                System.out.print("Please enter the dest of the connection to add: ");
                String userTo = in.nextLine();
                followGraph.addConnection(userFrom, userTo);
            } else if (command.equalsIgnoreCase("AU")) {
                System.out.print("Enter the file name: ");
                String filename = in.nextLine();
                followGraph.loadAllUsers(filename);
            } else if (command.equalsIgnoreCase("AC")) {
                System.out.print("Enter the file name: ");
                String filename = in.nextLine( );
                followGraph.loadAllConnections(filename);
            } else if (command.equalsIgnoreCase("P")) {
                printUsers(followGraph, in);
            } else if (command.equalsIgnoreCase("RU")) {
                System.out.print("Please enter the user to remove: ");
                String userName = in.nextLine();
                followGraph.removeUser(userName);
            } else if (command.equalsIgnoreCase("RC")) {
                System.out.print("Please enter the source of the connection to remove: ");
                String userFrom = in.nextLine();
                System.out.print("Please enter the dest of the connection to remove: ");
                String userTo = in.nextLine();
                followGraph.removeConnection(userFrom, userTo);
            } else if (command.equalsIgnoreCase("L")) {
                List<String> paths = followGraph.findAllLoops();
                System.out.println("There is " + paths.size() +" loop: ");
                for (String path: paths) {
                    System.out.println(path);
                }
            } else if (command.equalsIgnoreCase("SP")) {
                System.out.print("Please enter the desired source: ");
                String source = in.nextLine();
                System.out.print("Please enter the desired destination: ");
                String dest = in.nextLine();
                String path = followGraph.shortestPath(source, dest);
                System.out.println(path);
            } else if (command.equalsIgnoreCase("AP")) {
                System.out.print("Please enter the desired source: ");
                String source = in.nextLine();
                System.out.print("Please enter the desired destination: ");
                String dest = in.nextLine();
                List<String> paths = followGraph.allPaths(source, dest);
                System.out.println("There are a total of " + paths.size() + " paths:");
                for (String path: paths) {
                    System.out.println(path);
                }
            } else{
                System.out.println("Wrong input");
            }
        }
        System.out.println("Program terminating...");
    }
}
