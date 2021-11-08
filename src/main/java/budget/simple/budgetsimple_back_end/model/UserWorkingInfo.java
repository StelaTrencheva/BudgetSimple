package budget.simple.budgetsimple_back_end.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class UserWorkingInfo {

    @Getter @Setter private String bankAccount;
    @Getter @Setter private String emergencyPhoneNum;
    @Getter @Setter private Double hourlyWage;
    @Getter @Setter private Date firstWorkingDay;

    public UserWorkingInfo(String bankAccount, String emergencyPhoneNum, Date firstWorkingDay, Double hourlyWage) {
        this.bankAccount = bankAccount;
        this.hourlyWage = hourlyWage;
        this.emergencyPhoneNum = emergencyPhoneNum;
        this.firstWorkingDay=firstWorkingDay;
    }
}
