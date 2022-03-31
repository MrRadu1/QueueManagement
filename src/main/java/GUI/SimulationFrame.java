package GUI;

import BusinessLogic.SelectionPolicy;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SimulationFrame {

    private final JFrame frame;
    private final JTextArea textArea;
    private final JButton simulationBtn;
    private final JTextField queueField;
    private final JTextField clientsField;
    private final JTextField minArrivalField;
    private final JTextField maxArrivalField;
    private final JTextField minServiceField;
    private final JTextField maxServiceField;
    private final JTextField simulationField;
    private final JComboBox<SelectionPolicy> comboBox;

    public SimulationFrame() {
        frame = new JFrame();
        frame.setBounds(0,0, 1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 290, 1500, 500);
        frame.getContentPane().add(scrollPane);

        textArea = new JTextArea("");

        scrollPane.setViewportView(textArea);

        simulationBtn = new JButton("Start Simulation");
        simulationBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
        simulationBtn.setBounds(705, 240, 125, 25);
        frame.getContentPane().add(simulationBtn);

        JLabel queueLabel = new JLabel("Number of queues :");
        queueLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
        queueLabel.setBounds(485, 25, 110, 20);
        frame.getContentPane().add(queueLabel);

        queueField = new JTextField();
        queueField.setBounds(605, 25, 95, 20);
        frame.getContentPane().add(queueField);
        queueField.setColumns(10);

        JLabel clientsLabel = new JLabel("Number of clients :");
        clientsLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
        clientsLabel.setBounds(810, 25, 110, 20);
        frame.getContentPane().add(clientsLabel);

        clientsField = new JTextField();
        clientsField.setColumns(10);
        clientsField.setBounds(915, 25, 95, 20);
        frame.getContentPane().add(clientsField);

        JLabel arrivalLabel = new JLabel("Arrival Time :");
        arrivalLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
        arrivalLabel.setBounds(485, 65, 110, 20);
        frame.getContentPane().add(arrivalLabel);

        JLabel serviceLabel = new JLabel("Service Time :");
        serviceLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
        serviceLabel.setBounds(810, 65, 110, 20);
        frame.getContentPane().add(serviceLabel);

        minArrivalField = new JTextField();
        minArrivalField.setColumns(10);
        minArrivalField.setBounds(605, 65, 95, 20);
        frame.getContentPane().add(minArrivalField);

        maxArrivalField = new JTextField();
        maxArrivalField.setColumns(10);
        maxArrivalField.setBounds(605, 100, 95, 20);
        frame.getContentPane().add(maxArrivalField);

        minServiceField = new JTextField();
        minServiceField.setColumns(10);
        minServiceField.setBounds(915, 65, 95, 20);
        frame.getContentPane().add(minServiceField);

        maxServiceField = new JTextField();
        maxServiceField.setColumns(10);
        maxServiceField.setBounds(915, 100, 95, 20);
        frame.getContentPane().add(maxServiceField);

        JLabel minServiceLabel = new JLabel("min ");
        minServiceLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
        minServiceLabel.setBounds(1025, 65, 30, 20);
        frame.getContentPane().add(minServiceLabel);

        JLabel minArrivalLabel = new JLabel("min ");
        minArrivalLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
        minArrivalLabel.setBounds(705, 65, 30, 20);
        frame.getContentPane().add(minArrivalLabel);

        JLabel maxArrivalLabel = new JLabel("max");
        maxArrivalLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
        maxArrivalLabel.setBounds(705, 100, 30, 20);
        frame.getContentPane().add(maxArrivalLabel);

        JLabel maxServiceLabel = new JLabel("max");
        maxServiceLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
        maxServiceLabel.setBounds(1025, 100, 30, 20);
        frame.getContentPane().add(maxServiceLabel);

        JLabel simulationLabel = new JLabel("Simulation Time :");
        simulationLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
        simulationLabel.setBounds(725, 145, 110, 20);
        frame.getContentPane().add(simulationLabel);

        simulationField = new JTextField();
        simulationField.setColumns(10);
        simulationField.setBounds(725, 165, 95, 20);
        frame.getContentPane().add(simulationField);

        JLabel lblSelectionPolicy = new JLabel("Selection Policy :");
        lblSelectionPolicy.setFont(new Font("Times New Roman", Font.BOLD, 13));
        lblSelectionPolicy.setBounds(725, 185, 110, 20);
        frame.getContentPane().add(lblSelectionPolicy);

        SelectionPolicy[] policy=new SelectionPolicy[] {SelectionPolicy.SHORTEST_TIME,SelectionPolicy.SHORTEST_QUEUE};
        comboBox = new JComboBox<>(policy);
        comboBox.setBounds(700, 210, 135, 20);
        frame.getContentPane().add(comboBox);

        frame.setVisible(true);
    }

    public  JTextArea getTextArea() {
        return this.textArea;
    }

    public void addListener(ActionListener x) {
        simulationBtn.addActionListener(x);
    }

    public String getQueues() {
        return queueField.getText();
    }


    public String getClients() {
        return clientsField.getText();
    }

    public String getMinArrival() {
        return minArrivalField.getText();
    }

    public String getMaxArrival() {
        return maxArrivalField.getText();
    }

    public String getMinService() {
        return minServiceField.getText();
    }

    public String getMaxService() {
        return maxServiceField.getText();
    }

    public String getSimulationTime() {
        return simulationField.getText();
    }

    public SelectionPolicy getPolicyType() {
        return (SelectionPolicy) comboBox.getSelectedItem();
    }

    public void showError(String errMessage) {
        JOptionPane.showMessageDialog(this.frame, errMessage);
    }

}

