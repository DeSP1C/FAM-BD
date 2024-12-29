package dto;

public record CreatorDto(int creatorId, String firstName, String lastName) {
    public void show() {
        System.out.printf("creator id = %-5d name = %s\n", creatorId, (firstName + " " + lastName));
    }
}