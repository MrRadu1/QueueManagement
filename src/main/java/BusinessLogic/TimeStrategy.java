package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class TimeStrategy implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task task) {
        int min= Integer.MAX_VALUE;
        int index=0;
        for (Server s : servers) {
            if (s.getWaitingPeriod().get() < min) {
                min = s.getWaitingPeriod().get();
                index = servers.indexOf(s);
            }
        }
        servers.get(index).addTask(task);
    }
}
