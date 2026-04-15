package com.azathorpe.panel;

import com.azathorpe.Entities.Bus;
import com.azathorpe.Utils.DataBaseUtils;
import com.azathorpe.Utils.Filter;

import javax.swing.*;

public class PopupFilter extends JPanel {
    private JTextField busIDField = new JTextField(20){{
        setText("-1");
    }};
    private JTextField busNameField = new JTextField(20);
    private JTextField startTimeField = new JTextField(20);
    private JTextField ticketField = new JTextField(20);
    private JTextField destinationField = new JTextField(20);
    private JTextField nextStationField = new JTextField(20);
    private JComboBox<String> busStatueComboBox = new JComboBox<>(new String[]{"BUS_NOT_ARRIVED", "BUS_ARRIVED", "BUS_DEPARTED"});
    private JButton addButton = new JButton("添加");

    public PopupFilter() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new JLabel("车牌:"));
        this.add(busNameField);
        this.add(new JLabel("发车时间(xx:xx):"));
        this.add(startTimeField);
        this.add(new JLabel("余票:"));
        this.add(ticketField);
        this.add(new JLabel("终点站:"));
        this.add(destinationField);
        this.add(new JLabel("下一站:"));
        this.add(nextStationField);
        this.add(new JLabel("状态:"));
        this.add(busStatueComboBox);
        this.add(addButton);

        addButton.addActionListener(e -> {
            String busName = busNameField.getText();
            String startTime = startTimeField.getText();
            String ticket = ticketField.getText();
            String destination = destinationField.getText();
//            String nextStation = nextStationField.getText();
            String busStatue = (String) busStatueComboBox.getSelectedItem();
            Filter.setVal(ticket,busName,startTime,destination,busStatue);
        });
    }
}
