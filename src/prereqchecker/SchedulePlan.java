package prereqchecker;

import java.util.*;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * SchedulePlanInputFile name is passed through the command line as args[1]
 * Read from SchedulePlanInputFile with the format:
 * 1. One line containing a course ID
 * 2. c (int): number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * SchedulePlanOutputFile name is passed through the command line as args[2]
 * Output to SchedulePlanOutputFile with the format:
 * 1. One line containing an int c, the number of semesters required to take the course
 * 2. c lines, each with space separated course ID's
 */
public class SchedulePlan {
    public static void main(String[] args) {

        Graph g = new Graph("adjlist.in");
        StdIn.setFile("scheduleplan.in");
        // StdOut.setFile(args[2]);

        String targetCourse = StdIn.readLine();
        int takenCount = Integer.parseInt(StdIn.readLine());

        HashSet<CourseNode> taken = new HashSet<CourseNode>();

        for (int i = 0; i < takenCount; i++) {
            taken.add(g.get(StdIn.readLine()));
        }

        ArrayList<HashSet<CourseNode>> empty = new ArrayList<HashSet<CourseNode>>();
        ArrayList<HashSet<CourseNode>> schedule = g.create(g.get(targetCourse), taken, empty);
        
        StdOut.println(schedule.size());
        for (int i = 0; i < schedule.size(); i++) {
            for (CourseNode n : schedule.get(i)) {
                StdOut.print(n.id + " ");
            }

            StdOut.println();
        }

    }
}
