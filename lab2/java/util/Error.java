package util;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Session;

public class Error {

    private final SessionFactory sessionFactory;

    public Error(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public boolean isUserIdExists(int userId) {
        Transaction transaction = null;
        boolean exists;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            String hql = "select count(u) from User u where u.userId = :userId";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("userId", userId)
                    .uniqueResult();

            exists = count != null && count > 0;

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new IllegalStateException("Hibernate error while checking if userId exists", e);
        }

        return exists;
    }

    public boolean isCatalogueIdExists(int catalogueId) {
        Transaction transaction = null;
        boolean exists;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            String hql = "select count(c) from Catalogue c where c.catalogueId = :catalogueId";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("catalogueId", catalogueId)
                    .uniqueResult();

            exists = count != null && count > 0;

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new IllegalStateException("Hibernate error while checking if userId exists", e);
        }

        return exists;
    }

    public boolean isMusicIdExists(int musicId) {
        Transaction transaction = null;
        boolean exists;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            String hql = "select count(m) from Music m where m.musicId = :musicId";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("musicId", musicId)
                    .uniqueResult();

            exists = count != null && count > 0;

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new IllegalStateException("Hibernate error while checking if userId exists", e);
        }

        return exists;
    }

    public boolean isCreatorIdExists(int creatorId) {
        Transaction transaction = null;
        boolean exists;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            String hql = "select count(c) from Creator c where c.creatorId = :creatorId";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("creatorId", creatorId)
                    .uniqueResult();

            exists = count != null && count > 0;

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            throw new IllegalStateException("Hibernate error while checking if userId exists", e);
        }

        return exists;
    }
}
