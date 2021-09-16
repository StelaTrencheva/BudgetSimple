package budget.simple.exceptions;

public class NoUsersFoundException extends RuntimeException{
    public NoUsersFoundException(){
        super("There are not any users in the system!");
    }
}
