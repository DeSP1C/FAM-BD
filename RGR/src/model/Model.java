package model;

import java.sql.*;
import java.util.ArrayList;
import SQLError.DataBaseException;
import entity.*;
import entity.additional.*;
import util.Error;
import validation.Validation;


public class Model {
    private final Connection connection;

    public Model(Connection connection){
        this.connection = connection;
    }

    public ArrayList<User> getAllUsers(){
        final String sql = "SELECT * FROM \"users\"\n" + "ORDER BY \"user_id\"";
        ArrayList<User> user = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                int id = resultSet.getInt("user_id");
                String email = resultSet.getString("email");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                User user1 = new User(id, email, firstName, lastName);
                user.add(user1);
            }
        } catch(SQLException e){
            throw new DataBaseException("SQL Error! Unexpected error related to users data retrieval");
        }

        return user;
    }

    public User getUser(int userId){
        if(!Validation.isID(userId)){
            System.out.println("Your ID entered incorrectly.");
            return null;
        }

        if(!Error.isUserIdTaken(connection, userId)){
            System.out.println("This user id is not exist.");
            return null;
        }

        final String sql = "SELECT \"email\", \"first_name\", \"last_name\" FROM \"users\" WHERE \"user_id\" = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                String email = resultSet.getString("email");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                return new User(userId, email, firstName, lastName);
            }
            System.out.println("Error! Table is empty!");
            return null;
        } catch(SQLException e){
            throw new DataBaseException("SQL Error! Unexpected error related to user data retrieval");
        }
    }

    public void addUser(User user) {
        if(user == null) {
            System.out.println("Error! You have not entered the data");
            return;
        }
        if(!Validation.isName(user.getFirstName())){
            System.out.println("Your name entered incorrectly.");
            return;
        }
        if(!Validation.isEmail(user.getEmail())){
            System.out.println("Your email entered incorrectly.");
            return;
        }
        if(!Validation.isID(user.getUserId())){
            System.out.println("Your ID entered incorrectly.");
            return;
        }

        if(Error.isUserIdTaken(connection, user.getUserId())){
            System.out.println("This user id is already taken.");
            return;
        }

        final String sql = "INSERT INTO \"users\"(user_id, email, first_name, last_name) VALUES (?,?,?,?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, user.getUserId());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());

            if(statement.executeUpdate() == 0)
                System.out.println("Error! The \"users\" table has not changed.");
            else System.out.println("User successfully added!");
        } catch(SQLException e){
            throw new DataBaseException("SQL Error! Unexpected error related to adding a user");
        }
    }

    public void deleteUser(int userId){
        if(!Validation.isID(userId)){
            System.out.println("User ID is not entered correctly or this ID does not exist.");
            return;
        }
        if(!Error.isUserIdTaken(connection, userId)){
            System.out.println("This user id is not exist.");
            return;
        }

        final String sql = "DELETE FROM \"users\" WHERE \"user_id\" = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, userId);
            if(statement.executeUpdate() == 0)
                System.out.println("Error! Table is empty!");
            else System.out.println("User successfully deleted!");
        } catch(SQLException e){
            throw new DataBaseException("SQL Error! Unexpected error related to user deletion");
        }
    }

    public void updateUser(User user){
        if(user == null) {
            System.out.println("Error! You have not entered the data");
            return;
        }

        if(!Validation.isID(user.getUserId())){
            System.out.println("User ID entered incorrectly.");
            return;
        }

        if(!Validation.isName(user.getFirstName())){
            System.out.println("Your name entered incorrectly.");
            return;
        }

        if(!Validation.isEmail(user.getEmail())){
            System.out.println("Your email entered incorrectly.");
            return;
        }

        if(!Error.isUserIdTaken(connection, user.getUserId())){
            System.out.println("This user id is not exist.");
            return;
        }

        final String sql = "UPDATE \"users\" SET \"email\"= ?, \"first_name\"= ?, \"last_name\" = ? WHERE \"user_id\"= ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
                statement.setInt(4, user.getUserId());
                statement.setString(1, user.getEmail());
                statement.setString(2, user.getFirstName());
                statement.setString(3, user.getLastName());

            if(statement.executeUpdate() == 0)
                System.out.println("Error! Table is empty!");

            else System.out.println("User successfully updated!");
        } catch(SQLException e){
            throw new DataBaseException("SQL Error! Unexpected error related to user data change");
        }
    }

    public ArrayList<Catalogue> getAllCatalogue(){
        ArrayList<Catalogue> catalogue = new ArrayList<>();
        final String sql = "SELECT * FROM catalogues\n" + "ORDER BY \"catalogue_id\"";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                int catalogueId = resultSet.getInt("catalogue_id");
                String name = resultSet.getString("catalogue_name");
                int userId = resultSet.getInt("user_id");

                Catalogue catalogue1 = new Catalogue(catalogueId, name, userId);
                catalogue.add(catalogue1);
            }
            return catalogue;
        } catch(SQLException e){
            throw new DataBaseException("SQL Error! Unexpected error related to catalogues data retrieval");
        }
    }

    public Catalogue getCatalogue(int catalogueId){
        if(!Validation.isID(catalogueId)){
            System.out.println("Catalogue ID entered incorrectly.");
            return null;
        }

        if(!Error.isCatalogueIdTaken(connection, catalogueId)){
            System.out.println("This catalogue id is not exist.");
            return null;
        }

        final String sql = "SELECT \"catalogue_name\", \"user_id\" FROM \"catalogues\" WHERE \"catalogue_id\" = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, catalogueId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int userId = resultSet.getInt("user_id");
                String catalogueName = resultSet.getString("catalogue_name");

                return new Catalogue(catalogueId, catalogueName, userId);
            }

            System.out.println("Error! Table is empty!");
            return null;
        } catch(SQLException e){
            throw new DataBaseException(" SQL Error! Unexpected error related to catalogue data retrieval");
        }
    }
    public void addCatalogue(Catalogue catalogue){
        if(catalogue == null) {
            System.out.println("Error! You have not entered the data");
            return;
        }
        if(!Validation.isName(catalogue.getCatalogueName())){
            System.out.println("Catalogue name entered incorrectly.");
            return;
        }
        if(!Validation.isID(catalogue.getCatalogueId())){
            System.out.println("Catalogue ID entered incorrectly.");
            return;
        }
        if(!Validation.isID(catalogue.getUserId())){
            System.out.println("User ID entered incorrectly.");
            return;
        }

        if(Error.isCatalogueIdTaken(connection, catalogue.getCatalogueId())){
            System.out.println("This catalogue id is already taken.");
            return;
        }

        if(!Error.isUserIdTaken(connection, catalogue.getUserId())){
            System.out.println("This user id is not exist.");
            return;
        }

        final String sql = "INSERT INTO \"catalogues\"(catalogue_id, catalogue_name, user_id) VALUES (?,?,?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, catalogue.getCatalogueId());
            statement.setString(2, catalogue.getCatalogueName());
            statement.setInt(3, catalogue.getUserId());

            if(statement.executeUpdate() == 0) {
                System.out.println("Error! The \"catalogue\" table has not changed.");
            }
            else System.out.println("Catalogue successfully added!");
        } catch(SQLException e){
            throw new DataBaseException("SQL Error! Unexpected error related to adding a catalogue");
        }
    }
    public void deleteCatalogue(int catalogueId){
        if(!Validation.isID(catalogueId)){
            System.out.println("Catalogue ID entered incorrectly.");
            return;
        }

        if(!Error.isCatalogueIdTaken(connection, catalogueId)){
            System.out.println("This catalogue id is not exist.");
            return;
        }

        final String sql = "DELETE FROM \"catalogues\" WHERE \"catalogue_id\" = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, catalogueId);
            if(statement.executeUpdate() == 0)
                System.out.println("Error! Table is empty!");
            else System.out.println("Catalogue successfully deleted!");
        } catch(SQLException e){
            throw new DataBaseException("SQL Error! Unexpected error related to catalogue deletion");
        }
    }

    public void updateCatalogue(Catalogue catalogue){
        if(catalogue == null) {
            System.out.println("Error! You have not entered the data");
            return;
        }
        if(!Validation.isName(catalogue.getCatalogueName())){
            System.out.println("Catalogue name entered incorrectly.");
            return;
        }
        if(!Validation.isID(catalogue.getCatalogueId())){
            System.out.println("Catalogue ID entered incorrectly.");
            return;
        }
        if(!Validation.isID(catalogue.getUserId())){
            System.out.println("User ID entered incorrectly.");
            return;
        }

        if(!Error.isCatalogueIdTaken(connection, catalogue.getCatalogueId())){
            System.out.println("This catalogue id is not exist.");
            return;
        }

        final String sql = "UPDATE \"catalogues\" SET \"catalogue_name\"= ?, \"user_id\" = ? WHERE \"catalogue_id\"= ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(3, catalogue.getCatalogueId());
            statement.setString(1, catalogue.getCatalogueName());
            statement.setInt(2, catalogue.getUserId());

            if(statement.executeUpdate() == 0) {
                System.out.println("Error! The \"catalogues\" table has not changed.");
            }
            else System.out.println("Catalogue successfully updated!");
        } catch(SQLException e){
            throw new DataBaseException("SQL Error! Unexpected error related to accessing data in a table");
        }
    }

    public ArrayList<Music> getAllMusic(){
        final String sql = "SELECT * FROM \"musics\"\n " + "ORDER BY \"music_id\"";
        ArrayList<Music> music = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                int musicId = resultSet.getInt("music_id");
                String musicName = resultSet.getString("music_name");
                int creatorId = resultSet.getInt("creator_id");
                int catalogueId = resultSet.getInt("catalogue_id");
                int views = resultSet.getInt("views");

                Music music1 = new Music(musicId, musicName, creatorId, catalogueId, views);
                music.add(music1);
            }

            return music;
        } catch(SQLException e) {
            throw new DataBaseException("QL Error! Unexpected error related to musics data retrieval");
        }
    }
    public Music getMusic(int musicId){
        if(!Validation.isID(musicId)){
            System.out.println("Music ID entered incorrectly.");
            return null;
        }

        if(!Error.isMusicIdTaken(connection, musicId)){
            System.out.println("This music id is not exist.");
            return null;
        }

        final String sql = "SELECT \"music_name\", \"creator_id\", \"catalogue_id\", \"views\" FROM \"musics\" WHERE \"music_id\" = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, musicId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                String musicName = resultSet.getString("music_name");
                int creatorId = resultSet.getInt("creator_id");
                int catalogueId = resultSet.getInt("catalogue_id");
                int views = resultSet.getInt("views");
                return new Music(musicId, musicName, creatorId, catalogueId, views);
            }
            System.out.println("Error! Table is empty!");
            return null;
        } catch(SQLException e){
            throw new DataBaseException("SQL Error! Unexpected error related to music data retrieval");
        }
    }
    public void addMusic(Music music){
        if(music == null) {
            System.out.println("Error! You have not entered the data");
            return;
        }
        if(!Validation.isName(music.getMusicName())){
            System.out.println("Music name entered incorrectly.");
            return;
        }
        if(!Validation.isID(music.getMusicId())){
            System.out.println("Music ID entered incorrectly.");
            return;
        }

        if(!Validation.isID(music.getCatalogueId())){
            System.out.println("Catalogue ID entered incorrectly.");
            return;
        }

        if(!Validation.isID(music.getCreatorId())){
            System.out.println("Creator ID entered incorrectly.");
            return;
        }

        if(!Validation.isView(music.getViews())){
            System.out.println("Views entered incorrectly.");
            return;
        }

        if(Error.isMusicIdTaken(connection, music.getMusicId())){
            System.out.println("This music id is already taken.");
            return;
        }

        final String sql = "INSERT INTO \"musics\"(music_id, music_name, creator_id, catalogue_id, views) VALUES (?,?,?,?,?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, music.getMusicId());
            statement.setString(2, music.getMusicName());
            statement.setInt(3, music.getCreatorId());
            statement.setInt(4, music.getCatalogueId());
            statement.setInt(5, music.getViews());

            if(statement.executeUpdate() == 0) {
                System.out.println("Error! The \"musics\" table has not changed.");
            }
            else System.out.println("Music successfully added!");
        } catch(SQLException e){
            throw new DataBaseException("SQL Error! Unexpected error related to adding a music");
        }
    }
    public void deleteMusic(int musicId){
        if(!Validation.isID(musicId)){
            System.out.println("Music ID entered incorrectly.");
            return;
        }

        if(!Error.isMusicIdTaken(connection, musicId)){
            System.out.println("This music id is not exist.");
            return;
        }

        final String sql = "DELETE FROM \"musics\" WHERE \"music_id\" = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, musicId);
            if(statement.executeUpdate() == 0) {
                System.out.println("Error! Table is empty!");
            }
            else System.out.println("Music successfully deleted!");
        } catch(SQLException e){
            throw new DataBaseException("SQL Error! Unexpected error related to music deletion");
        }
    }
    public void updateMusic(Music music){
        if(music == null) {
            System.out.println("Error! You have not entered the data");
            return;
        }

        if(!Validation.isID(music.getMusicId())){
            System.out.println("Music ID entered incorrectly.");
            return;
        }

        if(!Validation.isName(music.getMusicName())){
            System.out.println("Music name entered incorrectly.");
            return;
        }

        if(!Validation.isID(music.getCatalogueId())){
            System.out.println("Catalogue ID entered incorrectly.");
            return;
        }

        if(!Validation.isID(music.getCreatorId())){
            System.out.println("Creator ID entered incorrectly.");
            return;
        }

        if(!Validation.isView(music.getViews())){
            System.out.println("Views entered incorrectly.");
        }

        if(!Error.isMusicIdTaken(connection, music.getMusicId())){
            System.out.println("This music id is not exist.");
            return;
        }

        final String sql = "UPDATE \"musics\" SET \"music_name\"= ?, \"creator_id\"= ?, \"catalogue_id\"= ?, " +
                "\"views\" = ? WHERE \"music_id\"= ?\n";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(5, music.getMusicId());
            statement.setString(1, music.getMusicName());
            statement.setInt(2, music.getCreatorId());
            statement.setInt(3, music.getCatalogueId());
            statement.setInt(4, music.getViews());

            if(statement.executeUpdate() == 0) {
                System.out.println("Error! The \"musics\" table has not changed.");
            }
            else System.out.println("Music successfully updated!");
        } catch(SQLException e){
            throw new DataBaseException(" SQL Error! Unexpected error related to music data change");
        }
    }

    public ArrayList<Creator> getAllCreator(){
        final String sql = "SELECT * FROM \"creators\"\n" + "ORDER BY \"creator_id\"";
        ArrayList<Creator> creators = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                int creatorId = resultSet.getInt("creator_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                creators.add(new Creator(creatorId, firstName, lastName));
            }
            return creators;
        } catch(SQLException e){
            throw new DataBaseException("SQL Error! Unexpected error related to creators data retrieval");
        }
    }
    public Creator getCreator(int creatorId){
        if(!Validation.isID(creatorId)){
            System.out.println("Creator ID entered incorrectly.");
            return null;
        }

        if(!Error.isCreatorIdTaken(connection, creatorId)){
            System.out.println("This creator id is not exist.");
            return null;
        }

        final String sql = "SELECT \"first_name\",  \"last_name\" FROM \"creators\" WHERE \"creator_id\"= ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, creatorId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                return new Creator(creatorId, firstName, lastName);
            }
            System.out.println("Error! Table is empty!");
            return null;
        } catch(SQLException e){
            throw new DataBaseException("SQL Error! Unexpected error related to creator data retrieval");
        }
    }

    public void addCreator(Creator creator){
        if(creator == null) {
            System.out.println("Error! You have not entered the data");
            return;
        }
        if(!Validation.isName(creator.getFirstName())){
            System.out.println("Creator first name entered incorrectly.");
            return;
        }
        if(!Validation.isName(creator.getLastName())){
            System.out.println("Creator last name entered incorrectly.");
            return;
        }
        if(!Validation.isID(creator.getCreatorId())){
            System.out.println("Creator ID entered incorrectly.");
            return;
        }

        if(Error.isCreatorIdTaken(connection, creator.getCreatorId())){
            System.out.println("This creator id is already taken.");
            return;
        }

        final String sql = "INSERT INTO \"creators\"(creator_id, first_name, last_name) VALUES(?,?,?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, creator.getCreatorId());
            statement.setString(2, creator.getFirstName());
            statement.setString(3, creator.getLastName());
            if(statement.executeUpdate() == 0) {
                System.out.println("Error! The \"users\" table has not changed.");
            }
            else System.out.println("Creator successfully added!");
        } catch(SQLException e){
            throw new DataBaseException("SQL Error! Unexpected error related to adding a creator");
        }
    }

    public void deleteCreator(int creatorId){
        if(!Validation.isID(creatorId)){
            System.out.println("Creator ID entered incorrectly.");
            return;
        }

        if(!Error.isCreatorIdTaken(connection, creatorId)){
            System.out.println("This creator id is not exist.");
            return;
        }

        final String sql = "DELETE FROM \"creators\" WHERE \"creator_id\"= ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, creatorId);
            if(statement.executeUpdate() == 0) {
                System.out.println("Error! Table is empty!");
            }
            else System.out.println("Creator successfully deleted!");
        } catch(SQLException e){
            throw new DataBaseException("SQL Error! Unexpected error related to creator deletion");
        }
    }
    public void updateCreator(Creator creator){
        if(creator == null) {
            System.out.println("Error! You have not entered the data");
            return;
        }
        if(!Validation.isName(creator.getFirstName())){
            System.out.println("Creator first name entered incorrectly.");
            return;
        }
        if(!Validation.isName(creator.getLastName())){
            System.out.println("Creator last name entered incorrectly.");
            return;
        }
        if(!Validation.isID(creator.getCreatorId())){
            System.out.println("Creator ID entered incorrectly.");
            return;
        }

        if(!Error.isCreatorIdTaken(connection, creator.getCreatorId())){
            System.out.println("This creator id is not exist.");
            return;
        }

        final String sql = "UPDATE \"creators\" SET \"first_name\"= ?, \"last_name\"= ? WHERE \"creator_id\"= ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(3, creator.getCreatorId());
            statement.setString(1, creator.getFirstName());
            statement.setString(2, creator.getLastName());

            if(statement.executeUpdate() == 0) {
                System.out.println("Error! The \"creators\" table has not changed.");
            }
            else System.out.println("Creator successfully updated!");
        } catch(SQLException e){
            throw new DataBaseException("SQL Error! Unexpected error related to creator data change");
        }
    }

    public void randomGenerateUser(int recordCount) {
        String generateSql = """
            SELECT "first_name", "last_name",
                   LOWER("first_name" || '.' || "last_name") || '@gmail.com' as "email"
            FROM unnest(array['Max', 'Ann', 'John', 'Ava', 'Bob', 'Tomas', 'Mia', 'Richard',
            'Freya', 'Florence', 'Oliver', 'Noah', 'Laura', 'Mike', 'Johan', 'James',
             'Arthur', 'Leo', 'Harry', 'Oscar', 'Harry', 'Amelia', 'Lily', 'Stephen']) AS "first_name"
            CROSS JOIN unnest(array['Lebron', 'Nelson', 'Smith', 'Jones', 'Williams', 'Brown', 'Taylor',
            'Davies', 'Evans', 'Williams', 'Stone', 'Bell', 'Campbell', 'Morgan', 'Lewis', 'Roberts',
            'Evans', 'Adams', 'Gibson', 'Peters', 'Shelby', 'Harry', 'Carry', 'Davis']) AS "last_name"
            ORDER BY random()
            LIMIT ?;
            """;
        String maxIdSql = "SELECT COALESCE(MAX(user_id), 0) FROM users";
        String insertSql = "INSERT INTO \"users\" (\"user_id\", \"first_name\", \"last_name\", \"email\") VALUES (?, ?, ?, ?)";

        int startingId = 0;
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(maxIdSql)) {
            if (rs.next()) {
                startingId = rs.getInt(1);
            }
        }
        catch(SQLException e){
            throw new DataBaseException("SQL error! Your number: " + recordCount + " is not allowed.");
        }

        try (PreparedStatement generatedStatement = connection.prepareStatement(generateSql);
             PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {

            generatedStatement.setInt(1, recordCount);

            ResultSet rs = generatedStatement.executeQuery();

            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");

                insertStatement.setInt(1, ++startingId);
                insertStatement.setString(2, firstName);
                insertStatement.setString(3, lastName);
                insertStatement.setString(4, email);

                insertStatement.executeUpdate();
            }
            System.out.println(recordCount + " records inserted successfully.");
        } catch (SQLException e) {
            throw new DataBaseException("SQL error! Unexpected error related to data generation for users table");
        }
    }
    public void randomGenerateCreator(int recordCount){
        String generateSql = """
            SELECT "first_name", "last_name"
            FROM unnest(array['Max', 'Ann', 'John', 'Ava', 'Bob', 'Tomas', 'Mia', 'Richard',
            'Freya', 'Florence', 'Oliver', 'Noah', 'Laura', 'Mike', 'Johan', 'James',
             'Arthur', 'Leo', 'Harry', 'Oscar', 'Harry', 'Amelia', 'Lily', 'Stephen']) AS "first_name"
            CROSS JOIN unnest(array['Lebron', 'Nelson', 'Smith', 'Jones', 'Williams', 'Brown', 'Taylor',
            'Davies', 'Evans', 'Williams', 'Stone', 'Bell', 'Campbell', 'Morgan', 'Lewis', 'Roberts',
            'Evans', 'Adams', 'Gibson', 'Peters', 'Shelby', 'Harry', 'Carry', 'Davis']) AS "last_name"
            ORDER BY random()
            LIMIT ?;
            """;
        String maxIdSql = "SELECT COALESCE(MAX(creator_id), 0) FROM creators";
        String insertSql = "INSERT INTO \"creators\" (\"creator_id\", \"first_name\", \"last_name\") VALUES (?, ?, ?)";

        int startingId = 0;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(maxIdSql)) {
            if (rs.next()) {
                startingId = rs.getInt(1);
            }
        }
        catch(SQLException e){
            throw new DataBaseException("SQL error! Your number: " + recordCount + " is not allowed.");
        }

        try (PreparedStatement generatedStatement = connection.prepareStatement(generateSql);
             PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {

            generatedStatement.setInt(1, recordCount);

            ResultSet resultSet = generatedStatement.executeQuery();

            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                insertStatement.setInt(1, ++startingId);
                insertStatement.setString(2, firstName);
                insertStatement.setString(3, lastName);

                insertStatement.executeUpdate();
            }
            System.out.println(recordCount + " records inserted successfully.");
        } catch (SQLException e) {
            throw new DataBaseException("SQL error!");
        }
    }

    public void getCreatorViews(int creatorId){
        if(!Validation.isID(creatorId)){
            System.out.println("Creator ID entered incorrectly.");
            return;
        }

        if(!Error.isCreatorIdTaken(connection, creatorId)){
            System.out.println("This creator id is not exist.");
            return;
        }

        long startTime, endTime, duration;
        startTime = System.nanoTime();
        ArrayList<CreatorStats> creatorStats = new ArrayList<>();
        final String sql = """
                SELECT c."first_name", c."last_name", SUM(m."views") AS total_views
                FROM public."musics" m
                INNER JOIN public."creators" c ON c."creator_id" = m."creator_id"
                WHERE c."creator_id" =?
                GROUP BY c."first_name", c."last_name"
                ORDER BY total_views DESC
                """;
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, creatorId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                String name = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int views = resultSet.getInt("total_views");

                creatorStats.add(new CreatorStats(name, lastName, views));
            }
        }catch(SQLException e){
            throw new DataBaseException("SQL Error! Unexpected error related to data generation for creators table");
        }

        for(CreatorStats cs : creatorStats) cs.show();

        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("Time spent: " + (duration) + " nanoseconds");
    }

    public void getBiggestCatalogue(){
        long startTime, endTime, duration;
        startTime = System.nanoTime();
        BiggestCatalogue catalogue;
        final String sql = """
                SELECT u."first_name", u."last_name", c."catalogue_name", COUNT(m."music_id") AS music_count
                FROM "catalogues" c
                LEFT JOIN "musics" m ON c."catalogue_id" = m."catalogue_id"
                INNER JOIN "users" u ON u."user_id" = c."user_id"
                GROUP BY u."first_name", u."last_name", c."catalogue_name"
                ORDER BY music_count DESC
                LIMIT 1
                """;
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String catalogueName = resultSet.getString("catalogue_name");
                int musicCount = resultSet.getInt("music_count");

                catalogue = new BiggestCatalogue(firstName, lastName, catalogueName, musicCount);
            }
            else{
                System.out.println("Error! Data is not exist.");
                return;
            }
        }catch(SQLException e){
            throw new DataBaseException("SQL Error! Unexpected error related to catalogue data retrieval");
        }
        catalogue.show();
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("Time spent: " + (duration) + " nanoseconds");
}

    public void getPopularMusic(){
        long startTime, endTime, duration;
        startTime = System.nanoTime();

        ArrayList<PopularMusic> musics = new ArrayList<>();
        final String sql = """
                SELECT c."first_name", c."last_name", m."music_name", m."views"
                FROM "musics" m
                LEFT JOIN "creators" c ON c."creator_id" = m."creator_id"
                GROUP BY c."first_name", c."last_name", m."music_name", m."views"
                ORDER BY m."views" DESC
                LIMIT 3
                """;
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String musicName = resultSet.getString("music_name");
                int views = resultSet.getInt("views");

                PopularMusic music = new PopularMusic(firstName, lastName, musicName, views);
                musics.add(music);
            }
                if(!musics.isEmpty())
                for(PopularMusic music : musics) music.show();
                else System.out.println("Error! Table is empty!\n");

        }catch(SQLException e){
            throw new DataBaseException("SQL Error! Unexpected error related to receive data from musics and/or creator.");
        }
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("Time spent: " + (duration) + " nanoseconds");

    }
}
