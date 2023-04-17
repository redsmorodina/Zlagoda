package info_menu_manager;

import entity.ProductInStore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;

import static bd_connection.Store_Product.getAllAboutProductsOnUPC;

public class StoreProductManager {

    public static void display() {

        String textForUPCField = "Enter UPC";

        // Create a JFrame
        JFrame frame = new JFrame("StoreProduct Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JPanel for the buttons
        JToolBar buttonPanel = new JToolBar();

        // Create a "Home" button and add it to the button panel
        JButton homeButton = new JButton("Home");
        buttonPanel.add(homeButton);



        JTextField upcField = new JTextField(textForUPCField);
        upcField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (upcField.getText().equals(textForUPCField)) {
                    upcField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (upcField.getText().isEmpty()) {
                    upcField.setText(textForUPCField);
                }
            }
        });
        buttonPanel.add(upcField);

        JButton searchButton = new JButton("Search");
        buttonPanel.add(searchButton);

        JPanel contentPanel = new JPanel(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridLayout(6, 2));
        JLabel upcLabel = new JLabel("UPC:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel descrLabel = new JLabel("Description");
        JLabel priceLabel = new JLabel("Price:");
        JLabel amountLabel = new JLabel("Amount:");
        JLabel promoLabel = new JLabel("Is Promo:");


        JLabel upcLabelN = new JLabel("");
        JLabel nameLabelN = new JLabel("");
        JLabel descrLabelN = new JLabel("");
        JLabel priceLabelN = new JLabel("");
        JLabel amountLabelN = new JLabel("");
        JLabel promoLabelN = new JLabel("");

        infoPanel.add(upcLabel);
        infoPanel.add(upcLabelN);
        infoPanel.add(nameLabel);
        infoPanel.add(nameLabelN);
        infoPanel.add(descrLabel);
        infoPanel.add(descrLabelN);
        infoPanel.add(priceLabel);
        infoPanel.add(priceLabelN);
        infoPanel.add(amountLabel);
        infoPanel.add(amountLabelN);
        infoPanel.add(promoLabel);
        infoPanel.add(promoLabelN);

        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.add(infoPanel, BorderLayout.CENTER);

        contentPanel.add(productPanel, BorderLayout.CENTER);

        frame.add(buttonPanel, BorderLayout.PAGE_START);
        frame.add(contentPanel, BorderLayout.CENTER);

        frame.setSize(500, 500);
        frame.setVisible(true);

        productPanel.setVisible(false);

        searchButton.addActionListener(e -> {
            String upc = upcField.getText();

            if (!upc.equals(textForUPCField)) {
                ProductInStore pr;
                try {
                    pr = getAllAboutProductsOnUPC(upc);

                    upcLabelN.setText(pr.getUPC());
                    nameLabelN.setText(pr.getProduct().getName());
                    descrLabelN.setText(pr.getProduct().getCharacteristics());
                    priceLabelN.setText(pr.getPrice().toString());
                    amountLabelN.setText(pr.getAmount()+"");
                    promoLabelN.setText(pr.isPromotional()+"");

                    productPanel.setVisible(true);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Please enter correct upc", "Eror", JOptionPane.ERROR_MESSAGE);
                }
                    catch (NullPointerException ex){
                        JOptionPane.showMessageDialog(null, "Please enter correct upc", "Eror", JOptionPane.ERROR_MESSAGE);
                        productPanel.setVisible(false);
                    }



            } else {
                productPanel.setVisible(false);
            }
        });
    }

    public static void main(String[] args) {
        display();
    }
}
