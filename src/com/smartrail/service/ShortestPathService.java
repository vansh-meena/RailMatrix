package com.smartrail.service;

import com.smartrail.model.Route;
import java.util.*;

public class ShortestPathService {

    public static List<Integer> findShortestPath(
            Map<Integer, List<Route>> graph,
            int sourceStationId,
            int destinationStationId) {

        Map<Integer, Integer> distance = new HashMap<>();
        Map<Integer, Integer> parent = new HashMap<>();

        PriorityQueue<int[]> pq =
                new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        pq.add(new int[]{sourceStationId, 0});
        distance.put(sourceStationId, 0);

        while (!pq.isEmpty()) {

            int[] current = pq.poll();
            int currentNode = current[0];
            int currentDist = current[1];

            if (!graph.containsKey(currentNode)) continue;

            for (Route route : graph.get(currentNode)) {

                int neighbor = route.getDestinationStationId();
                int newDist = currentDist + route.getDistanceKm();

                if (!distance.containsKey(neighbor) || newDist < distance.get(neighbor)) {

                    distance.put(neighbor, newDist);
                    parent.put(neighbor, currentNode);

                    pq.add(new int[]{neighbor, newDist});
                }
            }
        }

        if (!distance.containsKey(destinationStationId)) {
            return null;
        }

        List<Integer> path = new ArrayList<>();
        int current = destinationStationId;

        while (current != sourceStationId) {
            path.add(current);
            current = parent.get(current);
        }
        path.add(sourceStationId);

        Collections.reverse(path);

        return path;
    }
}
