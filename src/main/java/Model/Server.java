package Model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import static java.lang.Thread.sleep;

public class Server implements Runnable{
    private final BlockingQueue <Task> tasks;
    private final AtomicInteger waitingPeriod;

    public Server(){
        this.tasks= new LinkedBlockingQueue<>();
        this.waitingPeriod = new AtomicInteger(0);


    }

    public void addTask(Task task) {
        this.tasks.add(task);
        this.waitingPeriod.addAndGet(task.getServiceTime().get());
    }

    public BlockingQueue<Task> getTasks () {
        return tasks;
    }

    @Override
    public void run() {
        while (true)
            if (!tasks.isEmpty()) {
                try {
                    Task task = tasks.peek();
                    int time = task.getServiceTime().get();
                    for (int i = 0; i < time; i++) {
                        try {
                        sleep(1000); }
                        catch (InterruptedException x) {
                            x.printStackTrace();
                        }
                        if (task.getServiceTime().get()==0)
                            tasks.take();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

}
