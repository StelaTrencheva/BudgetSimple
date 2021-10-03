package budget.simple.logic;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.lang.String;

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
                    name = "position",
                    nullable = false,
                    columnDefinition = "enum('USER', 'ADMIN', 'CUSTOMER_SUPPORT')"
            )
    @Enumerated(EnumType.STRING)
    @Getter @Setter private Position position;
    @Column
            (
                    name = "password",
                    nullable = false,
                    columnDefinition = "TEXT"
            )
    @Getter @Setter private String password;

    public User(Long id,String firstName, String lastName, String email, String phoneNum, String username, String password,Position position) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.username = username;
        this.password = password;
        this.id=id;
        this.position=position;
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
                ", position='" + this.position+
                '}';
    }


}
