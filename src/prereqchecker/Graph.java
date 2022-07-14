package prereqchecker;

import java.util.*;

/**
 * Uses ClassNode to create an ACyclic Graph of prereq connections
 */
public class Graph {
    HashSet<CourseNode> edges;
    int size;

    public Graph() {
        this.edges = new HashSet<CourseNode>();
    }

    public Graph(String input) {
        this.edges = new HashSet<CourseNode>();

        StdIn.setFile(input);

        int count = Integer.parseInt(StdIn.readLine());        
        for (int i = 0; i < count; i++) add(StdIn.readLine());

        int edges = Integer.parseInt(StdIn.readLine());
        for (int i = 0 ; i < edges; i++) {
            String[] e = StdIn.readLine().split(" ");

            String preqFor = e[0];
            String is = e[1];

            CourseNode a = get(preqFor);
            CourseNode b = get(is);
            connect(a, b);
        }
    }

    public Graph(String input, String output) {
        this.edges = new HashSet<CourseNode>();

        StdIn.setFile(input);

        int count = Integer.parseInt(StdIn.readLine());
        for (int i = 0; i < count; i++) add(StdIn.readLine());

        int edges = Integer.parseInt(StdIn.readLine());
        for (int i = 0 ; i < edges; i++) {
            String[] e = StdIn.readLine().split(" ");

            String preqFor = e[0];
            String is = e[1];

            CourseNode a = get(preqFor);
            CourseNode b = get(is);
            connect(a, b);
        }

        StdOut.setFile(output);
        print();
    }

    /**
     * 
     * @param id
     * @param v
     * @return
     */
    public boolean add(String id) {
        CourseNode c = new CourseNode(id);
        if (edges.add(c)) {
            return true;
        } else return false;
    }

    public CourseNode get(String id) {
        if (!exists(id)) return null;

        Optional<CourseNode> c = edges.stream()
            .filter(course -> course.id.equals(id))
            .findFirst();

        if (c.isPresent()) return c.get();
        else return null;

    }
    
    public boolean exists(String id) {
        Optional<CourseNode> c = edges.stream()
            .filter(course -> course.id.equals(id))
            .findFirst();

        return c.isPresent();
    }

    public boolean connect(CourseNode a, CourseNode b) {
        return a.connections.add(b);
    }

    public boolean unconnect(CourseNode a, CourseNode b) {
        return a.connections.remove(b);
    }

    /**
     * Uses DFS
     * @param s Source node
     * @return Prereqs for a course
     */
    public HashSet<CourseNode> prereqsAre(CourseNode s) {
        HashMap<CourseNode, Boolean> visited = new HashMap<CourseNode, Boolean>();

        visited.put(s, true);
        Queue<CourseNode> q = new LinkedList<CourseNode>();
        q.add(s);

        HashSet<CourseNode> visited_nodes = new HashSet<CourseNode>();

        while (!q.isEmpty()) {
            s = q.poll();
            Iterator<CourseNode> i = s.connections.iterator();

            while (i.hasNext()) {
                CourseNode next = i.next();
                if (!visited.containsKey(next)) {
                    visited.put(next, true);
                    q.add(next);
                    visited_nodes.add(next);
                }
            }

        }

        return visited_nodes;
    }

    /**
     * Uses DFS
     * @param s Source node
     * @return Prereqs for a course
     */
    public HashSet<CourseNode> dfs(CourseNode s) {
        HashMap<CourseNode, Boolean> visited = new HashMap<CourseNode, Boolean>();

        visited.put(s, true);
        Queue<CourseNode> q = new LinkedList<CourseNode>();
        q.add(s);

        HashSet<CourseNode> visited_nodes = new HashSet<CourseNode>();

        while (!q.isEmpty()) {
            s = q.poll();
            Iterator<CourseNode> i = s.connections.iterator();

            while (i.hasNext()) {
                CourseNode next = i.next();
                if (!visited.containsKey(next)) {
                    visited.put(next, true);
                    q.add(next);
                    visited_nodes.add(next);
                }
            }

        }

        return visited_nodes;
    }

    public HashMap<CourseNode, Integer> dfsDistance(CourseNode s) {
        HashMap<CourseNode, Boolean> visited = new HashMap<CourseNode, Boolean>();

        visited.put(s, true);
        Queue<CourseNode> q = new LinkedList<CourseNode>();
        q.add(s);

        HashSet<CourseNode> visited_nodes = new HashSet<CourseNode>();
        HashMap<CourseNode, Integer> result = new HashMap<CourseNode, Integer>();

        while (!q.isEmpty()) {
            s = q.poll();
            Iterator<CourseNode> i = s.connections.iterator();

            while (i.hasNext()) {
                CourseNode next = i.next();
                if (!visited.containsKey(next)) {
                    visited.put(next, true);
                    q.add(next);
                    visited_nodes.add(next);

                    result.put(next, 1);
                } else {
                    int current = result.get(next);
                    result.put(next, current + 1);
                }
            }

        }

        return result;
    }

    public HashSet<CourseNode> prereqsFor(CourseNode n) {
        HashSet<CourseNode> s = new HashSet<CourseNode>();

        for (CourseNode connection : n.connections) {
            s.add(connection);
        }
        return s;
    }
    /**
     * If you have a graph and all the edges are weighted the same, BFS is better. Else Dikstra
     * --> BFS has a runtime of O(n), Dikstra of O(n^2)
     */

    public HashMap<CourseNode, Integer> bfs(CourseNode source) {
        Queue<CourseNode> q = new LinkedList<CourseNode>();
        q.offer(source);

    //    Boolean[] visited = new Boolean[edges.size()];
       HashMap<CourseNode, Integer> visited = new HashMap<CourseNode, Integer>();

       visited.put(source, 1);

       while (!q.isEmpty()) {
           CourseNode v = q.poll();

           v.connections.forEach(neighbor -> {
               if (!visited.containsKey(neighbor)) {
                    q.offer(neighbor);
                    visited.put(neighbor, 1);
               } else {
                   visited.replace(neighbor, visited.get(neighbor), visited.get(neighbor)+1);
               }
           });
       }

        return visited;
    }

    public HashSet<CourseNode> bfsTo(CourseNode s, CourseNode target) {
        HashMap<CourseNode, Boolean> visited = new HashMap<CourseNode, Boolean>();
        HashSet<CourseNode> result = new HashSet<CourseNode>();

        visited.put(s, true);
        Queue<CourseNode> q = new LinkedList<CourseNode>();
        q.add(s);

        while (q.size() != 0) {

            // dequeue a vertetx from a queue and print it
            s = q.poll();
            StdOut.println(s.id);

            // Get all adjacent vertices of the dequeued vertex s
            // If an adjacent has not been visited, then mark it visited
            // and enqueue it.

            Iterator<CourseNode> i = s.connections.iterator();
            while (i.hasNext()) {
                CourseNode next = i.next();
                if (next.equals(target)) { break; }
                if (!visited.containsKey(next)) {
                    visited.put(next, true);
                    result.add(next);
                    q.add(next);
                }
            }

            

        }

        return result;

         
    }

    public HashSet<CourseNode> preqsFor(CourseNode n) {
        HashSet<CourseNode> hs = new HashSet<CourseNode>();

        edges.forEach(course -> {
            course.connections.forEach(prereq -> {
                if (prereq == n) {
                    hs.add(course);
                }
            });
        });

        return hs;

    }

    // takes in classes (and their prereqs) 
    public HashSet<CourseNode> findEligible(HashSet<CourseNode> completedCourses) {
        HashSet<CourseNode> eligble = new HashSet<CourseNode>();
        HashSet<CourseNode> taken = new HashSet<CourseNode>();

        completedCourses.forEach(course -> {
            taken.add(course);
            HashSet<CourseNode> prereqs = this.dfs(course);
            taken.addAll(prereqs);
        });

        edges.forEach(course -> {
            if (!taken.contains(course)) {
                if (taken.containsAll(course.connections)) {
                    eligble.add(course);
                }
            }
        });


        return eligble;

    }


    public HashSet<CourseNode> findPrereqsForCourse(CourseNode course) {
        HashSet<CourseNode> prereqs = new HashSet<CourseNode>();
        course.connections.forEach(edge -> prereqs.add(edge));

        return prereqs;
    }

    public HashSet<CourseNode> eligible(String[] c) {
        HashSet<CourseNode> e = new HashSet<CourseNode>();

        for (String courseString : c) {
            preqsFor(get(courseString)).forEach(course -> e.add(course));
        }

        return e;
    }

    public HashSet<CourseNode> needToTake(CourseNode target, HashSet<CourseNode> completedCourses) {
        HashSet<CourseNode> needToTake = new HashSet<CourseNode>();
        HashSet<CourseNode> taken = new HashSet<CourseNode>();

        completedCourses.forEach(course -> {
            taken.add(course);
            HashSet<CourseNode> prereqs = this.dfs(course);
            taken.addAll(prereqs);
        });

        HashSet<CourseNode> targetPrereqs = this.dfs(target);

        targetPrereqs.forEach(prereq -> {
            if (!taken.contains(prereq)) {
                needToTake.add(prereq);
            }
        });

        return needToTake;

    }

    // max 3 courses per semester
    /**
     * Use topological sort
     * @param target
     * @param completedCourses
     */

    /**
     * Input file contains the following:
     * [line 1] courseId [target course]
     * [line 2] e [number of taken courses]
     * [line 3] e kines, each with one courseId [taken course]
     */

    public ArrayList<HashSet<CourseNode>> create(CourseNode target, HashSet<CourseNode> taken, ArrayList<HashSet<CourseNode>> schedule) {
        HashSet<CourseNode> need = needToTake(target, taken);
        HashSet<CourseNode> eligible = findEligible(taken);
        HashSet<CourseNode> semester = new HashSet<CourseNode>();

        if (need.size() == 0) return schedule;

        need.forEach(n -> {
            if (eligible.contains(n)) semester.add(n); 
        });

        schedule.add(semester);
        taken.addAll(semester);

        return this.create(target, taken, schedule);
    }

    // reverse post-order
    public ArrayList<CourseNode> reverseDFS(CourseNode s) {
        HashMap<CourseNode, Boolean> visited = new HashMap<CourseNode, Boolean>();

        visited.put(s, true);
        Stack<CourseNode> stack = new Stack<CourseNode>();
        stack.add(s);

        ArrayList<CourseNode> visited_nodes = new ArrayList<CourseNode>();

        while (!stack.isEmpty()) {
            s = stack.pop();
            Iterator<CourseNode> i = s.connections.iterator();

            while (i.hasNext()) {
                CourseNode next = i.next();
                if (!visited.containsKey(next)) {
                    visited.put(next, true);

                    // StdOut.println("Neighbor of "+s.id +": " + next.id);
                    stack.add(next);
                    visited_nodes.add(next);
                }
            }

        }

        return visited_nodes;
    }

    public void print() {
        edges.forEach(course -> {
            course.print();
            StdOut.println();
        });
    }

    public boolean validPrereq(String course1, String course2) {

        CourseNode course_1 = get(course1);
        CourseNode course_2 = get(course2);

        if (course_1.equals(course_2)) return false;

        connect(course_1, course_2);

        HashSet<CourseNode> course_1_prereqsFor = preqsFor(course_1);
        HashSet<CourseNode> course_1_prereqs = prereqsAre(course_1);
        ArrayList<CourseNode> dupes = new ArrayList<CourseNode>();

        course_1_prereqs.forEach(prereq -> {
            dupes.add(prereq);
        });

        course_1_prereqsFor.forEach(course -> {
            dupes.add(course);
        });

        Boolean valid = true;
        HashSet<CourseNode> hs = new HashSet<CourseNode>();
        for (CourseNode n : dupes) {
            if (hs.contains(n)) {
                valid = false;
                break;
            } else hs.add(n);
        }

        if (valid) return true;
        else return false;

    }

}
