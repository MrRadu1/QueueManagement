package BusinessLogic;

import Model.Server;
import Model.Task;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Scheduler {
    private final List<Server> servers;
    private final Strategy strategy;

    public Scheduler (int maxNoServers,SelectionPolicy selectionPolicy,int timeLimit) {
        servers = new ArrayList<>();
        if (selectionPolicy == SelectionPolicy.SHORTEST_QUEUE)
            strategy = new ShortestQueueStrategy();
        else
            strategy = new TimeStrategy();
        for (int i=0; i<maxNoServers;i++)
        {
            Server s = new Server();
            servers.add(s);
            Thread t = new Thread(s);
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(maxNoServers);
            Future<?> future = executor.submit(t);
            executor.schedule(() -> {
                future.cancel(true);
            },timeLimit , TimeUnit.SECONDS);
            executor.shutdown();
        }
    }

    public void dispatchTask(Task task){
        strategy.addTask(servers,task);
    }

    public int afisare(JTextArea txt, FileWriter fileWriter) {
        int i=0;
        int count=0;
        for (Server s: servers) {
            count+=s.getWaitingPeriod().get();
            String result="";
            txt.append("Queue " + i + ":");
            try {
                fileWriter.write("Queue " + i + ":");
            }catch (IOException e) {
                e.printStackTrace();
            }
            if (s.getTasks().isEmpty() || (s.getTasks().peek().getServiceTime().get()==0 && s.getTasks().size()==1))
                result="empty";
            else
                for(Task t : s.getTasks())
                    if (t.getServiceTime().get()>0) {
                        result = result + "(" + t.getID() + "," + t.getArrivalTime() + "," + t.getServiceTime() + "),";
                        if (t==s.getTasks().peek()) {
                            t.getServiceTime().addAndGet(-1);
                        s.getWaitingPeriod().addAndGet(-1);}
                    }
            txt.append(result + "\n");
                try {
                    fileWriter.write(result + "\n");
                }catch (IOException e) {
                    e.printStackTrace();
                }
            i++;
        }
        return count;
    }

    public List<Server> getServers () {
        return this.servers;
    }
}
