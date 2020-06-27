package bank.model;

import hibernate.CloseableSession;
import hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * SessionFactory sf = HibernateUtil.getSessionFactory(); Session session =
 * sf.openSession();
 *
 * C -> session.save(objekatKojiSNimamo) R-> session.get(Class obj,
 * primarniKljuc); U -> session.update(objekatKojiSnimamo); D ->
 * session.delete(objekatKojiSnimamo);
 *
 * <p>
 * JPA + ORM tool(Hibernate, EclipseLink)
 *
 * EntityManagerFactory emf = ... EntityManager entityManager =
 * emf.createEntityManager();
 *
 * entityManager.persist(objekatKojiSnimamo) entityManager.load(Class obj,
 * primarniKljuc); entityManager.merge(objekatKojiSnimamo);
 * entityManager.remove(objekatKojiSnimamo);
 *
 * @author grupa 1
 */
public abstract class AbstractBankAccount {

    public BankAccount getThis() {
        return (BankAccount) this;
    }

    public void save() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.getTransaction().begin();
            session.save(this);
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            throw new RuntimeException(exception.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void update() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.getTransaction().begin();
            session.update(this);
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            throw new RuntimeException(exception.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public BankAccount get() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            BankAccount bankAccount = (BankAccount) session.get(BankAccount.class, getThis().getAccountNumber());
            return bankAccount;
        } catch (HibernateException exception) {
            throw new RuntimeException(exception.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    //HQL imena tabela u bazi: ima klase u javi mapirane iz te tabele
    public void delete() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.getTransaction().begin();
            session.delete(this);
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            throw new RuntimeException(exception.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    //SQL statement : koristi ime tabele u bazi bankaccount
    public static List<BankAccount> loadAll() {
        //Hibernate 4.3

        try (CloseableSession closeableSession = new CloseableSession(HibernateUtil.getSessionFactory().openSession())) {

            //SQL select *from bankaccount;
            //HQL from BankAccount
            // Query query = session.createQuery("from BankAccount");
            // List<BankAccount> bankAccounts = query.list();
            return closeableSession.getSession()
                    .createQuery("from BankAccount").list();
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    public static boolean transferMoney(BankAccount fromAccount, BankAccount toAccount, double amount) {
        if (fromAccount == null || toAccount == null || amount <= 0) {
            return false;
        }
        if (fromAccount.getAccountNumber().equals(toAccount.getAccountNumber())) {
            return false;
        }
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.getTransaction().begin();
            if (fromAccount.getAmount() < amount) {
                System.err.println("Nemate dovoljno sredstava na vašem računu");
                return false;
            }
            double stariIznosFromAccount = fromAccount.getAmount();
            fromAccount.setAmount(stariIznosFromAccount - amount);
            double stariIznosToAccount = toAccount.getAmount();
            toAccount.setAmount(stariIznosToAccount + amount);
            session.update(fromAccount);
            session.update(toAccount);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException exception) {
            throw new RuntimeException(exception.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
