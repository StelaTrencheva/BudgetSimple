package budget.simple.budgetsimple_back_end.logic;

import lombok.Getter;
import lombok.Setter;

public class UserContact {
    @Getter @Setter private String email;
    @Getter @Setter private String address;
    @Getter @Setter private String phoneNum;

    public UserContact(String email,String address,String phoneNum){
        this.email = email;
        this.address = address;
        this.phoneNum = phoneNum;
    }
}
