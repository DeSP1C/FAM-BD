package controller;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import SQLError.DataBaseException;
import model.ConnectionToDB;
import model.Model;
import view.View;
import entity.*;

public class Controller {
    private final Model model;
    private final View view;
    private final Connection connection;

    public Controller(){
        ConnectionToDB connections = new ConnectionToDB();
        this.connection = connections.connect();
        model = new Model(this.connection);
        view = new View();

    }

    public boolean startMenu(){
        view.displayStartMenu();

        switch(view.getOptionChoice(0, 4)){
            case 0: {
                End();
                return true;
            }
            case 1: {
                //User
                if(userMenu())
                    return true;
            } break;

            case 2: {
                //Catalogue
                if(catalogueMenu())
                    return true;
            } break;

            case 3: {
                //Music
                if(musicMenu())
                    return true;
            } break;

            case 4: {
                //Creator
                if(creatorMenu())
                    return true;
            } break;

            default: System.out.println("Error. Wrote wrong option.");
        }

        return false;
    }
    //1. View
    //2. Add
    //3. Delete
    //4. Update
    private boolean userMenu(){
        view.displayObjectMenu("user");

        switch(view.getOptionChoice(0, 4)){
            case 0: {
                End();
                return true;
            }
            case 1: { //1. View
                view.displayUserViewMenu();
                switch(view.getOptionChoice(0, 2)){
                    case 0: {
                        End();
                        return true;
                    }

                    case 1: {
                       ArrayList<User> user = model.getAllUsers();
                       view.displayUserShow(user);
                    } break;

                    case 2: {
                        try {
                            User user = model.getUser(view.userInputId("Enter the user id of the user you want to view: "));
                            user.show();
                        }catch(NullPointerException e){
                            throw new DataBaseException("This user is not exist.");
                        }
                    } break;

                    case 9: {
                        startMenu();
                    } break;
                }
            } break;
            case 2:{ //2. Add
                view.displayAddMenu("user");
                switch(view.getOptionChoice(0, 2)){
                    case 0: {
                        End();
                        return true;
                    }

                    case 1: {
                        try {
                            User user = view.displayUserCreateMenu();
                            model.addUser(user);
                        } catch(NullPointerException e){
                            throw new DataBaseException("Wrong input data. User is not created.");
                        }
                    } break;

                    case 2: {
                        try {
                            view.displayGenerateDataMenu("user");
                            int amount = view.getUserNumber();
                            model.randomGenerateUser(amount);
                        }catch(NullPointerException e){
                            throw new DataBaseException("Wrong input data. User is not generated.");
                        }
                    } break;

                    case 9: {
                        startMenu();
                    } break;
                }
            } break;
            case 3: {  //3. Delete
                int userId = view.userInputId("Enter the id of the user you want to delete: ");
                model.deleteUser(userId);
            } break;
            case 4: { //4. Update
                try {
                    int userId = view.userInputId("Enter the user id of the user you want to change: ");
                    User user = model.getUser(userId);
                    if(user != null) {
                        User newUser = view.displayUserUpdateMenu(user);
                        model.updateUser(newUser);
                    }
                } catch(NullPointerException e){
                    throw new DataBaseException("Wrong input data. User is not updated.");
                }
            } break;
        }
        return false;
    }

    boolean catalogueMenu(){
        view.displayObjectMenu("catalogue");
        switch(view.getOptionChoice(0, 4)){
            case 0: {
                End();
                return true;
            }

            case 1: { //View
                view.displayCatalogueViewMenu();
                switch(view.getOptionChoice(0, 3)){
                    case 0: {
                        End();
                        return true;
                    }
                    case 1: {
                        ArrayList<Catalogue> catalogues = model.getAllCatalogue();
                        view.displayCatalogueShow(catalogues);
                    }
                    break;
                    case 2: {
                        try {
                            Catalogue catalogue = model.getCatalogue(view.catalogueInputId("Enter the catalogue id of the catalogue you want to view: "));
                            catalogue.show();
                        }catch(NullPointerException e){
                            throw new DataBaseException("This catalogue is not exist.");
                        }
                    } break;

                    case 3:{
                        model.getBiggestCatalogue();
                    } break;

                    case 9: {
                        startMenu();
                    } break;
                }
            } break;

            case 2: { //Add
                        try {
                            Catalogue catalogue = view.displayCatalogueCreateMenu();
                            model.addCatalogue(catalogue);
                        } catch(NullPointerException e){
                            throw new DataBaseException("Wrong input data. Catalog is not created.");
                        }
            } break;

            case 3: {  //3. Delete
                int catalogueId = view.catalogueInputId("Enter the id of the catalogue you want to delete: ");
                model.deleteCatalogue(catalogueId);
            }  break;

            case 4: { //Upgrade
                try {
                    int catalogueId = view.catalogueInputId("Enter the catalogue id of the catalogue you want to change: ");
                    Catalogue catalogue = model.getCatalogue(catalogueId);
                    if(catalogue != null) {
                        Catalogue newCatalogue = view.displayCatalogueUpdateMenu(catalogue);
                        model.updateCatalogue(newCatalogue);
                    }
                } catch(NullPointerException e){
                    throw new DataBaseException("Wrong input data. Catalog is not updated.");
                }
            } break;

            case 9: {
                startMenu();
            } break;
        }
        return false;
    }

    private boolean musicMenu(){
        view.displayObjectMenu("music");
        switch(view.getOptionChoice(0, 4)){
            case 0: {
                End();
                return true;
            }

            case 1: { //View
                view.displayMusicViewMenu();
                switch(view.getOptionChoice(0, 3)){
                    case 0: {
                        End();
                        return true;
                    }
                    case 1: {
                        ArrayList<Music> musics = model.getAllMusic();
                        view.displayMusicShow(musics);
                    }
                    break;
                    case 2: {
                        try {
                            Music music = model.getMusic(view.musicInputId("Enter the music id of the music you want to view: "));
                            music.show();
                        }catch(NullPointerException e){
                            throw new DataBaseException("This music is not exist.");
                        }
                    } break;

                    case 3:{
                        model.getPopularMusic();
                    } break;

                    case 9: {
                        startMenu();
                    } break;
                }
            } break;

            case 2: { //Add
                try {
                    Music music = view.displayMusicCreateMenu();
                    model.addMusic(music);
                } catch(NullPointerException e){
                    throw new DataBaseException("Wrong input data. Music is not created.");
                }
            } break;

            case 3: {  //3. Delete
                int musicId = view.catalogueInputId("Enter the id of the music you want to delete: ");
                model.deleteMusic(musicId);
            }  break;

            case 4: { //Upgrade
                try {
                    int musicId = view.musicInputId("Enter the music id of the music you want to change: ");
                    Music music = model.getMusic(musicId);
                    if(music != null) {
                        Music newMusic = view.displayMusicUpdateMenu(music);
                        model.updateMusic(newMusic);
                    }
                } catch(NullPointerException e){
                    throw new DataBaseException("Wrong input data. Music is not updated.");
                }
            } break;

            case 9: {
                startMenu();
            } break;
        }
        return false;
    }

    private boolean creatorMenu(){
        view.displayObjectMenu("creator");
        switch(view.getOptionChoice(0, 4)){
            case 0: {
                End();
                return true;
            }
            case 1: { //1. View
                view.displayCreatorViewMenu();
                switch(view.getOptionChoice(0, 3)){
                    case 0: {
                        End();
                        return true;
                    }

                    case 1: {
                        ArrayList<Creator> creator = model.getAllCreator();
                        view.displayCreatorShow(creator);
                    } break;

                    case 2: {
                        try {
                            Creator creator = model.getCreator(view.creatorInputId("Enter the creator id of the creator you want to view:"));
                            creator.show();
                        }catch(NullPointerException e){
                            throw new DataBaseException("This creator is not exist.");
                        }
                    } break;

                    case 3: {
                        try{
                            int creatorId = view.creatorInputId("Enter the creator id of the creator you want to view:");
                            model.getCreatorViews(creatorId);
                        }catch(NullPointerException e){
                            throw new DataBaseException("");
                        }
                    }

                    case 9: {
                        startMenu();
                    } break;
                }
            } break;
            case 2:{ //2. Add
                view.displayAddMenu("creator");
                switch(view.getOptionChoice(0, 2)){
                    case 0: {
                        End();
                        return true;
                    }

                    case 1: {
                        try {
                            Creator creator = view.displayCreatorCreateMenu();
                            model.addCreator(creator);
                        } catch(NullPointerException e){
                            throw new DataBaseException("Wrong input data. Creator is not created.");
                        }
                    } break;

                    case 2: {
                        try {
                            view.displayGenerateDataMenu("creator");
                            int amount = view.getUserNumber();
                            model.randomGenerateCreator(amount);
                        }catch(NullPointerException e){
                            throw new DataBaseException("Wrong input data. Creator is not generated.");
                        }
                    } break;

                    case 9: {
                        startMenu();
                    } break;
                }
            } break;
            case 3: {  //3. Delete
                int creatorId = view.creatorInputId("Enter the id of the creator you want to delete: ");
                model.deleteCreator(creatorId);
            } break;
            case 4: { //4. Update
                try {
                    int creatorId = view.creatorInputId("Enter the creator id of the creator you want to change: ");
                    Creator creator = model.getCreator(creatorId);
                    if(creator != null) {
                        Creator newCreator = view.displayCreatorUpdateMenu(creator);
                        model.updateCreator(newCreator);
                    }
                } catch(NullPointerException e){
                    throw new DataBaseException("Wrong input data. Creator is not updated.");
                }
            } break;
        }
        return false;
    }

    public void End(){
        view.closeScanner();
        try {
            if (connection != null)
                connection.close();
        } catch(SQLException e){
            throw new DataBaseException("SQL error! Unexpected problems when leaving the local server.");
        }
        System.out.println("Session is ended. Thanks for using our program!");
    }
}
