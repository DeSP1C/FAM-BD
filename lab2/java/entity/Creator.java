package entity;

import jakarta.persistence.*;

@Entity
@Table(name="creators")
public class Creator {
    @Id
    @Column(name="creator_id", length=5, unique=true, nullable = false)
    int creatorId;

    @Column(name="first_name", length=20, nullable = false)
    String firstName;
    @Column(name="last_name", length=20, nullable = false)
    String lastName;

    public Creator(){

    }

    public Creator(int creatorId, String firstName, String lastName){
        this.creatorId = creatorId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

