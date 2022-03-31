package Model;

import java.util.concurrent.atomic.AtomicInteger;

public class Task   implements Comparable<Task> {
    private final int ID;
    private final int arrivalTime;
    private AtomicInteger serviceTime;

    public Task(int ID, int arrivalTime, int serviceTime) {
        this.arrivalTime = arrivalTime;
        this.ID = ID;
        this.serviceTime= new AtomicInteger(serviceTime);
    }

    public int getID() {
        return ID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public AtomicInteger getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int x) {this.serviceTime=new AtomicInteger(x);}

    @Override
    public int compareTo(Task o) {
        return this.arrivalTime-o.arrivalTime;
    }
}
