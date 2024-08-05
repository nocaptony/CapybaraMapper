package learn.capybara_mapper.models;

public class Location {
    private int locationId;
    private int userId;
    private double latitude;
    private double longitude;

    public Location() {}

    public Location(int locationId, int userId, double longitude, double latitude) {
        this.locationId = locationId;
        this.userId = userId;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}

