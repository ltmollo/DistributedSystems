import org.apache.zookeeper.*;

import java.io.IOException;

public class NodeWatcher implements Watcher {
    private final ZooKeeper zooKeeper;
    private final String processName;
    private final String node;
    private Process process;

    public NodeWatcher(String ipPort, String processName, String node) throws IOException, InterruptedException, KeeperException {
        this.zooKeeper = new ZooKeeper(ipPort, 3000, this);
        this.processName = processName;
        this.node = node;
        this.zooKeeper.addWatch("/", AddWatchMode.PERSISTENT_RECURSIVE);
    }

    private void handleNodeCreated(String path) {
        if (path.equals(node)) {
            startProcess();
        } else if (path.startsWith(node)) {
            System.out.println("Number of children nodes: " + getNumberOfChildrenNodes());
        }
        System.out.println("Created node: " + path);
    }

    private void handleNodeDeleted(String path) {
        if (path.equals(node)) {
            killProcess();
        }
        System.out.println("Deleted node: " + path);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        String path = watchedEvent.getPath();

        switch (watchedEvent.getType()) {
            case NodeCreated -> handleNodeCreated(path);
            case NodeDeleted -> handleNodeDeleted(path);
        }
    }

    private boolean nodeExists(String nodePath) throws InterruptedException, KeeperException {
        return zooKeeper.exists(nodePath, false) != null;
    }

    private void printNode(String nodePath, int level) {
        System.out.println("  ".repeat(level) + nodePath);
    }

    public void printTree(String nodePath, int level) throws InterruptedException, KeeperException {
        if (!nodeExists(nodePath)) {
            System.out.println( node + " node doesn't exist");
            return;
        }

        printNode(nodePath, level);
        zooKeeper.getChildren(nodePath, false)
                .forEach(child -> {
                    try {
                        String path = nodePath + "/" + child;
                        printTree(path, level + 1);
                    } catch (InterruptedException | KeeperException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private int getNumberOfChildrenNodes() {
        try {
            if (nodeExists(node)) {
                return zooKeeper.getAllChildrenNumber(node);
            }
        } catch (KeeperException | InterruptedException e) {
            System.out.println("Error while getting number of nodes");
            e.printStackTrace();
        }
        return 0;
    }

    private void startProcess() {
        if (process != null) {
            return;
        }

        System.out.println("Start: " + processName);
        try {
            process = Runtime.getRuntime().exec(processName);
        } catch (IOException e) {
            System.out.println("Error while starting process: " + processName);
            e.printStackTrace();
        }
    }

    private void killProcess() {
        if (process == null) {
            return;
        }

            System.out.println("Kill: " + processName);
            process.destroy();
            process = null;
        }
}
