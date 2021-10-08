package budget.simple.budgetsimple_back_end.exceptions;

public class ExistingUserException extends RuntimeException{
    public ExistingUserException(){
        super("This user already exist in the system!");
    }
}
