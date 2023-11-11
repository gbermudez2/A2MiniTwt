import javax.swing.*;
import java.awt.Dimension;

public class UserDetails extends JFrame {
    private JTextArea userDetailsTextArea;

    public UserDetails() {
        setTitle("User Panel");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        userDetailsTextArea = new JTextArea();
        add(userDetailsTextArea);
    }

    public void showUserDetails(String userName) {
        userDetailsTextArea.setText("Selected User: " + userName);
        setVisible(true);
    }
}
