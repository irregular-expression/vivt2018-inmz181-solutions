package lab;

import lab.database.SQLDatabase;
import lab.forms.AddAddressForm;
import lab.forms.SearchForm;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class PhonebookApp {

    private JFrame addAddressFrame;
    private JFrame searchFrame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SQLDatabase.init();

                    PhonebookApp phonebook = new PhonebookApp();
                    phonebook.addAddressFrame.setVisible(true);
                    //phonebook.searchFrame.setVisible(true);
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
        addAddressFrame = new AddAddressForm();
        searchFrame = new SearchForm();
    }


}
