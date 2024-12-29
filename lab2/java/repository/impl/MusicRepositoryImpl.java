package repository.impl;
import entity.Music;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import repository.MusicRepository;

import java.util.List;

public class MusicRepositoryImpl implements MusicRepository{
    private final SessionFactory sessionFactory;

    public MusicRepositoryImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Music> getAllMusics(){
        List<Music> musicList;
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            musicList = session.createQuery("from Music order by musicId", Music.class).getResultList();

            transaction.commit();
        } catch (DataException e) {
            if(transaction != null)
                transaction.rollback();
            throw new RuntimeException("Data conversion error converting");
        } catch (ConstraintViolationException e) {
            if(transaction != null)
                transaction.rollback();
            throw new RuntimeException("Duplicated saved music \"duplicatedData\"");
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
            throw new RuntimeException("Error related to get all musics from \"musics\" table");
        }
        return musicList;
    }

    @Override
    public Music getMusic(int id){
        Music music;
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            music = session.get(Music.class, id);

            transaction.commit();
        } catch (DataException e) {
            if(transaction != null)
                transaction.rollback();
            throw new RuntimeException("Data conversion error converting");
        } catch (ConstraintViolationException e) {
            if(transaction != null)
                transaction.rollback();
            throw new RuntimeException("Duplicated saved music \"duplicatedData\"");
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
            throw new RuntimeException("Error related to get music from \"musics\" table");
        }
        return music;
    }

    @Override
    public void addMusic(Music music){
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            session.persist(music);

            transaction.commit();
        } catch (DataException e) {
            if(transaction != null)
                transaction.rollback();
            throw new RuntimeException("Data conversion error converting");
        } catch (ConstraintViolationException e) {
            if(transaction != null)
                transaction.rollback();
            throw new RuntimeException("Duplicated saved music \"duplicatedData\"");
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
            throw new RuntimeException("Error related to add music to \"musics\" table");
        }
    }

    @Override
    public void deleteMusic(int id){
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            Music music = session.get(Music.class, id);
            if(music != null) {
                music.setCatalogue(null);
                music.setCreator(null);
                session.remove(music);
            }
            transaction.commit();
        } catch (DataException e) {
            if(transaction != null)
                transaction.rollback();
            throw new RuntimeException("Data conversion error converting");
        } catch (ConstraintViolationException e) {
            if(transaction != null)
                transaction.rollback();
            throw new RuntimeException("Duplicated saved music \"duplicatedData\"");
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
            throw new RuntimeException("Error related to delete music from \"musics\" table");
        }
    }

    @Override
    public void updateMusic(Music music){
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            session.update(music);

            transaction.commit();
        } catch (DataException e) {
            if(transaction != null)
                transaction.rollback();
            throw new RuntimeException("Data conversion error converting");
        } catch (ConstraintViolationException e) {
            if(transaction != null)
                transaction.rollback();
            throw new RuntimeException("Duplicated saved music \"duplicatedData\"");
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
            throw new RuntimeException("Error related to update music in the \"musics\" table");
        }
    }

    @Override
    public void getPopularMusic() {
        long startTime, endTime, duration;
        startTime = System.nanoTime();

        final String hql = """
            SELECT c.firstName, c.lastName, m.musicName, m.views
            FROM Music m
            LEFT JOIN m.creator c
            GROUP BY c.firstName, c.lastName, m.musicName, m.views
            ORDER BY m.views DESC
            """;

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            List<Object[]> results = session.createQuery(hql, Object[].class)
                    .setMaxResults(3) // Обмеження до 3 найпопулярніших треків
                    .getResultList();

            if (!results.isEmpty()) {
                for (Object[] row : results) {
                    String firstName = (String) row[0];
                    String lastName = (String) row[1];
                    String musicName = (String) row[2];
                    Integer views = (Integer) row[3];

                    System.out.printf("Owner: %s %s. Music: %s. Views: %d%n", firstName, lastName, musicName, views);
                }
            } else {
                System.out.println("Error! Table is empty!\n");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Hibernate error while retrieving popular music", e);
        }

        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("Time spent: " + duration + " nanoseconds");
    }
}
