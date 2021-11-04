package budget.simple.budgetsimple_back_end.exception;

public class NotExistingUserException extends RuntimeException{
    public NotExistingUserException(){
        super("This user does not exist in the system!");
    }
}
