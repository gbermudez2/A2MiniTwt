import javax.swing.tree.DefaultMutableTreeNode;

public class UserTreeNode extends DefaultMutableTreeNode {
    private static int nextUserID = 1;
    private static int nextGroupID = 1;

    private int userID;
    private int groupID;
    private boolean isUser;

    public UserTreeNode(String userObject, boolean isUser) {
        super(userObject);

        if (isUser) {
            this.userID = nextUserID++;
            this.groupID = 0; // 0 indicates that it's a user, not a group
            this.isUser = true;
        } else {
            this.userID = 0; // 0 indicates that it's a group, not a user
            this.groupID = nextGroupID++;
            this.isUser = false;
        }
    }

    public int getUserID() {
        return userID;
    }

    public int getGroupID() {
        return groupID;
    }

    public boolean isUser() {
        return isUser;
    }
}
