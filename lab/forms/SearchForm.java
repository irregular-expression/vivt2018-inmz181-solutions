package lab.forms;

import lab.UserFormController;
import lab.database.Person;
import lab.database.SQLDatabase;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchForm extends JFrame {

    private UserFormController controller;
    private List<Person> persons;
    private JList<String> list1;

    public SearchForm(UserFormController controller) {
        this.controller = controller;

        try {
            persons = SQLDatabase.getFromDb();
        } catch (SQLException e) {
            persons = new ArrayList<>();
            e.printStackTrace();
        }

        this.setBounds(100, 100, 480, 360);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Телефонный справочник");
        this.getContentPane().setLayout(null);

        final JLabel label = new JLabel();
        label.setBounds(250,50,200,280);
        label.setText("Выберите абонента из списка.");
        this.add(label);

        JButton b=new JButton("Добавить абонента");
        b.setBounds(280,10,160,30);
        this.add(b);

        JButton b1=new JButton("Очистить справочник");
        b1.setBounds(280,50,160,30);
        this.add(b1);

        DefaultListModel<String> l1 = new DefaultListModel<>();
        for (int i = 0; i < persons.size(); i++) {
            l1.addElement(persons.get(i).getName());
        }
        list1 = new JList<>(l1);
        list1.setBounds(30,10, 200,300);
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (list1.getSelectedIndex() != -1) {
                    label.setText(persons.get(list1.getSelectedIndex()).toHtmlString());
                }
            }
        });
        this.add(list1);


        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.openUserForm(2);

            }
        });

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SQLDatabase.clearAll();
                    updateData();
                    label.setText("Выберите абонента из списка.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }


    public void updateData() {
        try {
            persons = SQLDatabase.getFromDb();
        } catch (SQLException e) {
            persons = new ArrayList<>();
            e.printStackTrace();
        }
        DefaultListModel<String> l1 = new DefaultListModel<>();
        for (int i = 0; i < persons.size(); i++) {
            l1.addElement(persons.get(i).getName());
        }
        list1.setModel(l1);
    }

}
