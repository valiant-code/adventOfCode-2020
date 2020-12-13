package adventofcode.vo;

public class Bus {
    private int id;
    private int timeToWait;

    public Bus() {
    }

    public Bus(int id, int timeToWait) {
        this.id = id;
        this.timeToWait = timeToWait;
    }

    public int answer() {
        return id * timeToWait;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimeToWait() {
        return timeToWait;
    }

    public void setTimeToWait(int timeToWait) {
        this.timeToWait = timeToWait;
    }


}
