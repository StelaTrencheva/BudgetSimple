package budget.simple.budgetsimple_back_end.model;

import budget.simple.budgetsimple_back_end.logic.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class CustomerSupportDTO {
    @Getter
    @Setter
    private Long id;
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
    @Getter @Setter private String email;
    @Getter @Setter private String address;
    @Getter @Setter private String phoneNum;
    @Getter @Setter private String username;
    @Getter @Setter private Role role;
    @Getter @Setter private String password;
    @Getter @Setter private Date dateOfBirth;
    @Getter @Setter private String bankAccount;
    @Getter @Setter private String emergencyPhoneNum;
    @Getter @Setter private Double hourlyWage;
    @Getter @Setter private Date firstWorkingDay;


}
