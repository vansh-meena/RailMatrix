package com.smartrail.dao;

import com.smartrail.model.Route;
import java.sql.*;
import java.util.*;

public class RouteDAO {

    private Connection con;

    public RouteDAO(Connection conn) {
        this.con = conn;
    }

    // Fetch all routes
    public List<Route> getAllRoutes() {

        List<Route> routes = new ArrayList<>();

        try {
            String query = "SELECT * FROM routes WHERE is_active = 1";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Route route = new Route(
                        rs.getInt("route_id"),
                        rs.getInt("departure_station_id"),
                        rs.getInt("destination_station_id"),
                        rs.getInt("distance_km"),
                        rs.getInt("travel_time_minutes")
                );

                routes.add(route);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return routes;
    }
}
