package models;

import play.db.jpa.Model;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

import play.data.validation.Email;
import play.data.validation.Required;

import org.mindrot.jbcrypt.BCrypt;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends Model {
    @Required
    public String firstname;

    @Required
    public String lastname;

    @Email
    @Required
    @Column(unique=true)
    public String email;

    @Required
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Station> stations = new ArrayList<Station>();

    public User(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        setPassword(password);
    }

    public User() {}

    public static User findByEmail(String email) {
        return find("email", email).first();
    }

    public boolean setPassword(String password) {
        if (isValidPassword(password)) {
            this.password = BCrypt.hashpw(password, BCrypt.gensalt());
            return true;
        } else {
            return false;
        }
    }

    public boolean isValidPassword(String password) {
        // check if password is at least 8 characters long, has numbers, and both upper and lowercase letters
        return password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}");
    }

    public boolean checkPassword(String password) {
        if(BCrypt.checkpw(password, this.password)) {
            return true;
        } else {
            return false;
        }
    }

}
