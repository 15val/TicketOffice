package model.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;


public class Route {
    private int routeId;
    private Station firstStation;
    private Station lastStation;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private int trainId;
    private String routeTime;
    private int freeSeats;
    private BigInteger price;

    public static class Builder{
        private final Route route;
        public Builder(){
            route = new Route();
        }

        public Builder withRouteId(int routeId){
            route.routeId = routeId;
            return this;
        }

        public Builder withFirstStation(Station firstStation){
            route.firstStation = firstStation;
            return this;
        }

        public Builder withLastStation(Station lastStation){
            route.lastStation = lastStation;
            return this;
        }

        public Builder withDeparture(LocalDateTime departure){
            route.departure = departure;
            return this;
        }

        public Builder withArrival(LocalDateTime arrival){
            route.arrival = arrival;
            return this;
        }

        public Builder withTrainId(int trainId){
            route.trainId = trainId;
            return this;
        }

        public Builder withRouteTime(String routeTime){
            route.routeTime = routeTime;
            return this;
        }

        public Builder withFreeSeats(int freeSeats){
            route.freeSeats = freeSeats;
            return this;
        }

        public Builder withPrice(BigInteger price){
            route.price = price;
            return this;
        }

        public Route build(){
            return route;
        }


    }

    public int getRouteId() {
        return routeId;
    }

    public Station getFirstStation() {
        return firstStation;
    }

    public Station getLastStation() {
        return lastStation;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

    public int getTrainId() {
        return trainId;
    }

    public String getRouteTime() {
        return routeTime;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public BigInteger getPrice() {
        return price;
    }
}
