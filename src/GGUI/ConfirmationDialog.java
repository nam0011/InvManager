package GGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmationDialog extends JDialog implements ActionListener {
    private boolean okBool;
    private String verb, item;
    private JLabel areYouSureLabel;
    private JButton okBtn, cancelBtn;
    //TODO REMOVE this class doesn't do shit.

    public ConfirmationDialog(String P_verbStr, String P_itemStr)
    {

        okBool = false;
        verb = P_verbStr;
        item = P_itemStr;
        areYouSureLabel = new JLabel("Are you sure you want to " +verb+" "+item+"?");
        okBtn = new JButton("Ok");
        cancelBtn = new JButton("Cancel");

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        okBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(areYouSureLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        add(okBtn, gbc);

        setSize(300, 200);
        setVisible(true);

    }
    public boolean GetokBool()
    {
        buildDialog();
        return okBool;
    }

    private void buildDialog(){




    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == okBtn){
            okBool = true;


        }

    }
}
