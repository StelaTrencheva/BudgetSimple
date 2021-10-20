package budget.simple.budgetsimple_back_end.logic.mappers;

import budget.simple.budgetsimple_back_end.controller.dto.UserDTO;
import budget.simple.budgetsimple_back_end.logic.User;
import budget.simple.budgetsimple_back_end.logic.UserContact;
import budget.simple.budgetsimple_back_end.logic.UserCredentials;
import budget.simple.budgetsimple_back_end.logic.UserPersonalInfo;
import lombok.Getter;
import lombok.Setter;

public class UserMapper {
    @Getter @Setter private User user;

    public UserMapper(UserDTO userDTO){
        UserCredentials userCredentials = new UserCredentials(userDTO.getUsername(), userDTO.getPassword());
        UserContact userContact = new UserContact(userDTO.getEmail(),userDTO.getAddress(),userDTO.getPhoneNum());
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo(userDTO.getFirstName(),userDTO.getLastName(),userDTO.getDateOfBirth());
        user = new User(userDTO.getId(),userPersonalInfo,userContact,userDTO.getPosition(),userCredentials);
    }
}
