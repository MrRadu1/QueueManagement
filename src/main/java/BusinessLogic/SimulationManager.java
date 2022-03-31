package BusinessLogic;

import GUI.SimulationFrame;
import Model.Server;
import Model.Task;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static java.lang.Thread.sleep;

public class SimulationManager implements Runnable {
    private int timeLimit;
    private int minArrTime;
    private int maxArrTime;
    private int minProcTime;
    private int maxProcTime;
    private int nrOfServices; // Queues
    private int nrOfTasks;   // Clients
    private double avgServiceTime=0.0;
    private double avgWaitingTime=0.0;
    private int peakWaiting;
    private int peak;
    private FileWriter myWriter=null;
    private SelectionPolicy selectionPolicy;
    private Scheduler scheduler;
    private List<Task> generatedTasks;
    private final SimulationFrame GUI;

    public SimulationManager() {
        GUI = new SimulationFrame();
        getInputs();
    }

    private void getInputs() {
        GUI.addListener(e->
        {
            try {
                this.timeLimit = Integer.parseInt(GUI.getSimulationTime());
                this.minProcTime = Integer.parseInt(GUI.getMinService());
                this.maxProcTime = Integer.parseInt(GUI.getMaxService());
                this.minArrTime = Integer.parseInt(GUI.getMinArrival());
                this.maxArrTime = Integer.parseInt(GUI.getMaxArrival());
                this.nrOfServices = Integer.parseInt(GUI.getQueues());
                this.nrOfTasks = Integer.parseInt(GUI.getClients());
                this.selectionPolicy= GUI.getPolicyType();
                this.scheduler = new Scheduler(nrOfServices,selectionPolicy,timeLimit);
                this.generatedTasks = new ArrayList<>();
                generateNRandomTasks();

            } catch (NumberFormatException x) {
                GUI.showError("Date introduse invalid");
            }
            Thread t = new Thread(this);
            t.setPriority(10);
            t.start();
        });
    }

    private void generateNRandomTasks() {
        for (int i=0;i < nrOfTasks; i ++) {
            int arrT =(int)((maxArrTime-minArrTime+1)*Math.random()+minArrTime);
            int serT =(int)((maxProcTime-minProcTime+1)*Math.random()+minProcTime);
            Task t = new Task(i,arrT,serT);
            this.avgServiceTime+=serT;
            generatedTasks.add(t);
        }
        this.avgServiceTime/=nrOfTasks;
        Collections.sort(generatedTasks);
    }

    @Override
    public void run() {
        int currentTime = 0;
        int countT=0;
        openF();
        while (currentTime<this.timeLimit) {
            writeToF("Time: " +currentTime +"\n");
            ArrayList <Task> tasks = new ArrayList<>();
            for (Task t : this.generatedTasks)
                if (t.getArrivalTime() == currentTime) {
                    this.scheduler.dispatchTask(t);
                    tasks.add(t);
                }
            for(Task t : tasks)
                this.generatedTasks.remove(t);
            countT=avgCalc(countT);
            writeToF("Tasks waiting: \n");
            for (Task t : generatedTasks)
                writeToF("(" + t.getID() + "," + t.getArrivalTime() + "," + t.getServiceTime().get() + "),");
            if (!generatedTasks.isEmpty())
                writeToF("\n");
            int peakTemp=scheduler.afisare(GUI.getTextArea(),this.myWriter);
            if (peakTemp>=this.peakWaiting) {
                this.peak =currentTime;
                this.peakWaiting = peakTemp;
            }
            currentTime++;
            try {
                sleep(1050);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            writeToF("------------------\n");
        }
        this.avgWaitingTime/=countT;
        writeToF("Peak: " + this.peak +"\nAvgWaitingTime: " +Math.round(avgWaitingTime * 100.0) / 100.0 +"\nAvgServiceTime: "+ Math.round(avgServiceTime * 100.0) / 100.0 +"\nDone!\n");
        closeF();
    }

    private void writeToF(String toWrite) {
        GUI.getTextArea().append(toWrite);
        try {
            this.myWriter.write(toWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int avgCalc(int countT) {
        int countS=0;
        double Step=0.0;
        boolean ok=false;
        for (Server s : scheduler.getServers())
            if (s.getWaitingPeriod().get()!=0)  {
                countS++;
                ok=true;
                Step+=s.getWaitingPeriod().get();}
        if (ok)
            countT++;
        if (countS!=0)
            Step/=countS;
        this.avgWaitingTime+=Step;
        return countT;
    }

    private void openF() {
        File myObj;
        this.myWriter = null;
        try {
            myObj = new File("simulation.txt");
            this.myWriter = new FileWriter(myObj);
        } catch (IOException e ) {
            e.printStackTrace();
        }
    }

    private void closeF() {
        try {
            this.myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SimulationManager();
    }
}
