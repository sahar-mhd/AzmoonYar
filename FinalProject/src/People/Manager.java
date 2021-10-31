package People;


/**
 *
 * this is the type of person that can set tests, get answers , mark them , and
 * enjoy that!! :|
 */
public class Manager extends Person {
    
    public Manager (String username){
        super(username);
    }
    public Manager( String userName, String password) {
        super(userName,password);              
    }
    public Manager(String firstName, String lastName, String userName, String password) {
        super(firstName, lastName, userName, password);
    }
    
}
