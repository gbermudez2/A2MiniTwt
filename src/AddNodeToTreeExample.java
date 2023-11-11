import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddNodeToTreeExample {
    public static void main(String[] args) {
        // Create a JFrame
        JFrame frame = new JFrame("Show Total Users with Extra Buttons");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Create a root node for the tree
        UserTreeNode root = new UserTreeNode("Root", false); // Root is a group

        // Create some child nodes (users)
        UserTreeNode user1 = new UserTreeNode("User 1", true);
        UserTreeNode user2 = new UserTreeNode("User 2", true);

        // Add child nodes to the root node
        root.add(user1);
        root.add(user2);

        // Create a tree model with the root node
        DefaultTreeModel treeModel = new DefaultTreeModel(root);

        // Create a JTree with the tree model
        JTree tree = new JTree(treeModel);

        // Set tree selection mode to single selection
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        // Create a panel to hold the JTree
        JPanel treePanel = new JPanel(new BorderLayout());
        treePanel.add(new JScrollPane(tree), BorderLayout.CENTER);

        // Set the preferred size of the tree panel
        treePanel.setPreferredSize(new Dimension(300, 300));

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS)); // Use BoxLayout with vertical layout

        // Create a panel for the top two buttons
        JPanel topButtonPanel = new JPanel();
        topButtonPanel.setLayout(new BoxLayout(topButtonPanel, BoxLayout.LINE_AXIS)); // Horizontal layout

        // Create two buttons for the top row
        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");

        // Customize the buttons to have a solid color background
        button1.setBackground(Color.BLUE);
        button2.setBackground(Color.GREEN);

        topButtonPanel.add(button1);
        topButtonPanel.add(button2);

        // Create a panel for the two buttons in the bottom row
        JPanel bottomButtonPanel = new JPanel();
        bottomButtonPanel.setLayout(new BoxLayout(bottomButtonPanel, BoxLayout.LINE_AXIS)); // Horizontal layout

        // Create two more buttons for the bottom row
        JButton button3 = new JButton("Button 3");
        JButton button4 = new JButton("Button 4");

        // Customize the buttons to have a solid color background
        button3.setBackground(Color.RED);
        button4.setBackground(Color.ORANGE);

        bottomButtonPanel.add(button3);
        bottomButtonPanel.add(button4);

        // Create a split pane to separate the tree and button panels
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, buttonPanel);

        // Add the topButtonPanel with the first two buttons to the buttonPanel
        buttonPanel.add(topButtonPanel);

        // Add the bottomButtonPanel with the second pair of buttons to the buttonPanel
        buttonPanel.add(bottomButtonPanel);

        // Create a label to display the total number of users
        JLabel totalUsersLabel = new JLabel("Total Users: 0");

        // Create a button to show the total number of users
        JButton showTotalUsersButton = new JButton("Show Total Users");
        showTotalUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int totalUsers = countUsers(root);
                totalUsersLabel.setText("Total Users: " + totalUsers);
            }
        });

        // Add the showTotalUsersButton and totalUsersLabel to a panel
        JPanel userCountPanel = new JPanel();
        userCountPanel.add(showTotalUsersButton);
        userCountPanel.add(totalUsersLabel);

        // Add the userCountPanel to the frame
        frame.getContentPane().add(userCountPanel, BorderLayout.NORTH);

        // Add the split pane to the frame
        frame.getContentPane().add(splitPane, BorderLayout.CENTER);

        // Create two extra buttons below the existing buttons
        JButton button5 = new JButton("Button 5");
        JButton button6 = new JButton("Button 6");

        bottomButtonPanel.add(button5);
        bottomButtonPanel.add(button6);

        // Display the frame
        frame.setVisible(true);
    }

    // Helper method to count the number of users in the tree
    private static int countUsers(DefaultMutableTreeNode node) {
        int count = 0;

        if (node.getUserObject() instanceof UserTreeNode && ((UserTreeNode) node.getUserObject()).isUser()) {
            count++;
        }

        // Recursively count users in child nodes
        for (int i = 0; i < node.getChildCount(); i++) {
            count += countUsers((DefaultMutableTreeNode) node.getChildAt(i));
        }

        return count;
    }
}
