package Alg;

import java.util.*;

/**
 * A* Algorithm class
 * @author Lorenzo Radice
 * @version 1.0.0
 */
public class alg_Astar {
    public static List<Station> reconstructPath(Map<Station, Station> cameFrom, Station current) {
        List<Station> totalPath = new ArrayList<>();
        totalPath.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            totalPath.add(0, current);
        }
        return totalPath;
    }
    /**
     * A* finds a path from start to goal.
     * h is the heuristic function.
     * h(n) estimates the cost to reach goal from node n.
     * @param start
     * @param goal
     * @param h
     * @return list of stations
     */
    public static List<Station> aStarSearch(Station start, Station goal, HeuristicFunction h) {
        /*
        * The set of discovered nodes that may need to be (re-)expanded.
        * Initially, only the start node is known.
        * This is usually implemented as a min-heap or priority queue rather than a hash-set.
        */
        Set<Station> openSet = new HashSet<>();
        openSet.add(start);

        /*
         * For node n, cameFrom[n] is the node immediately preceding it on the cheapest path from the start
         * to n currently known
         */
        Map<Station, Station> cameFrom = new HashMap<>();
        Map<Station, Integer> gScore = new HashMap<>();
        gScore.put(start, 0);

        /*
         * For node n, fScore[n] := gScore[n] + h(n). fScore[n] represents our current best guess as to
         * how cheap a path could be from start to finish if it goes through n.
         */
        Map<Station, Integer> fScore = new HashMap<>();
        fScore.put(start, alg_Astar.HeuristicFunction.estimateCost(start, goal));

        while (!openSet.isEmpty()) {
            /*
             * This operation can occur in O(Log(N)) time if openSet is a min-heap or a priority queue
             */
            Station current = openSet.stream()
                    .min(Comparator.comparingDouble(fScore::get))
                    .orElseThrow(null);

            if (current.equals(goal)) {
                return reconstructPath(cameFrom, current);
            }

            openSet.remove(current);

            for (Station neighbor : current.getNearStations()) {
                /*
                 * d(current,neighbor) is the weight of the edge from current to neighbor
                 * tentative_gScore is the distance from start to the neighbor through current
                 */
                int tentativeGScore = gScore.getOrDefault(current, Integer.MAX_VALUE)
                        + current.TimeForHeuristic(neighbor);

                if (tentativeGScore < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    /*
                     * This path to neighbor is better than any previous one. Record it!
                     */
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);
                    fScore.put(neighbor, tentativeGScore + alg_Astar.HeuristicFunction.estimateCost(neighbor, goal));
                    openSet.add(neighbor);
                }
            }
        }
        /*
         * Open set is empty but goal was never reached
         */
        return Collections.emptyList(); // No path found
    }
    public interface HeuristicFunction {
        /**
         * Heuristc function
         * @param node current node
         * @param goal goal node
         * @return h(n)
         */
        static int estimateCost(Station node, Station goal) {
            return node.heuristic(goal);
        }
    }
    // TODO Remove Test main
    /*
    public static void main(String[] args) {
        Station a = new Station("Valmy");
        Station b = new Station("Flachet");
        List<Station> ls = aStarSearch(a, b, null);
        for (Station l : ls) {
            System.out.println(l.toString());
        }
    }
    */
}
