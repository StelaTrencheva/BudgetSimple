package budget.simple.budgetsimple_back_end.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class UserPersonalInfo {
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
    @Getter @Setter private Date dateOfBirth;

    public UserPersonalInfo(String firstName, String lastName, Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

}
