import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserDetails {

    private UserTreeNode userNode;
    private String userName;

    public UserDetails(UserTreeNode userNode) {
        this.userNode = userNode;
        if (userNode != null) {
            this.userName = userNode.toString(); // Initialize userName from userNode
        }
    }
    public UserDetails() {
        // No-parameter constructor
        this.userNode = null; // Initialize userNode to null
        this.userName = "";  // Initialize userName to an empty string
    }

    public void showUserDetails() {
        if (userNode != null) {
            // Create a user details window
            JFrame userDetailsFrame = new JFrame("User Details: " + Integer.toString(userNode.getUserID()));
            userDetailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            userDetailsFrame.setSize(300, 150);

            // Create a panel for user details
            JPanel userDetailsPanel = new JPanel();
            userDetailsFrame.add(userDetailsPanel);

            // Display the user's ID
            JLabel idLabel = new JLabel("User ID: " + userNode.getUserID());
            userDetailsPanel.add(idLabel);

            // Create a button to follow another user
            JButton followButton = new JButton("Follow User");
            userDetailsPanel.add(followButton);

            followButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Implement the logic to follow another user here
                    // You can open a dialog or perform any other action
                    JOptionPane.showMessageDialog(userDetailsFrame, "You clicked the Follow User button.");
                }
            });

            userDetailsFrame.setVisible(true);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create a UserTreeNode with user information
                UserTreeNode userNode = new UserTreeNode("JohnDoe", true);

                UserDetails userDetails = new UserDetails(userNode);
                userDetails.showUserDetails();
            }
        });
    }
}


