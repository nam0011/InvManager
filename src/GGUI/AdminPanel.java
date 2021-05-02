package GGUI;

import com.company.AccessControl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.ParseException;

public class AdminPanel  extends abstractPanel{

    private AccessControl ac;
    private JToolBar toolBar;
    private JButton addUserB;

    public AdminPanel(DefaultFrame inFrame) throws IOException, ParseException {
        super(inFrame);
        ac = AccessControl.getAccessInstance();
        buildToolbar();

    }
    private void buildToolbar(){
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        addUserB = new JButton("Add User");
        addUserB.addActionListener(this);
        toolBar.add(addUserB);
        add(toolBar);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addUserB){
            try {
                new AddUserDialog(this);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }

        }

    }
}
