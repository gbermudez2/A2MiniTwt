import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UserDetails {

    private UserTreeNode userNode;
    private String userName;
    private JTree userTree;
    private List<String> messages = new ArrayList<>();

    public UserDetails(UserTreeNode userNode) {
        this.userNode = userNode;
        if (userNode != null) {
            this.userName = userNode.toString();
        }
    }

    public UserDetails() {
        this.userNode = null; // Initialize userNode to null
        this.userName = "";  // Initialize userName to an empty string
    }

    public void showUserDetails() {
        if (userNode != null) {
            // Create a user details window
            JFrame userDetailsFrame = new JFrame("User Details: " + Integer.toString(userNode.getUserID()));
            userDetailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            userDetailsFrame.setSize(600, 400);

            // Create a panel for user details
            JPanel userDetailsPanel = new JPanel(new BorderLayout());

            // Create a panel for user information (ID and follow button)
            JPanel userInfoPanel = new JPanel();
            JLabel idLabel = new JLabel("User ID: " + userNode.getUserID());
            JButton followButton = new JButton("Follow User");
            userInfoPanel.add(idLabel);
            userInfoPanel.add(followButton);

            // Create a tree to display other users
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("Other Users");
            DefaultTreeModel treeModel = new DefaultTreeModel(root);
            userTree = new JTree(treeModel);

            // Create a panel for posts
            JPanel postsPanel = new JPanel(new BorderLayout());
            JTextArea postTextArea = new JTextArea(10, 30);
            JButton postButton = new JButton("Post");
            postsPanel.add(new JScrollPane(postTextArea), BorderLayout.CENTER);
            postsPanel.add(postButton, BorderLayout.SOUTH);

            // Create a panel for messages
            JPanel messagesPanel = new JPanel(new BorderLayout());
            JTextArea messagesTextArea = new JTextArea(10, 30);
            messagesPanel.add(new JScrollPane(messagesTextArea), BorderLayout.CENTER);

            // Create a panel for positive word count
            JPanel positiveWordPanel = new JPanel();
            JButton countPositiveButton = new JButton("Count Positive Words");
            positiveWordPanel.add(countPositiveButton);

            // Add a listener to count positive words in the messagesTextArea
            countPositiveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String text = messagesTextArea.getText();
                    int positiveWordCount = countPositiveWords(text);
                    JOptionPane.showMessageDialog(userDetailsFrame,
                            "Number of positive words in messages: " + positiveWordCount);
                }
            });

            // Add a listener to the Post button to add messages
            postButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String message = postTextArea.getText();
                    if (!message.isEmpty()) {
                        messages.add(message);
                        messagesTextArea.append(message + "\n");
                        postTextArea.setText("");
                    }
                }
            });

            // Add components to the user details panel
            userDetailsPanel.add(userInfoPanel, BorderLayout.NORTH);
            userDetailsPanel.add(new JScrollPane(userTree), BorderLayout.WEST);
            userDetailsPanel.add(postsPanel, BorderLayout.CENTER);
            userDetailsPanel.add(messagesPanel, BorderLayout.EAST);
            userDetailsPanel.add(positiveWordPanel, BorderLayout.SOUTH);

            userDetailsFrame.add(userDetailsPanel);
            userDetailsFrame.setVisible(true);
        }
    }

    // Helper method to count positive words
    private int countPositiveWords(String text) {
        // You can customize the list of positive words as needed
        String[] positiveWords = {"good", "great", "excellent", "awesome", "fantastic"};
        int count = 0;
        for (String word : positiveWords) {
            count += countOccurrences(text, word);
        }
        return count;
    }

    // Helper method to count occurrences of a word in a text
    private int countOccurrences(String text, String word) {
        int count = 0;
        int index = text.indexOf(word);
        while (index != -1) {
            count++;
            index = text.indexOf(word, index + 1);
        }
        return count;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UserTreeNode userNode = new UserTreeNode("Gabriel", true);
                UserDetails userDetails = new UserDetails(userNode);
                userDetails.showUserDetails();
            }
        });
    }
}
