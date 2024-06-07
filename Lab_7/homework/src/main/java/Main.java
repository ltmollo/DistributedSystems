import org.apache.zookeeper.KeeperException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        String ipPort;
        String processName;

        if (args.length == 2) {
            System.out.println("Using arguments provided");
            ipPort = args[0];
            processName = args[1];
        } else {
            System.out.println("Using default arguments, you can also pass yours: IP:PORT PROCESS_NAME");
            ipPort = "127.0.0.1:2181";
            processName = "mspaint";
        }

        String A_NODE = "/a";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        NodeWatcher nodeWatcher = new NodeWatcher(ipPort, processName, A_NODE);

        while (true) {

            System.out.println("Try 'printTree' or 'exit'");
            String input = reader.readLine();

            switch (input) {
                case "printTree" -> nodeWatcher.printTree(A_NODE, 0);
                case "exit" -> System.exit(0);
                default -> System.out.println("Unknown command");
            }
        }
    }
}