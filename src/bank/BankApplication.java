package bank;

import bank.model.BankAccount;
import java.util.List;

/**
 *
 * <li>1. Kreiranje baze bank i tabele bank_account(account_number, amount)
 * <li>2. Kreiranje Java app
 * <li>3. Mapiranje bank_account tabele u Java klasu -> ORM(Objektno Relaciono
 * Mapiranje) 3.1 add Hibernate library 3.2 hibernate.cfg.xml -> na koju se ona
 * bazu veže i koji dijalekt(MySQL) 3.3 Session session =
 * HibernateUtil.getSessionFactory().openSession(); Actor actor = new
 * Actor(....); session.save(actor); session.delete(actor);
 * session.update(actor); 3.4 Mapiranje bank_account tabele u Java klasu
 * hibernate.reveng.xml
 *
 * DVA pristupa : ¸ 1. Java klasu + XML fajl unutar kojeg se nalaze podaci o
 * mapiranju 2. Java klasu sa anotacijama
 *
 * @author grupa 1
 */
public class BankApplication {

    public static void main(String[] args) {
        //AbstractBankAcccount i tipa je BankAccount
        BankAccount bankAccount = new BankAccount("33456700000000000", 12345.67);
        //sa active record
        bankAccount.save();

        List<BankAccount> bankAccounts = BankAccount.loadAll();
        for(BankAccount ba: bankAccounts){
            System.out.println(ba);
        }

    }
}
