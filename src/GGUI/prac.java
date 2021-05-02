package GGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class prac extends JFrame  {
    private JPanel panel;
    private JButton click;
    private JTextField message;
    public prac(){
        click = new JButton("Toggle");

        click.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggle();
            }
        });
        this.setVisible(true);
        message = new JTextField();
        message.setColumns(6);
        message.setEditable(false);
        message.setText("hello");
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        this.setSize(new Dimension(400,400));
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx= 0;
        gc.gridy = 0;
        panel.add(message, gc);
        gc.gridy =1;
        panel.add(click, gc);
        this.add(panel);



    }

    private void toggle() {
        String curr = message.getText();
        if(curr.equals("hello")){
            message.setText("goodbye");
        }
        else{
            message.setText("hello");
        }
    }


}
