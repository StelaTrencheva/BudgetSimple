package budget.simple.exceptions;

public class NotExistingUserException extends RuntimeException{
    public NotExistingUserException(){
        super("This user does not exist in the system!");
    }
}
