package ui;

import business.ContactBusiness;
import entity.ContactEntity;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainForm extends JFrame{
    private JPanel rootPanel;
    private JButton buttonNewContact;
    private JButton buttonRemove;
    private JTable tableContacts;
    private JLabel labelContactCount;

    private ContactBusiness mContactBusiness;
    private String mName = "";
    private String mPhone = "";

    public MainForm() {
        setContentPane(rootPanel);
        setSize(500, 250);
        setVisible(true);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width / 2 - getSize().width / 2, dimension.width / 2 - getSize().height / 2);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mContactBusiness = new ContactBusiness();

        setListeners();
        loadContacts();
    }

    private void loadContacts(){
        List<ContactEntity> contactList = mContactBusiness.getList();

        // Java Swing - To create model for table
        String[] columnNames = {"Name", "Phone"};
        DefaultTableModel model = new DefaultTableModel(new Object[0][0], columnNames);

        for (ContactEntity entity : contactList) {
            Object[] object = new Object[2];
            object[0] = entity.getName();
            object[1] = entity.getPhone();
            model.addRow(object);
        }

        // Add values to table
        this.tableContacts.clearSelection();
        this.tableContacts.setModel(model);

        // Update contacts from table
        labelContactCount.setText(mContactBusiness.getContactCountDescription());
    }

    private void setListeners(){
        buttonNewContact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ContactForm();
                dispose();
            }
        });

        tableContacts.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()){
                    if (tableContacts.getSelectedRow() != -1) {
                        mName = tableContacts.getValueAt(tableContacts.getSelectedRow(), 0).toString();
                        mPhone = tableContacts.getValueAt(tableContacts.getSelectedRow(), 1).toString();
                    }
                }
            }
        });

        buttonRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Remove
                    mContactBusiness.delete(mName, mPhone);
                    // Clear
                    mName = "";
                    mPhone = "";
                } catch (Exception excp) {
                    JOptionPane.showMessageDialog(new JFrame(), excp.getMessage(), "Failed to remove", JOptionPane.ERROR_MESSAGE);
                } finally {
                    // Update list
                    loadContacts();
                }
            }
        });
    }


}
