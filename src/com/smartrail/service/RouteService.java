package com.smartrail.service;

import com.smartrail.dao.RouteDAO;
import com.smartrail.model.Route;

import java.util.*;

public class RouteService {

    private RouteDAO routeDAO;

    public RouteService(RouteDAO routeDAO) {
        this.routeDAO = routeDAO;
    }

    public List<Integer> getShortestPath(int sourceId, int destId) {

        List<Route> routes = routeDAO.getAllRoutes();

        Map<Integer, List<Route>> graph = GraphBuilder.buildGraph(routes);

        return ShortestPathService.findShortestPath(graph, sourceId, destId);
    }
}