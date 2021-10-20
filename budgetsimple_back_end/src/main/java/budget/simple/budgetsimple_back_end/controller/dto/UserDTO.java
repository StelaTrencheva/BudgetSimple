package budget.simple.budgetsimple_back_end.controller.dto;

import budget.simple.budgetsimple_back_end.logic.Position;
import budget.simple.budgetsimple_back_end.logic.UserContact;
import budget.simple.budgetsimple_back_end.logic.UserCredentials;
import budget.simple.budgetsimple_back_end.logic.UserPersonalInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class UserDTO {
    @Getter @Setter private Long id;
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
    @Getter @Setter private String email;
    @Getter @Setter private String address;
    @Getter @Setter private String phoneNum;
    @Getter @Setter private String username;
    @Getter @Setter private Position position;
    @Getter @Setter private String password;
    @Getter @Setter private Date dateOfBirth;

    public UserDTO(Long id, UserPersonalInfo personalInfo, UserContact userContact, Position position, UserCredentials userCredentials) {

        this.firstName = personalInfo.getFirstName();
        this.lastName = personalInfo.getLastName();
        this.email = userContact.getEmail();
        this.phoneNum = userContact.getPhoneNum();
        this.address = userContact.getAddress();
        this.username = userCredentials.getUsername();
        this.password = userCredentials.getPassword();
        this.id=id;
        this.position = position;
        this.dateOfBirth=personalInfo.getDateOfBirth();
    }

    public UserDTO() {

    }
}
