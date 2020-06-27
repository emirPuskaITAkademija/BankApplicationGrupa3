package bank.ui;

import bank.model.BankAccount;
import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * 1. UI controls -> 2. Kontejner -> JFrame , JPanel.. 3. LayoutManager->
 * BorderLayout, BoxLayout, GridLayout, GridBagLayout, FlowLayout
 *
 * @author grupa 1
 */
public class BankFrame extends JFrame {

    private final JLabel fromLabel = new JLabel("Sa računa:");
    private final JLabel amountLabel = new JLabel("Iznos: ");
    private final JLabel toLabel = new JLabel("Na račun:");
    private final JComboBox<BankAccount> fromComboBox = new JComboBox<>();
    private final JTextField amountTextField = new JTextField(10);
    private JComboBox<BankAccount> toComboBox = new JComboBox<>();
    private final JButton transferButton = new JButton("Execute");

    public BankFrame() {
        super("Bank transfer");
        setSize(450, 250);
        setLayout(new GridLayout(4, 1));
        JPanel fromPanel = new JPanel();
        JPanel amountPanel = new JPanel();
        JPanel toPanel = new JPanel();
        JPanel transferPanel = new JPanel();
        add(fromPanel);
        add(amountPanel);
        add(toPanel);
        add(transferPanel);

        fromPanel.add(fromLabel);
        List<BankAccount> bankAccounts = BankAccount.loadAll();
        bankAccounts.forEach(bankAccount -> fromComboBox.addItem(bankAccount));
        fromPanel.add(fromComboBox);
        amountPanel.add(amountLabel);
        amountPanel.add(amountTextField);
        toPanel.add(toLabel);
        bankAccounts.forEach(bankAccount -> toComboBox.addItem(bankAccount));
        toPanel.add(toComboBox);
        transferButton.addActionListener(this::executeTransfer);
        transferPanel.add(transferButton);
    }

    private void executeTransfer(ActionEvent e) {
        BankAccount fromAccount = (BankAccount) fromComboBox.getSelectedItem();
        BankAccount toAccount = (BankAccount) toComboBox.getSelectedItem();
        Double amount = Double.parseDouble(amountTextField.getText());
        BankAccount.transferMoney(fromAccount, toAccount, amount);
    }

    public void showFrame() {
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        BankFrame bankFrame = new BankFrame();
        SwingUtilities.invokeLater(bankFrame::showFrame);
    }
}
