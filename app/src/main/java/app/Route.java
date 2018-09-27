package app;

public class Route {
    private int id;
    private double distance;
    private double topSpeed;
    private double duration;
    private String time_start;
    private String time_end;

    public Route(int id, double distance, double topSpeed, double duration, String time_start, String time_end) {
        this.id = id;
        this.distance = distance;
        this.topSpeed = topSpeed;
        this.duration = duration;
        this.time_start = time_start;
        this.time_end = time_end;
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

    public double getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTimeStart() {
        return time_start;
    }

    public void setTimeStart(String time_start) { this.time_start = time_start; }

    public String getTimeEnd() {
        return time_end;
    }

    public void setTimeEnd(String time_end) {
        this.time_end = time_end;
    }
}
