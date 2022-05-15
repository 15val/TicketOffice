package model.entity;

import java.math.BigInteger;

public class Ticket {
    private int ticketId;
    private int routeId;
    private int userId;
    private BigInteger price;


    public static class Builder{
        private final Ticket ticket;

        public Builder(){
            ticket = new Ticket();
        }

        public Ticket.Builder withUserId (int userId){
            ticket.userId = userId;
            return this;
        }

        public Ticket.Builder withTicketId (int ticketId) {
            ticket.ticketId = ticketId;
            return this;
        }
        public Ticket.Builder withRouteId (int routeId){
            ticket.routeId = routeId;
            return this;
        }

        public Ticket.Builder withPrice(BigInteger price){
           ticket.price = price;
            return this;
        }

        public Ticket build(){
            return ticket;
        }

    }

    public int getTicketId() {
        return ticketId;
    }

    public int getRouteId() {
        return routeId;
    }

    public BigInteger getPrice() {
        return price;
    }

    public int getUserId() {
        return userId;
    }
}
