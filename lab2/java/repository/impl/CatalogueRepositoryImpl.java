package repository.impl;

import entity.Catalogue;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import repository.CatalogueRepository;

import java.util.List;

public class CatalogueRepositoryImpl implements CatalogueRepository {
    private final SessionFactory sessionFactory;

    public CatalogueRepositoryImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }



    @Override
    public List<Catalogue> getAllCatalogues() {
        List<Catalogue> cataloguesList;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            cataloguesList = session.createQuery("from Catalogue order by catalogueId", Catalogue.class).getResultList();

            transaction.commit();
        } catch (DataException e) {
            if(transaction != null)
                transaction.rollback();
            throw new RuntimeException("Data conversion error converting");
        } catch (ConstraintViolationException e) {
            if(transaction != null)
                transaction.rollback();
            throw new RuntimeException("Duplicated saved catalogue \"duplicatedData\"");
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
            throw new RuntimeException("Error related to get all catalogues from \"catalogues\" table");
        }
        return cataloguesList;
    }

    @Override
    public Catalogue getCatalogue(int id) {
        Catalogue catalogue;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            catalogue = session.get(Catalogue.class, id);

            transaction.commit();
        } catch (DataException e) {
            if(transaction != null)
                transaction.rollback();
            throw new RuntimeException("Data conversion error converting");
        } catch (ConstraintViolationException e) {
            if(transaction != null)
                transaction.rollback();
            throw new RuntimeException("Duplicated saved catalogue \"duplicatedData\"");
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
            throw new RuntimeException("Error related to get catalogue from \"catalogues\" table");
        }
        return catalogue;
    }

    @Override
    public void addCatalogue(Catalogue catalogue) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.persist(catalogue);

            transaction.commit();
        } catch (DataException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Data conversion error converting");
        } catch (ConstraintViolationException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Duplicated saved catalogue \"duplicatedData\"");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Error related to update user into users table");
        }
    }

    @Override
    public void deleteCatalogue(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Catalogue catalogue = session.get(Catalogue.class, id);
            if (catalogue != null) {
                catalogue.setUser(null);
                session.remove(catalogue);
            }

            transaction.commit();
        } catch (DataException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Data conversion error converting");
        } catch (ConstraintViolationException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Duplicated saved catalogue \"duplicatedData\"");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Error related to delete catalogue from catalogues table");
        }
    }

    @Override
    public void updateCatalogue(Catalogue catalogue) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.update(catalogue);

            transaction.commit();
        } catch (DataException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Data conversion error converting");
        } catch (ConstraintViolationException e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Duplicated saved catalogue \"duplicatedData\"");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new RuntimeException("Error related to delete catalogue from catalogues table");
        }
    }

    @Override
    public void getBiggestCatalogue() {
        long startTime, endTime, duration;
        startTime = System.nanoTime();

        final String hql = """
            SELECT u.firstName, u.lastName, c.catalogueName, COUNT(m.musicId) AS musicCount
            FROM Catalogue c
            LEFT JOIN c.musics m
            INNER JOIN c.user u
            GROUP BY u.firstName, u.lastName, c.catalogueName
            ORDER BY musicCount DESC
            """;

        try (Session session = sessionFactory.openSession()) {
            List<Object[]> result = session.createQuery(hql, Object[].class)
                    .setMaxResults(1)
                    .getResultList();

            if (!result.isEmpty()) {
                Object[] row = result.getFirst();
                String firstName = (String) row[0];
                String lastName = (String) row[1];
                String catalogueName = (String) row[2];
                Long musicCount = (Long) row[3];

                System.out.printf("Owner: %s %s. Catalogue: %s. Total music: %d%n",
                        firstName, lastName, catalogueName, musicCount);
            } else {
                System.out.println("Error! Data does not exist.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Hibernate error while retrieving biggest catalogue", e);
        }

        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("Time spent: " + duration + " nanoseconds");
    }
}