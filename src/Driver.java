import com.sun.tools.javac.Main;

import javax.swing.*;

public class Driver {
    public static void main(String args[]){
        // Window details
        JFrame frame = new JFrame("Mini Twt");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);

        MainPanel.TreeConstruct(frame);

        // Display the frame
        frame.setVisible(true);
    }
}
