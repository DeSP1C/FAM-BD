package dto;


public record UserDto(int userId, String email, String firstName, String lastName) {
    public void show() {
        System.out.printf("user id = %-5d name = %-20s email = %-30s%n", userId, (firstName + " " + lastName), email);
    }
}