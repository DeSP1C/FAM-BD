package view;

import dto.*;
import entity.*;

import java.util.ArrayList;
import java.util.Scanner;

public class View {
    private final Scanner scanner;

    public View(){
        this.scanner = new Scanner(System.in);
    }

    public int getOptionChoice(int from, int to){
        int choice = 99;

        inputFlag();
        if(scanner.hasNextInt())
            choice = scanner.nextInt();
        if(!(choice < from || choice > to || choice == 9))
            choice = repeatUntilAccess(choice, from, to);

        return choice;
    }

    private void displayTryAgainMessage(){
        System.out.println("Your choice is unavailable. Please, try again");
    }

    private void inputFlag(){
        System.out.print("-> ");
    }

    private int repeatUntilAccess(int choice, int from, int to){
        int temp = choice;
        while (temp < from || temp > to) {
            displayTryAgainMessage();
            inputFlag();
            if (scanner.hasNextInt())
                temp = scanner.nextInt();
        }
        return temp;

    }

    public int getUserNumber(){
        inputFlag();
        int num = 99;
        if(scanner.hasNextInt()){
            num = scanner.nextInt();
        }
        else {
            displayTryAgainMessage();
            getUserNumber();
        }
        return num;
    }

    public int userInputId(String message){
        int choice;

        System.out.println(message);
        inputFlag();
        if(scanner.hasNextInt())
            choice = scanner.nextInt();
        else{
            displayTryAgainMessage();
            scanner.nextLine();
            choice = userInputId(message);
        }
        return choice;
    }

    public int catalogueInputId(String message){
        int choice;

        System.out.println(message);
        inputFlag();
        if(scanner.hasNextInt())
            choice = scanner.nextInt();
        else{
            displayTryAgainMessage();
            scanner.nextLine();
            choice = catalogueInputId(message);
        }
        return choice;
    }

    public int musicInputId(String message){
        int choice;

        System.out.println(message);
        inputFlag();
        if(scanner.hasNextInt())
            choice = scanner.nextInt();
        else{
            displayTryAgainMessage();
            scanner.nextLine();
            choice = musicInputId(message);
        }
        return choice;
    }

    public int creatorInputId(String message){
        int choice;

        System.out.println(message);
        inputFlag();
        if(scanner.hasNextInt())
            choice = scanner.nextInt();
        else{
            displayTryAgainMessage();
            scanner.nextLine();
            choice = creatorInputId(message);
        }
        return choice;
    }

    public void displayUserShow(ArrayList<UserDto> users){
        for(UserDto user : users) user.show();
    }

    public void displayCatalogueShow(ArrayList<CatalogueDto> catalogues){
        for(CatalogueDto catalogue : catalogues) catalogue.show();
    }

    public void displayMusicShow(ArrayList<MusicDto> musics){
        for(MusicDto music : musics) music.show();
    }

    public void displayCreatorShow(ArrayList<CreatorDto> creators){
        for(CreatorDto creator : creators) creator.show();
    }

    public void displayStartMenu(){
        System.out.println("\t\tMUSIC CATALOGUE");
        System.out.println("Choose one option:");
        System.out.println("1. User table.");
        System.out.println("2. Catalogue table.");
        System.out.println("3. Music table.");
        System.out.println("4. Creator table.");
        System.out.println("9. Back to start menu.");
        System.out.println("0. Exit.");
    }

    public void displayObjectMenu(String object){
        String upperObject = object.toUpperCase();
        System.out.println("\t\t" + upperObject + " MENU");
        System.out.println("Choose what you wish to do:");
        System.out.println("1. View " + object + ".");
        System.out.println("2. Add " + object + ".");
        System.out.println("3. Delete " + object + ".");
        System.out.println("4. Update " + object + ".");
        System.out.println("9. Back to start menu.");
        System.out.println("0. Exit");
    }
    //View one - objectInputId
    public void displayUserViewMenu(){
        System.out.println("Select one of the methods for viewing the table:");
        System.out.println("1. View the whole table.");
        System.out.println("2. View one user.");
        System.out.println("9. Back to start menu.");
        System.out.println("0. Exit");
    }
    //View one - objectInputId.
    public void displayCatalogueViewMenu(){
        System.out.println("Select one of the methods for viewing the table:");
        System.out.println("1. View the whole table.");
        System.out.println("2. View one catalogue.");
        System.out.println("3. View the largest catalogue.");
        System.out.println("9. Back to start menu.");
        System.out.println("0. Exit");
    }

    public void displayMusicViewMenu(){
        System.out.println("Select one of the methods for viewing the table:");
        System.out.println("1. View the whole table.");
        System.out.println("2. View one song.");
        System.out.println("3. View top 3 popular song.");
        System.out.println("9. Back to start menu.");
        System.out.println("0. Exit");
    }

    //View one - creatorInputId. View num of views - creatorInputId
    public void displayCreatorViewMenu(){
        System.out.println("Select one of the methods for viewing the table:");
        System.out.println("1. View the whole table.");
        System.out.println("2. View one creator.");
        System.out.println("3. See the number of views by creator.");
        System.out.println("9. Back to start menu.");
        System.out.println("0. Exit");
    }

    //User or Creator
    public void displayAddMenu(String object){
        System.out.println("Select one of the methods for adding to the table:");
        System.out.println("1. Create new " + object + ".");
        System.out.println("2. Generate new " + object + ".");
        System.out.println("9. Back to start menu.");
        System.out.println("0. Exit");
    }

    public void displayGenerateDataMenu(String object){
        System.out.println("Enter how many " + object + "s you want to generate: ");
    }

    public User displayUserCreateMenu(){
        int userId = userInputId("Enter user id: ");

        System.out.println("Enter user first name: ");
        inputFlag();
        String firstName = scanner.next();

        System.out.println("Enter user last name: ");
        inputFlag();
        String lastName = scanner.next();

        System.out.println("Enter user email: ");
        inputFlag();
        String email = scanner.next();

        return new User(userId, email, firstName, lastName);
    }

    public Catalogue displayCatalogueCreateMenu(User user){
        int catalogueId = catalogueInputId("Enter catalogue id: ");

        System.out.println("Enter catalogue name: ");
        inputFlag();
        String catalogueName = scanner.next();

        return new Catalogue(catalogueId, catalogueName, user);
    }

    public MusicDto displayMusicCreateMenu(CatalogueDto catalogue, CreatorDto creator){ //fix
        int musicId = musicInputId("Enter music id: ");

        System.out.println("Enter music name: ");
        inputFlag();
        String musicName = scanner.next();

        System.out.println("Enter number of views: ");
        inputFlag();
        int views = scanner.nextInt();

        return new MusicDto(musicId, musicName, creator, catalogue, views);
    }

    public Creator displayCreatorCreateMenu(){
        int creatorId = creatorInputId("Enter creator id: ");

        System.out.println("Enter creator first name: ");
        inputFlag();
        String firstName = scanner.next();

        System.out.println("Enter creator last name: ");
        inputFlag();
        String lastName = scanner.next();

        return new Creator(creatorId, firstName, lastName);
    }

    public User displayUserUpdateMenu(User userOld){
        //Вивід старого значення + надання можливості ввести нове
        int userId = userOld.getUserId();
        String firstName;
        String lastName;
        String email;

        System.out.println("Old first name: " + userOld.getFirstName() + ".");
        System.out.println("Enter new first name: ");
        inputFlag();
        firstName = scanner.next();

        System.out.println("Old last name: " + userOld.getLastName() + ".");
        System.out.println("Enter new last name: ");
        inputFlag();
        lastName = scanner.next();

        System.out.println("Old email email: " + userOld.getEmail() + ".");
        System.out.println("Enter new email: ");
        inputFlag();
        email = scanner.next();


        return new User(userId, email, firstName, lastName);
    }

    public CatalogueDto displayCatalogueUpdateMenu(CatalogueDto catalogueOld){
        int catalogueId = catalogueOld.catalogueId();
        String catalogueName;

        System.out.println("Old catalogue name: " + catalogueOld.catalogueName() + ".");
        System.out.println("Enter new catalogue name: ");
        catalogueName = scanner.next();

        return new CatalogueDto(catalogueId, catalogueName, catalogueOld.user());

    }

    public Music displayMusicUpdateMenu(Music musicOld){ //fix
        int musicId = musicOld.getMusicId();
        int views;
        String musicName;

        System.out.println("Old music name: " + musicOld.getMusicName() + ".");
        System.out.println("Enter new music name: ");
        musicName = scanner.next();


        System.out.println("Old views: " + musicOld.getViews() + ".");
        System.out.println("Enter new views: ");
        if(scanner.hasNextInt())
            views = scanner.nextInt();
        else{
            while(true){
                displayTryAgainMessage();
                if(scanner.hasNextInt()){
                    views = scanner.nextInt();
                    break;
                }
            }
        }
        return new Music(musicId, musicName, musicOld.getCreator(), musicOld.getCatalogue(), views);
    }

    public CreatorDto displayCreatorUpdateMenu(CreatorDto creatorOld){
        //Вивід старого значення + надання можливості ввести нове
        int creatorId = creatorOld.creatorId();
        String firstName;
        String lastName;

        System.out.println("Old first name: " + creatorOld.firstName() + ".");
        System.out.println("Enter new first name: ");
        firstName = scanner.next();

        System.out.println("Old last name: " + creatorOld.lastName() + ".");
        System.out.println("Enter new last name: ");
        lastName = scanner.next();



        return new CreatorDto(creatorId, firstName, lastName);
    }

    public void closeScanner(){
        scanner.close();
    }
}
