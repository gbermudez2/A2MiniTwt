import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class MainPanel {

    // Static declarations for static reference
    private static DefaultMutableTreeNode root;
    private static int userCount;
    private static int groupCount;
    private static JButton button5;
    private static JButton button6;
    private static UserDetails userDetails;
    private static Map<String, UserDetails> userWindows;

    private static class CounterManager {

        public int getUserCount() {
            return userCount;
        }

        public int getGroupCount() {
            return groupCount;
        }

        public void updateCounts(DefaultMutableTreeNode root) {
            userCount = countUsers(root);
            groupCount = countGroups(root);
        }

        private int countUsers(DefaultMutableTreeNode node) {
            int count = 0;

            for (int i = 0; i < node.getChildCount(); i++) {
                DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
                if (child.getUserObject() instanceof UserTreeNode && ((UserTreeNode) child.getUserObject()).isUser()) {
                    count++;
                }
                count += countUsers(child);
            }

            return count;
        }

        private int countGroups(DefaultMutableTreeNode node) {
            int count = 0;

            for (int i = 0; i < node.getChildCount(); i++) {
                DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
                if (child.getUserObject() instanceof UserTreeNode && !((UserTreeNode) child.getUserObject()).isUser()) {
                    count++;
                }
                count += countGroups(child);
            }

            return count;
        }
    }

    static void TreeConstruct(JFrame frame) {
        // Create a root node for the tree, true means it is a user
        root = new UserTreeNode("Root", false);
        userWindows = new HashMap<>();

        // Create some nodes
        UserTreeNode node1 = new UserTreeNode("Gabe", true);
        UserTreeNode node2 = new UserTreeNode("John", true);
        UserTreeNode node3 = new UserTreeNode("Jack", true);
        UserTreeNode group1 = new UserTreeNode("CS Class", false);

        // Add nodes to the tree
        root.add(group1);
        root.add(node2);
        root.add(node3);

        group1.add(node1);

        // Create a tree model with the root node
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        JTree tree = new JTree(treeModel);

        // Set tree selection mode to single selection
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        // Create a panel to hold the Tree
        JPanel treePanel = new JPanel(new BorderLayout());
        JScrollPane treeScrollPane = new JScrollPane(tree);
        treePanel.add(treeScrollPane, BorderLayout.CENTER);
        treePanel.setPreferredSize(new Dimension(300, 300));

        // Create a panel for buttons. We pass important parameters to the Button Construct
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(300,20));
        ButtonConstruct(buttonPanel, treePanel, frame, root, treeModel, tree);
    }

    private static void ButtonConstruct(JPanel buttonPanel,
                                        JPanel treePanel,
                                        JFrame frame,
                                        DefaultMutableTreeNode root,
                                        DefaultTreeModel treeModel,
                                        JTree tree){
        JButton button1 = new JButton("User ID: " );
        JButton button2 = new JButton("Add User");

        // Customize the buttons to have a flat appearance
        button1.setBorderPainted(false);
        button2.setBorderPainted(false);
        button1.setBackground(Color.LIGHT_GRAY);
        button2.setBackground(Color.LIGHT_GRAY);
        button1.setForeground(Color.BLACK); // Text color
        button2.setForeground(Color.BLACK); // Text color

        button1.setPreferredSize(new Dimension(300,30));
        button2.setPreferredSize(new Dimension(100,30));

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserTreeNode newNode = new UserTreeNode("user", true);
                UserTreeNode selectedNode = (UserTreeNode) tree.getLastSelectedPathComponent();

                if (selectedNode == null) {
                    selectedNode = (UserTreeNode) root;
                }

                treeModel.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
            }
        });

        buttonPanel.add(button1);
        buttonPanel.add(button2);

        // Create a panel for the two buttons in the bottom row
        JPanel bottomButtonPanel = new JPanel();

        // Create two more buttons for the bottom row
        JButton button3 = new JButton("Group ID: ");
        JButton button4 = new JButton("Add Group");

        // Customize the buttons to have a flat appearance
        button3.setBorderPainted(false);
        button4.setBorderPainted(false);
        button3.setBackground(Color.LIGHT_GRAY);
        button4.setBackground(Color.LIGHT_GRAY);
        button3.setForeground(Color.BLACK); // Text color
        button4.setForeground(Color.BLACK); // Text color

        button3.setPreferredSize(new Dimension(300,30));
        button4.setPreferredSize(new Dimension(100,30));
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserTreeNode newNode = new UserTreeNode("group", false);
                UserTreeNode selectedNode = (UserTreeNode) tree.getLastSelectedPathComponent();

                if (selectedNode == null) {
                    selectedNode = (UserTreeNode) root;
                }

                treeModel.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
            }
        });

        // Add the additional buttons to the bottomButtonPanel
        bottomButtonPanel.add(button3);
        bottomButtonPanel.add(button4);

        // Create a split pane to separate the tree and button panels
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, buttonPanel);

        // Set the initial size of the split pane to ensure the tree panel has enough width
        splitPane.setDividerLocation(300); // Adjust the size as needed

        // Create a vertical box layout panel to hold the top and bottom button panels
        JPanel buttonBoxLayout = new JPanel();
        buttonBoxLayout.setLayout(new BoxLayout(buttonBoxLayout, BoxLayout.PAGE_AXIS));

        // Add the top and bottom button panels to the vertical layout
        buttonBoxLayout.add(buttonPanel);
        buttonBoxLayout.add(bottomButtonPanel);

        // Add the vertical buttonBoxLayout to the right side of the split pane
        splitPane.setRightComponent(buttonBoxLayout);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                UserTreeNode selectedNode = (UserTreeNode) tree.getLastSelectedPathComponent();

                if (selectedNode != null) {
                    button1.setText("User ID: " + selectedNode.getUserID());
                    button3.setText("Group ID: " + selectedNode.getGroupID());
                } else {
                    button1.setText("User ID: ");
                    button3.setText("Group ID: ");
                }
            }
        });

        // Create a panel for the totals buttons in the bottom row
        JPanel totalsButtonPanel = new JPanel();

        // Create two buttons for the totals row
        button5 = new JButton("User Total: ");
        button6 = new JButton("Group Total: ");

        // Customize the buttons to have a flat appearance
        button5.setBorderPainted(false);
        button6.setBorderPainted(false);
        button5.setBackground(Color.LIGHT_GRAY);
        button6.setBackground(Color.LIGHT_GRAY);
        button5.setForeground(Color.BLACK); // Text color
        button6.setForeground(Color.BLACK); // Text color

        button5.setPreferredSize(new Dimension(150, 30));
        button6.setPreferredSize(new Dimension(150, 30));

        // Add the totals buttons to the totalsButtonPanel
        totalsButtonPanel.add(button5);
        totalsButtonPanel.add(button6);

        // Add the totalsButtonPanel to the bottomButtonPanel
        bottomButtonPanel.add(totalsButtonPanel);

        // Create the button panel and add the "User Details" button
        JPanel userDetailsPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(300, 20));
        JButton userDetailsButton = new JButton("User Details");
        buttonPanel.add(userDetailsButton);

        // Add the TreeSelectionListener to handle user details button action
        userDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

                if (selectedNode != null && selectedNode.getUserObject() instanceof String) {
                    String userName = (String) selectedNode.getUserObject();

                    UserDetails userWindow = getUserWindow(userName);
                    userWindow.showUserDetails();
                }
            }
        });

        // Add the panel to the frame
        frame.getContentPane().add(splitPane, BorderLayout.CENTER);

        TreeModelListener treeModelListener = new TreeModelListener() {
            @Override
            public void treeNodesChanged(TreeModelEvent e) {
                updateCounts();
            }

            @Override
            public void treeNodesInserted(TreeModelEvent e) {
                updateCounts();
            }

            @Override
            public void treeNodesRemoved(TreeModelEvent e) {
                updateCounts();
            }

            @Override
            public void treeStructureChanged(TreeModelEvent e) {
                updateCounts();
            }
        };

        // Add the treeModelListener to the tree model
        treeModel.addTreeModelListener(treeModelListener);
    }

    private static UserDetails getUserWindow(String userName) {
        if (userWindows.containsKey(userName)) {
            return userWindows.get(userName);
        } else {
            UserDetails userWindow = new UserDetails();
            userWindows.put(userName, userWindow);
            return userWindow;
        }
    }

    // Update the user and group counts
    private static void updateCounts() {
        int userCount = countUsers(root); // Count the users
        int groupCount = countGroups(root); // Count the groups
        button5.setText("User Total: " + userCount);
        button6.setText("Group Total: " + groupCount);
    }

    private static int countUsers(DefaultMutableTreeNode node) {
        int userCount = 0;
        int childCount = node.getChildCount();
        for (int i = 0; i < childCount; i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
            if (child instanceof UserTreeNode && ((UserTreeNode) child).isUser()) {
                userCount++;
            }
            userCount += countUsers(child);
        }
        return userCount;
    }

    private static int countGroups(DefaultMutableTreeNode node) {
        int groupCount = 0;
        int childCount = node.getChildCount();
        for (int i = 0; i < childCount; i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
            if (child instanceof UserTreeNode && !((UserTreeNode) child).isUser()) {
                groupCount++;
            }
            groupCount += countGroups(child);
        }
        return groupCount;
    }
}
