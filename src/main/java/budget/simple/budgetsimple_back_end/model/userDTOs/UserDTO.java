package budget.simple.budgetsimple_back_end.model.userDTOs;

import budget.simple.budgetsimple_back_end.logic.user.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class UserDTO {
    @Getter @Setter private String id;
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
    @Getter @Setter private String email;
    @Getter @Setter private String address;
    @Getter @Setter private String phoneNum;
    @Getter @Setter private String username;
    @Getter @Setter private Role role;
    @Getter @Setter private String password;
    @Getter @Setter private Date dateOfBirth;

}
