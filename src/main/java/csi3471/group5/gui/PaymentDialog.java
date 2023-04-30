package csi3471.group5.gui;

import csi3471.group5.Guest;
import csi3471.group5.SystemHandler;
import csi3471.group5.bank.Bank;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class PaymentDialog extends JDialog {

    public PaymentDialog(Guest g) {
        super();
        createGUI(g);
    }

    private void createGUI(Guest g) {
        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.Y_AXIS));
        listPane.setSize(400, 500);

        JTextField cardNum = new JTextField(16);
        JTextField expDate = new JTextField(16);
        JTextField cvv = new JTextField(16);

        JLabel cardLbl = new JLabel("Card Number:");
        JLabel expLbl = new JLabel("Expiration Date: mm/yy");
        JLabel cvvLbl = new JLabel("CVV:");

        listPane.add(cardLbl);
        listPane.add(cardNum);

        listPane.add(expLbl);
        listPane.add(expDate);
        listPane.add(cvvLbl);
        listPane.add(cvv);

        JButton ok = new JButton("OK");
        listPane.add(ok);

        add(listPane);
        pack();
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cardNum.getText().length() != 16 || !cardNum.getText().matches("^[0-9]+$")) {
                    JOptionPane.showMessageDialog(null, "Invalid card number.");
                    return;
                }
                if(expDate.getText().length() != 5 || !expDate.getText().matches("^[0-9]+/[0-9]+$")) {
                    JOptionPane.showMessageDialog(null, "Invalid expiration date.");
                    return;
                }
                if(cvv.getText().length() != 3 || !cvv.getText().matches("^[0-9]+$")) {
                    JOptionPane.showMessageDialog(null, "Invalid CVV.");
                    return;
                }
                
                String[] MY = expDate.getText().split("/");
                Bank b = new Bank();
                String token = b.getToken(cardNum.getText(), Integer.parseInt(cvv.getText()), Integer.parseInt(MY[0]), Integer.parseInt(MY[1]));
                g.setBankToken(token);
                dispose();
            }
        });
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}