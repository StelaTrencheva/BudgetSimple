package budget.simple.budgetsimple_back_end.logic.mapper;

import budget.simple.budgetsimple_back_end.model.UserDTO;
import budget.simple.budgetsimple_back_end.logic.User;
import budget.simple.budgetsimple_back_end.model.UserContact;
import budget.simple.budgetsimple_back_end.model.UserCredentials;
import budget.simple.budgetsimple_back_end.model.UserPersonalInfo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class UserMapper {
    private final BCryptPasswordEncoder passwordEncoder;

    public UserMapper(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User mapToModel(UserDTO userDTO) {
        String encryptedPassword = this.passwordEncoder.encode(userDTO.getPassword());
        UserCredentials userCredentials = new UserCredentials(userDTO.getUsername(), encryptedPassword);
        UserContact userContact = new UserContact(userDTO.getEmail(),userDTO.getAddress(),userDTO.getPhoneNum());
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo(userDTO.getFirstName(),userDTO.getLastName(),userDTO.getDateOfBirth());

        return new User(userDTO.getId(),userPersonalInfo,userContact,userDTO.getRole(),userCredentials);
    }
}
