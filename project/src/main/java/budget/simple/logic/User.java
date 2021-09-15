package budget.simple.logic;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import lombok.Getter;
import lombok.Setter;
import java.util.Objects;
import java.lang.String;


public class User {
    @Getter @Setter private int id;
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
    @Getter @Setter private String email;
    @Getter @Setter private String address;
    @Getter @Setter private String phoneNum;
    @Getter @Setter private String username;
    @Getter @Setter private String password;

    public User(int id,String firstName, String lastName, String email, String phoneNum, String username, String password) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.username = username;
        this.password = password;
        this.id=id;
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
                '}';
    }


}
