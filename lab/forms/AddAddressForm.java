package lab.forms;

import lab.UserFormController;
import lab.database.Person;
import lab.database.SQLDatabase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddAddressForm extends JFrame {

    private UserFormController controller;
    private JTextField nameTextField;
    private JTextField phoneTextField;
    private JTextField emailTextField;

    public AddAddressForm(UserFormController controller) {

        this.setBounds(100, 100, 360, 180);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Добавление абонента");
        this.getContentPane().setLayout(null);

        JLabel lblName = new JLabel("Ф.И.О.");
        lblName.setBounds(20, 10, 60, 20);
        this.getContentPane().add(lblName);

        nameTextField = new JTextField();
        nameTextField.setBounds(100, 10, 200, 20);
        nameTextField.setColumns(10);
        this.getContentPane().add(nameTextField);

        JLabel lblPhone = new JLabel("Телефон");
        lblPhone.setBounds(20, 40, 60, 20);
        this.getContentPane().add(lblPhone);

        phoneTextField = new JTextField();
        phoneTextField.setBounds(100, 40, 200, 20);
        this.getContentPane().add(phoneTextField);
        phoneTextField.setColumns(10);

        JLabel lblEmailId = new JLabel("E-mail");
        lblEmailId.setBounds(20, 70, 60, 20);
        this.getContentPane().add(lblEmailId);

        emailTextField = new JTextField();
        emailTextField.setBounds(100, 70, 200, 20);
        this.getContentPane().add(emailTextField);
        emailTextField.setColumns(10);

        JButton btnSubmit = new JButton("Добавить");
        btnSubmit.setBounds(20, 110, 100, 23);
        this.getContentPane().add(btnSubmit);

        JButton btnClear = new JButton("Очистить");
        btnClear.setBounds(220, 110, 100, 23);
        this.getContentPane().add(btnClear);




        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(nameTextField.getText().isEmpty()||(phoneTextField.getText().isEmpty())||(emailTextField.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Незаполнены данные!");
                } else {
                    try {
                        Person person = new Person(0,
                                nameTextField.getText(),
                                phoneTextField.getText(),
                                emailTextField.getText());
                        SQLDatabase.addToDb(person);
                        JOptionPane.showMessageDialog(null, "Абонент успешно добавлен в справочник!");
                        controller.openUserForm(1);
                        System.out.println(person.toHtmlString());
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Ошибка базы данных!");
                        e.printStackTrace();
                    }
                }
            }
        });

        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                phoneTextField.setText(null);
                emailTextField.setText(null);
                nameTextField.setText(null);
            }
        });
    }


}
