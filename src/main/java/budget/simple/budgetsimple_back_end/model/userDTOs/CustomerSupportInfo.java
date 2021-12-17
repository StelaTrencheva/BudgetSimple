package budget.simple.budgetsimple_back_end.model.userDTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class CustomerSupportInfo {
    @Getter @Setter private String bankAccount;
    @Getter @Setter private String emergencyPhoneNum;
    @Getter @Setter private Double hourlyWage;
    @Getter @Setter private Date firstWorkingDay;
}
