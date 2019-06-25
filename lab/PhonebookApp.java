package lab;

import lab.database.SQLDatabase;
import lab.forms.AddAddressForm;
import lab.forms.SearchForm;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class PhonebookApp implements UserFormController {

    private JFrame addAddressFrame;
    private JFrame searchFrame;
    private static PhonebookApp phonebook;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SQLDatabase.init();
                    phonebook = new PhonebookApp();
                    phonebook.openUserForm(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public PhonebookApp() {
        addAddressFrame = new AddAddressForm(this);
        searchFrame = new SearchForm(this);
    }


    @Override
    public void openUserForm(int id) {
        switch (id) {
            case 2:
                phonebook.searchFrame.setVisible(false);
                phonebook.addAddressFrame.setVisible(true);
                break;
            case 1:
                phonebook.searchFrame.setVisible(true);
                ((SearchForm) phonebook.searchFrame).updateData();
                phonebook.addAddressFrame.setVisible(false);
                break;
        }
    }
}
