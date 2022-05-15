package model.entity;

public class Train {
    private int trainId;
    private int allSeats;

    public static class Builder{
        private final Train train;
        public Builder(){
            train = new Train();
        }
        public Train.Builder withTrainId (int trainId) {
            train.trainId = trainId;
            return this;
        }
        public Train.Builder withAllSeats (int allSeats){
            train.allSeats = allSeats;
            return this;
        }

        public Train build(){
            return train;
        }
    }


    public int getTrainId() {
        return trainId;
    }

    public int getAllSeats() {
        return allSeats;
    }
}
