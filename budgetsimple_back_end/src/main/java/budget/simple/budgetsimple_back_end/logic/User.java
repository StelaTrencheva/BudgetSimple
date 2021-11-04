package budget.simple.budgetsimple_back_end.logic;

import budget.simple.budgetsimple_back_end.model.UserContact;
import budget.simple.budgetsimple_back_end.model.UserCredentials;
import budget.simple.budgetsimple_back_end.model.UserPersonalInfo;
import budget.simple.budgetsimple_back_end.model.UserWorkingInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "User")
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "email",
                        columnNames = "email"
                ),
                @UniqueConstraint(
                        name = "username",
                        columnNames = "username"
                        )

        })
public class User {
    @Id
    @SequenceGenerator
            (
                    name="user_sequence",
                    sequenceName = "user_sequence",
                    allocationSize = 1
            )
    @GeneratedValue
            (
                    strategy = SEQUENCE,
                    generator = "user_sequence"

            )
    @Column
            (
                    name = "id",
                    updatable = false
            )
    @Getter @Setter private Long id;
    @Column
            (
                    name = "first_name",
                    nullable = false
            )
    @Getter @Setter private String firstName;
    @Column
            (
                    name = "last_name",
                    nullable = false,
                    columnDefinition = "TEXT"
            )
    @Getter @Setter private String lastName;
    @Column
            (
                    name = "email",
                    nullable = false,
                    columnDefinition = "varchar(255)"
            )
    @Getter @Setter private String email;
    @Column
            (
                    name = "address",
                    nullable = false,
                    columnDefinition = "TEXT"
            )
    @Getter @Setter private String address;
    @Column
            (
                    name = "phone_number",
                    nullable = false,
                    columnDefinition = "TEXT"
            )
    @Getter @Setter private String phoneNum;
    @Column
            (
                    name = "username",
                    nullable = false,
                    columnDefinition = "varchar(255)"
            )
    @Getter @Setter private String username;
    @Column
            (
                    name = "role",
                    nullable = false,
                    columnDefinition = "enum('USER', 'ADMIN', 'CUSTOMER_SUPPORT')"
            )
    @Enumerated(EnumType.STRING)
    @Getter @Setter private Role role;
    @Column
            (
                    name = "password",
                    nullable = false,
                    columnDefinition = "TEXT"
            )
    @Getter @Setter private String password;


    @Temporal(TemporalType.DATE)
    @Column
            (
                    name = "date_of_birth",
                    nullable = false,
                    columnDefinition = "date"
            )
    @Getter @Setter private Date dateOfBirth;


    @Column
            (
                    name = "emergency_phone_number",
                    columnDefinition = "TEXT"
            )

    @Getter @Setter private String emergencyPhoneNum;
    @Column
            (
                    name = "bank_account",
                    columnDefinition = "TEXT"
            )

    @Getter @Setter private String bankAccount;
    @Column
            (
                    name = "hourly_wage",
                    columnDefinition = "DOUBLE"
            )
    @Getter @Setter private Double hourlyWage;

    @Temporal(TemporalType.DATE)
    @Column
            (
                    name = "first_working_day",
                    columnDefinition = "date"
            )
    @Getter @Setter private Date firstWorkingDay;

    public User(Long id, UserPersonalInfo personalInfo, UserContact userContact, Role role, UserCredentials userCredentials) {

        this.firstName = personalInfo.getFirstName();
        this.lastName = personalInfo.getLastName();
        this.email = userContact.getEmail();
        this.phoneNum = userContact.getPhoneNum();
        this.address = userContact.getAddress();
        this.username = userCredentials.getUsername();
        this.password = userCredentials.getPassword();
        this.id=id;
        this.role = role;
        this.dateOfBirth=personalInfo.getDateOfBirth();
    }
    public User(Long id, UserPersonalInfo personalInfo, UserContact userContact, Role role, UserCredentials userCredentials, UserWorkingInfo userWorkingInfo) {

        this(id,personalInfo,userContact, role,userCredentials);
        this.bankAccount=userWorkingInfo.getBankAccount();
        this.emergencyPhoneNum=userWorkingInfo.getEmergencyPhoneNum();
        this.firstWorkingDay=userWorkingInfo.getFirstWorkingDay();
        this.hourlyWage=userWorkingInfo.getHourlyWage();
    }
    public User() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                ", name='" + this.firstName+" "+this.lastName + '\'' +
                ", email=" + this.email +'\''+
                ", address='" + this.address+ '\'' +
                ", phone number='" + this.phoneNum+ '\'' +
                ", username='" + this.username+
                ", position='" + this.role +
                '}';
    }


}
