package csi3471.group5;

public class RoomType {
    private boolean isSmoking;
    private Integer numBeds;
    private Hotel.qualityDesc quality;
    private Double price;

    public RoomType(boolean isSmoking, Integer numBeds, Hotel.qualityDesc quality, Double price) {
        this.isSmoking = isSmoking;
        this.numBeds = numBeds;
        this.quality = quality;
        this.price = price;
    }

    public boolean isSmoking() {
        return isSmoking;
    }

    public void setSmoking(boolean smoking) {
        isSmoking = smoking;
    }

    public Integer getNumBeds() {
        return numBeds;
    }

    public void setNumBeds(Integer numBeds) {
        this.numBeds = numBeds;
    }

    public Hotel.qualityDesc getQuality() {
        return quality;
    }

    public void setQuality(Hotel.qualityDesc quality) {
        this.quality = quality;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
