package assignment1.billboard.ControlPanel;

/**
 * Class for creating and managing users and their details
 */
public final class User {

    // User details
    protected String username;
    protected String password;

    // Permissions (createBB, listBB, scheduleBB, editPermissions)
    protected boolean[] permissions;

    /**
     * Constructor to create a new user
     * @param user_username of new user
     * @param user_password of new user
     * @param user_permissions of new user (boolean array of length 4)
     */
    public User(String user_username, String user_password, boolean[] user_permissions) {
        username = user_username;
        password = user_password;
        permissions = user_permissions;
    }

    /**
     * Get all valid permissions from user
     * @return a boolean array of user's permissions (createBB, listBB, scheduleBB, editPermissions)
     */
    public boolean[] getPermissions() {
        return permissions;
    }

    /**
     * Get the selected user's username
     * @return the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the selected user's password
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Check a single permission of user
     * @param type 0-4 (createBB, listBB, scheduleBB, editPermissions)
     * @return boolean of their permissions for the selected type
     */
    public boolean checkPermissions(int type) {
        // Error if invalid input
        if (type > 4 || type < 0) {
            throw new NullPointerException("Permission type must be 0, 1, 2, or 3 " +
                    "(createBB, listBB, scheduleBB, editPermissions)");
        }
        // Return true/false based on type
        return getPermissions()[type];
    }

    /**
     * Change password of user
     * @param new_password for user
     */
    public void changePassword(String new_password) {
        this.password = new_password;
    }
}
