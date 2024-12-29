package entity;

import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name="user_id", unique = true, nullable = false)
    private int userId;

    @Column(name="email", length = 30)
    private String email;

    @Column(name="first_name", length = 20)
    private String firstName;

    @Column(name="last_name", length = 20)
    private String lastName;


    public User(){

    }

    public User(int userId, String email, String firstName, String lastName){
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}

