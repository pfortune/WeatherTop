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
  @Column(unique = true)
  public String email;

  @Required
  private String password;

  @OneToMany(cascade = CascadeType.ALL)
  public List<Station> stations = new ArrayList<Station>();

  /**
   * Constructor for User.
   * @param firstname User's first name.
   * @param lastname User's last name.
   * @param email User's email address.
   * @param password User's password.
   */
  public User(String firstname, String lastname, String email, String password) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email.toLowerCase();
    setPassword(password);
  }

  /**
   * Finds a user by their email address.
   * @param email The email to search for.
   * @return The User object associated with the given email, or null if not found.
   */
  public static User findByEmail(String email) {
    return find("lower(email)", email.toLowerCase()).first();
  }

  /**
   * Sets the user's password, hashing it for security.
   * @param password The password to set.
   * @return True if the password is valid and was successfully set, false otherwise.
   */
  public boolean setPassword(String password) {
    if (isValidPassword(password)) {
      this.password = BCrypt.hashpw(password, BCrypt.gensalt());
      return true;
    } else {
      return false;
    }
  }

  /**
   * Checks if a password is valid.
   * @param password The password to check.
   * @return True if the password is valid, false otherwise.
   */
  public boolean isValidPassword(String password) {
    // check if password is at least 8 characters long, has numbers, and both upper and lowercase letters
    return password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}");
  }

  /**
   * Checks if a provided password matches the user's password.
   * @param password The password to check.
   * @return True if the password matches, false otherwise.
   */
  public boolean checkPassword(String password) {
    if (BCrypt.checkpw(password, this.password)) {
      return true;
    } else {
      return false;
    }
  }

}
