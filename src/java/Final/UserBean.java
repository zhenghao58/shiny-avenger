/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

/**
 *
 * @author apple
 */
public class UserBean implements java.io.Serializable {

    private String username;
    private String password;
    private int user_id;
    private String name;
    private boolean valid;

    public String getName() {
        return this.name;
    }
    
    public int getUser_id(){
        return user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        password = newPassword;
    }

    public void setName(String newname) {
        name = newname;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean newValid) {
        valid = newValid;
    }
    
    public void setUserName(String userName) {
        username = userName;
    }
    public String getUsername() {
        return username;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

}
