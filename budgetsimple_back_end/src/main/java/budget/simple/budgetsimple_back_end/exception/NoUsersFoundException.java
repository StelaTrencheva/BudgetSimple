package budget.simple.budgetsimple_back_end.exception;

public class NoUsersFoundException extends RuntimeException{
    public NoUsersFoundException(){
        super("There are not any users in the system!");
    }
}
