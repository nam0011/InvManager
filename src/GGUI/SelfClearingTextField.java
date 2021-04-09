package GGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SelfClearingTextField extends JTextField implements MouseListener, KeyListener {
    private boolean beenClicked;
    private String defaultText;
    public SelfClearingTextField(String title, int width){
        setText(title);
        addKeyListener(this);

        setMinimumSize(new Dimension(width,30));

        defaultText = title;
        setSize(width, 30);

        addMouseListener(this);
        beenClicked = false;
        setFont(new Font("New Times Roman" , Font.ITALIC, 12));

    }
    public void reset(){
        setText(defaultText);
        setFont(new Font("New Times Roman" , Font.ITALIC, 12));
        beenClicked = false;

    }

    public boolean hasBeenClickedAndFilled() {
        boolean filled = true;
        String x = getText();
        if(x.equals("")){
            filled = false;
        }
        return (beenClicked && filled);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String text = getText();
        if(!beenClicked && !text.equals("")) {
            beenClicked = true;
            setFont(new Font("New Times Roman", Font.PLAIN, 12));
            setText("");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(getText().equals(defaultText)){
            setText("");
            beenClicked = true;
            setFont(new Font("New Times Roman", Font.PLAIN, 12));
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
