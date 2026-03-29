package com.smartrail.service;

import com.smartrail.model.Route;
import java.util.*;

public class GraphBuilder {

    public static Map<Integer, List<Route>> buildGraph(List<Route> routes) {

        Map<Integer, List<Route>> graph = new HashMap<>();

        for (Route route : routes) {

            int departure = route.getDepartureStationId();

            graph.computeIfAbsent(departure, k -> new ArrayList<>())
                    .add(route);
        }
        return graph;
    }
}
