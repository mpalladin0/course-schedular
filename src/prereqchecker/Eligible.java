package prereqchecker;

import java.util.*;

/**
 * 
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
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class Eligible {
    public static void main(String[] args) {
        Graph g = new Graph(args[0]);
        StdIn.setFile(args[1]);
        StdOut.setFile(args[2]);

        int count = Integer.parseInt(StdIn.readLine());
        String[] taken = new String[count];

        for (int i = 0; i < count; i++) {
            String s = StdIn.readLine();
            taken[i] = s;
        }

        /**
         * Debug only
         */
        // String[] taken = new String[2];
        // taken[0] = "cs211";
        // taken[1] = "mat152";
        /**
         * Expected output:
         * mat250 --- (Requires mat152)
         * cs210 ** Not a DIRECT prereq to cs211 or mat152 ** --- (Requires cs111)
         * cs213  ** Not a DIRECT prereq to cs211 or mat152 ** --- (Requires cs112)
         * cs214 --- (Requires 211)
         * cs205 ** Not a DIRECT prereq to cs211 or mat152 ** --- (Requires cs111)
         * 
         * Why some classes CANT be taken:
         * cs314 --- Requires cs205 (only have taken up to calc2/mat152)
         * cs252 --- Requires cs206 (only have taken up to calc2/mat152)
         * cs415 --- Requires cs314, which requires cs205 (only have taken up to calc2/mat152)
         * 
         */

         HashSet<CourseNode> completed = new HashSet<CourseNode>();
         for (int i = 0; i < count; i++) {
             completed.add(g.get(taken[i]));
         }
        //  completed.add(g.get(taken[0]));
        //  completed.add(g.get(taken[1]));

         HashSet<CourseNode> eligible = g.findEligible(completed);
         eligible.forEach(course -> StdOut.println(course.id));
  
        
    }
}
