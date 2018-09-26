package route;

public class Route {
    private int id;
    private double distance;
    private double topSpeed;
    private int duration;

    public Route(int id, double distance, double topSpeed, int duration) {
        this.id = id;
        this.distance = distance;
        this.topSpeed = topSpeed;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getTopSpeed() {
        return topSpeed;
    }

    public void setTopSpeed(double topSpeed) {
        this.topSpeed = topSpeed;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
