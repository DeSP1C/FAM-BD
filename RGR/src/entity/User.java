package entity;

public class User {
    private final int userId;
    private final String email;
    private final String firstName;
    private final String lastName;

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

    public void show() throws IllegalStateException {
        System.out.printf("user id = %-5d name = %-20s email = %-30s\n", userId, (firstName + " " + lastName), email);
    }

}

