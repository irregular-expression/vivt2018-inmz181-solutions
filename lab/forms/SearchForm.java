package lab.forms;

import lab.UserFormController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchForm extends JFrame {

    public SearchForm(UserFormController controller) {

        this.setBounds(100, 100, 450, 450);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        final JLabel label = new JLabel();
        label.setSize(450,100);
        this.add(label);

        JButton b=new JButton("Добавить");
        b.setBounds(200,150,80,30);
        this.add(b);

        final DefaultListModel<String> l1 = new DefaultListModel<>();
        l1.addElement("Иванов Иван Иванович");
        l1.addElement("Петров Пётр Петрович");
        l1.addElement("Сусликов Максим Леонидович");
        l1.addElement("Шишигин Семён Витальевич");

        final JList<String> list1 = new JList<>(l1);
        list1.setBounds(100,100, 75,75);
        this.add(list1);


        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String data = "";
                if (list1.getSelectedIndex() != -1) {
                    data = "" + list1.getSelectedValue();
                    label.setText(data);
                }
                label.setText(data);
            }
        });

    }

}
