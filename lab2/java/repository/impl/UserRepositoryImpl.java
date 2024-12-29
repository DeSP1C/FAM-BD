package repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import entity.User;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {
    private final SessionFactory sessionFactory;

    public UserRepositoryImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> getAllUsers(){
        List<User> usersList;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            usersList = session.createQuery("from User order by userId", User.class).getResultList();

            transaction.commit();
        } catch (DataException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Data conversion error converting");
        } catch (ConstraintViolationException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Duplicated saved user \"duplicatedData\"");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Error related to get all users from \"users\" table");
        }

        return usersList;
    }

    @Override
    public User getUser(int id){
        User user;
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            user = session.get(User.class, id);

            transaction.commit();
        } catch (DataException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Data conversion error converting");
        } catch (ConstraintViolationException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Duplicated saved user \"duplicatedData\"");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Error related to get user from \"users\" table");
        }

        return user;
    }

    @Override
    public void addUser(User user){
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            session.persist(user);

            transaction.commit();
        }catch (DataException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Data conversion error converting");
        } catch (ConstraintViolationException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Duplicated saved user \"duplicatedData\"");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Error related to add user to \"users\" table");
        }
    }

    @Override
    public void deleteUser(int id){
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            session.remove(user);

            transaction.commit();
        } catch (DataException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Data conversion error converting");
        } catch (ConstraintViolationException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Duplicated saved user \"duplicatedData\"");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Error related to delete user from \"users\" table");
        }
    }

    @Override
    public void updateUser(User user){
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            session.update(user); //deprecated but work :D

            transaction.commit();
        } catch (DataException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Data conversion error converting");
        } catch (ConstraintViolationException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Duplicated saved user \"duplicatedData\"");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Error related to update user in the \"users\" table: ");
        }
    }

    @Override
    public void randomGenerateUsers(int recordCount){
        final String[] FIRST_NAMES = {
                "Max", "Ann", "John", "Ava", "Bob", "Tomas", "Mia", "Richard",
                "Freya", "Florence", "Oliver", "Noah", "Laura", "Mike", "Johan", "James",
                "Arthur", "Leo", "Harry", "Oscar", "Amelia", "Lily", "Stephen"
        };

        final String[] LAST_NAMES = {
                "Lebron", "Nelson", "Smith", "Jones", "Williams", "Brown", "Taylor",
                "Davies", "Evans", "Williams", "Stone", "Bell", "Campbell", "Morgan",
                "Lewis", "Roberts", "Adams", "Gibson", "Peters", "Shelby", "Davis"
        };

        Random random = new Random();

        int startingId;
        try (Session session = sessionFactory.openSession()) {
            startingId = session.createQuery("SELECT COALESCE(MAX(u.userId), 0) FROM User u", Integer.class) //remove Integer.class. add on begin (int).
                    .uniqueResult();
        }

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            IntStream.range(1, recordCount + 1).forEach(i -> {
                int generatedId = startingId + i;
                String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
                String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
                String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + random.nextInt(1000) + "@gmail.com";

                User user = new User(generatedId, email, firstName, lastName);
                session.persist(user);
            });

            transaction.commit();
            System.out.println(recordCount + " records inserted successfully.");
        } catch (DataException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Data conversion error converting");
        } catch (ConstraintViolationException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Duplicated saved user \"duplicatedData\"");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Error related to add users to \"users\" table");
        }
    }
}
