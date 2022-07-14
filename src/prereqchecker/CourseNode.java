package prereqchecker;

import java.util.*;

public class CourseNode {
    String id;
    HashSet<CourseNode> connections;
    // int V;

    /**
     * Create a new CourseNode
     * @param id
     * @param v
     * @return a class node
     */
    public CourseNode(String id) {
        this.id = id;
        this.connections = new HashSet<CourseNode>();
        // this.V = v;
    }

    public String getId() {
        return this.id;
    }

    public HashSet<CourseNode> getConnections() {
        return this.connections;
    }
    
    public CourseNode[] toArray() {
        return (CourseNode[]) this.connections.toArray();
    }

    public int getSize() {
        return this.connections.size();
    }

    public void print() {
        StdOut.print(this.id);
        StdOut.print(" ");

        connections.stream().forEach(course -> {
            StdOut.print(course.id);
            StdOut.print(" ");
        });
    }
    
}
