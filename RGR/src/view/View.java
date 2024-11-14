package view;

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

    public void displayUserShow(ArrayList<User> users){
        for(User user : users) user.show();
    }

    public void displayCatalogueShow(ArrayList<Catalogue> catalogues){
        for(Catalogue catalogue : catalogues) catalogue.show();
    }

    public void displayMusicShow(ArrayList<Music> musics){
        for(Music music : musics) music.show();
    }

    public void displayCreatorShow(ArrayList<Creator> creators){
        for(Creator creator : creators) creator.show();
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
        System.out.println("2. View one creator.");
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

    public Catalogue displayCatalogueCreateMenu(){
        int catalogueId = catalogueInputId("Enter catalogue id: ");

        System.out.println("Enter catalogue name: ");
        inputFlag();
        String catalogueName = scanner.next();

        int userId = userInputId("Enter user id: ");

        return new Catalogue(catalogueId, catalogueName, userId);
    }

    public Music displayMusicCreateMenu(){
        int musicId = musicInputId("Enter music id: ");

        System.out.println("Enter music name: ");
        inputFlag();
        String musicName = scanner.next();

        int creatorId = creatorInputId("Enter creator id: ");
        int catalogueId = catalogueInputId("Enter catalogue id: ");

        System.out.println("Enter number of views: ");
        inputFlag();
        int views = scanner.nextInt();

        return new Music(musicId, musicName, creatorId, catalogueId, views);
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

    public Catalogue displayCatalogueUpdateMenu(Catalogue catalogueOld){
        //Вивід старого значення + надання можливості ввести нове
        int catalogueId = catalogueOld.getCatalogueId();
        int userId;
        String catalogueName;

        System.out.println("Old user id: " + catalogueOld.getUserId() + ".");
        System.out.println("Enter new user id: ");
        if(scanner.hasNextInt())
            userId = scanner.nextInt();
        else{
            while(true){
                displayTryAgainMessage();
                if(scanner.hasNextInt()){
                    userId = scanner.nextInt();
                    break;
                }
            }
        }

        System.out.println("Old catalogue name: " + catalogueOld.getCatalogueName() + ".");
        System.out.println("Enter new catalogue name: ");
        catalogueName = scanner.next();

        return new Catalogue(catalogueId, catalogueName, userId);

    }

    public Music displayMusicUpdateMenu(Music musicOld){
        //Вивід старого значення + надання можливості ввести нове
        int musicId = musicOld.getMusicId();
        int catalogueId;
        int creatorId;
        int views;
        String musicName;

        System.out.println("Old music name: " + musicOld.getMusicName() + ".");
        System.out.println("Enter new music name: ");
        musicName = scanner.next();

        System.out.println("Old catalogue id: " + musicOld.getCatalogueId() + ".");
        System.out.println("Enter new catalogue id: ");
        if(scanner.hasNextInt())
            catalogueId = scanner.nextInt();
        else{
            while(true){
                displayTryAgainMessage();
                if(scanner.hasNextInt()){
                    catalogueId = scanner.nextInt();
                    break;
                }
            }
        }

        System.out.println("Old creator id: " + musicOld.getCreatorId() + ".");
        System.out.println("Enter new creator id: ");
        if(scanner.hasNextInt())
            creatorId = scanner.nextInt();
        else{
            while(true){
                displayTryAgainMessage();
                if(scanner.hasNextInt()){
                    creatorId = scanner.nextInt();
                    break;
                }
            }
        }

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
        return new Music(musicId, musicName, creatorId, catalogueId, views);
    }

    public Creator displayCreatorUpdateMenu(Creator creatorOld){
        //Вивід старого значення + надання можливості ввести нове
        int creatorId = creatorOld.getCreatorId();
        String firstName;
        String lastName;

        System.out.println("Old first name: " + creatorOld.getFirstName() + ".");
        System.out.println("Enter new first name: ");
        firstName = scanner.next();

        System.out.println("Old last name: " + creatorOld.getLastName() + ".");
        System.out.println("Enter new last name: ");
        lastName = scanner.next();



        return new Creator(creatorId, firstName, lastName);
    }

    public void closeScanner(){
        scanner.close();
    }
}
