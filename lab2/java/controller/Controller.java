package controller;

import java.util.ArrayList;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

import repository.impl.*;
import service.*;
import service.impl.*;
import view.View;
import util.Error;
import dto.*;
import entity.*;
import mapper.*;

public class Controller {
    private static final SessionFactory sessionFactory;
    private final UserService userService;
    private final CatalogueService catalogueService;
    private final MusicService musicService;
    private final CreatorServiceImpl creatorService;
    private final View view;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public Controller(){
        view = new View();
        Error error = new Error(sessionFactory);
        this.userService = new UserServiceImpl(new UserRepositoryImpl(sessionFactory), error);
        this.catalogueService = new CatalogueServiceImpl(new CatalogueRepositoryImpl(sessionFactory), error);
        this.musicService = new MusicServiceImpl(new MusicRepositoryImpl(sessionFactory), error);
        this.creatorService = new CreatorServiceImpl(new CreatorRepositoryImpl(sessionFactory), error);
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
                        ArrayList<UserDto> user = userService.getAllUsers();
                        view.displayUserShow(user);
                    } break;

                    case 2: {
                            UserDto user = userService.getUser(view.userInputId("Enter the user id of the user you want to view: "));
                            if(user != null)
                                user.show();
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
                            User user = view.displayUserCreateMenu();
                            userService.addUser(user);
                    } break;

                    case 2: {
                            view.displayGenerateDataMenu("user");
                            int amount = view.getUserNumber();
                            userService.randomGenerateUsers(amount);
                    } break;

                    case 9: {
                        startMenu();
                    } break;
                }
            } break;
            case 3: {  //3. Delete
                int userId = view.userInputId("Enter the id of the user you want to delete: ");
                userService.deleteUser(userId);
            } break;
            case 4: { //4. Update
                    UserMapper userMapper = new UserMapper();
                    int userId = view.userInputId("Enter the user id of the user you want to change: ");
                    User user = userMapper.toEntity(userService.getUser(userId));
                    if(user != null) {
                        User newUser = view.displayUserUpdateMenu(user);
                        userService.updateUser(newUser);
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
                        ArrayList<CatalogueDto> catalogues = catalogueService.getAllCatalogues();
                        view.displayCatalogueShow(catalogues);
                    }
                    break;
                    case 2: {
                            CatalogueDto catalogue = catalogueService.getCatalogue(view.catalogueInputId("Enter the catalogue id of the catalogue you want to view: "));
                            if(catalogue != null)
                                catalogue.show();
                    } break;

                    case 3:{
                       catalogueService.getBiggestCatalogue();
                    } break;

                    case 9: {
                        startMenu();
                    } break;
                }
            } break;

            case 2: { //Add
                    UserMapper userMapper = new UserMapper();
                    UserDto user = userService.getUser(view.userInputId("Enter the user id of the user to which this catalogue will apply: "));
                    Catalogue catalogue = view.displayCatalogueCreateMenu(userMapper.toEntity(user));
                    catalogueService.addCatalogue(catalogue);
            } break;

            case 3: {  //3. Delete
                int catalogueId = view.catalogueInputId("Enter the id of the catalogue you want to delete: ");
                catalogueService.deleteCatalogue(catalogueId);
            }  break;

            case 4: { //Upgrade
                    CatalogueMapper mapper = new CatalogueMapper();
                    int catalogueId = view.catalogueInputId("Enter the catalogue id of the catalogue you want to change: ");
                    CatalogueDto catalogue = catalogueService.getCatalogue(catalogueId);
                    if(catalogue != null) {
                        Catalogue newCatalogue = mapper.toEntity(view.displayCatalogueUpdateMenu(catalogue));
                        catalogueService.updateCatalogue(newCatalogue);
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
                        ArrayList<MusicDto> musics = musicService.getAllMusics();
                        view.displayMusicShow(musics);
                    }
                    break;
                    case 2: {
                            MusicDto music = musicService.getMusic(view.musicInputId("Enter the music id of the music you want to view: "));
                            if(music != null)
                                music.show();
                    } break;

                    case 3:{
                        musicService.getPopularMusic();
                    } break;

                    case 9: {
                        startMenu();
                    } break;
                }
            } break;

            case 2: { //Add
                    MusicMapper musicMapper = new MusicMapper();
                    CatalogueDto catalogue = catalogueService.getCatalogue(view.catalogueInputId("Enter the catalogue id of the catalogue to which this music will apply: "));
                    CreatorDto creator = creatorService.getCreator(view.creatorInputId("Enter the creator id of the creator to which this music will apply: "));
                    MusicDto music = view.displayMusicCreateMenu(catalogue, creator);
                    musicService.addMusic(musicMapper.toEntity(music));
            } break;

            case 3: {  //3. Delete
                int musicId = view.catalogueInputId("Enter the id of the music you want to delete: ");
                musicService.deleteMusic(musicId);
            }  break;

            case 4: { //Upgrade
                    MusicMapper musicMapper = new MusicMapper();
                    int musicId = view.musicInputId("Enter the music id of the music you want to change: ");
                    MusicDto music = musicService.getMusic(musicId);
                    if(music != null) {
                        Music newMusic = view.displayMusicUpdateMenu(musicMapper.toEntity(music));
                        musicService.updateMusic(newMusic);
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
                        ArrayList<CreatorDto> creator = creatorService.getAllCreators();
                        view.displayCreatorShow(creator);
                    } break;

                    case 2: {
                            CreatorDto creator = creatorService.getCreator(view.creatorInputId("Enter the creator id of the creator you want to view:"));
                            if(creator != null)
                                creator.show();
                    } break;

                    case 3: {
                        creatorService.getCreatorViews(view.creatorInputId("Enter the creator id of the creator you want to view:"));
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
                            Creator creator = view.displayCreatorCreateMenu();
                            creatorService.addCreator(creator);
                    } break;

                    case 2: {
                            view.displayGenerateDataMenu("creator");
                            int amount = view.getUserNumber();
                            creatorService.randomGenerateCreators(amount);
                    } break;

                    case 9: {
                        startMenu();
                    } break;
                }
            } break;
            case 3: {  //3. Delete
                creatorService.deleteCreator(view.creatorInputId("Enter the id of the creator you want to delete: "));
            } break;
            case 4: { //4. Update
                CreatorMapper creatorMapper = new CreatorMapper();
                    CreatorDto creator = creatorService.getCreator(view.creatorInputId("Enter the creator id of the creator you want to change: "));
                    if(creator != null) {
                        CreatorDto newCreator = view.displayCreatorUpdateMenu(creator);
                        creatorService.updateCreator(creatorMapper.toEntity(newCreator));
                    }
            } break;
        }
        return false;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private static void shutdown() {
        sessionFactory.close();
    }

    public void End(){
        view.closeScanner();
        shutdown();
        System.out.println("Session is ended. Thanks for using our program!");
    }
}
