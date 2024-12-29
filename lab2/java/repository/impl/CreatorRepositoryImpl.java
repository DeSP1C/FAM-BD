package repository.impl;

import entity.Creator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import repository.CreatorRepository;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class CreatorRepositoryImpl implements CreatorRepository {
    private final SessionFactory sessionFactory;

    public CreatorRepositoryImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Creator> getAllCreators(){
        List<Creator> creatorList;
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            creatorList = session.createQuery("from Creator order by creatorId", Creator.class).getResultList();

            transaction.commit();
        } catch (DataException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Data conversion error converting");
        } catch (ConstraintViolationException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Duplicated saved creator \"duplicatedData\"");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Error related to get all creators from \"creators\" table");
        }
        return creatorList;
    }

    @Override
    public Creator getCreator(int id) {
        Creator creator;
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            creator = session.get(Creator.class, id);

            transaction.commit();
        } catch (DataException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Data conversion error converting");
        } catch (ConstraintViolationException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Duplicated saved creator \"duplicatedData\"");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Error related to get creator from \"creators\" table");
        }
        return creator;
    }

    @Override
    public void addCreator(Creator creator){
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            session.persist(creator);

            transaction.commit();
        } catch (DataException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Data conversion error converting");
        } catch (ConstraintViolationException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Duplicated saved creator \"duplicatedData\"");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Error related to add creator to \"creators\" table");
        }
    }

    @Override
    public void deleteCreator(int id){
        Transaction transaction = null;

        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            Creator creator = session.get(Creator.class, id);
            session.remove(creator);

            transaction.commit();
        } catch (DataException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Data conversion error converting");
        } catch (ConstraintViolationException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Duplicated saved creator \"duplicatedData\"");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Error related to delete creator from \"creators\" table");
        }
    }

    @Override
    public void updateCreator(Creator creator){
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            session.update(creator);

            transaction.commit();
        } catch (DataException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Data conversion error converting");
        } catch (ConstraintViolationException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Duplicated saved creator \"duplicatedData\"");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Error related to update creator in the \"creators\" table");
        }
    }

    @Override
    public void randomGenerateCreators(int recordCount){
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
            startingId = session.createQuery("SELECT COALESCE(MAX(c.creatorId), 0) FROM Creator c", Integer.class)
                    .uniqueResult();
        }

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            IntStream.range(1, recordCount + 1).forEach(i -> {
                int generatedId = startingId + i;
                String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
                String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];

                Creator creator = new Creator(generatedId, firstName, lastName);
                session.persist(creator);
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
            throw new RuntimeException("Duplicated saved creator \"duplicatedData\"");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Error related to add creators to \"creators\" table");
        }
    }

    @Override
    public void getCreatorViews(int id) {
        long startTime, endTime, duration;
        startTime = System.nanoTime();

        final String hql = """
            SELECT c.firstName, c.lastName, SUM(m.views)
            FROM Music m
            JOIN m.creator c
            WHERE c.creatorId = :creatorId
            GROUP BY c.firstName, c.lastName
            ORDER BY SUM(m.views) DESC
            """;

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            List<Object[]> results = session.createQuery(hql, Object[].class)
                    .setParameter("creatorId", id)
                    .getResultList();

            for (Object[] row : results) {
                String firstName = (String) row[0];
                String lastName = (String) row[1];
                Long totalViews = (Long) row[2];

                System.out.printf("Creator: %s %s. Total views: %d%n", firstName, lastName, totalViews);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Hibernate error while retrieving creator statistics", e);
        }

        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("Time spent: " + duration + " nanoseconds");
    }
}
