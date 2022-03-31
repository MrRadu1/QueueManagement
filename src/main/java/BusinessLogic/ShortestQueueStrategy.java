package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class ShortestQueueStrategy implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task task) {
        int min= Integer.MAX_VALUE;
        int index=0;
        for (Server s : servers)
            if (s.getTasks().size()<min) {
                min = s.getTasks().size();
                index = servers.indexOf(s);
            }
        servers.get(index).addTask(task);
        //System.out.println("Queue " + index + " runs task " + task.getID() + " for " + task.getServiceTime());
    }
}
