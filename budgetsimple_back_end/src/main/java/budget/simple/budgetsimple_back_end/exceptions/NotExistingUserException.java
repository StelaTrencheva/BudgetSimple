package budget.simple.budgetsimple_back_end.exceptions;

public class NotExistingUserException extends RuntimeException{
    public NotExistingUserException(){
        super("This user does not exist in the system!");
    }
}
