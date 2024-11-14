package entity;

public class Creator {
    int creatorId;
    String firstName;
    String lastName;

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

    public void show(){
        System.out.printf("creator id = %-5d name = %s\n", creatorId, (firstName + " " + lastName));
    }
}

