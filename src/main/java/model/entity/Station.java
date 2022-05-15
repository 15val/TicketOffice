package model.entity;

public class Station {
    private int id;
    private String nameUA;
    private String nameEN;


    public static class Builder{
        private final Station station;

        public Builder(){
            station = new Station();
        }

        public Builder withId (int id) {
            station.id = id;
            return this;
        }
        public Builder withNameUA (String nameUA){
            station.nameUA = nameUA;
            return this;
        }

        public Builder withNameEN (String nameEN){
            station.nameEN = nameEN;
            return this;
        }

        public Station build(){
            return station;
        }

    }

    public int getId() {
        return id;
    }

    public String getNameUA() {
        return nameUA;
    }

    public String getNameEN() {
        return nameEN;
    }
}
