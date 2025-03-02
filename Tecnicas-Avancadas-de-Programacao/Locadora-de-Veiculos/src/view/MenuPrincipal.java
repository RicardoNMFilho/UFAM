package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("Menu");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setPreferredSize(new Dimension(200, 720));

        JButton aluguelButton = new JButton("Aluguel");
        JButton clientesButton = new JButton("Clientes");
        JButton veiculosButton = new JButton("Veiculos");

        aluguelButton.setPreferredSize(new Dimension(180, 60));
        clientesButton.setPreferredSize(new Dimension(180, 60));
        veiculosButton.setPreferredSize(new Dimension(180, 60));

        aluguelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	AluguelView aluguelView = new AluguelView();
            }
        });

        clientesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ClientesView clienteView = new ClientesView();
            }
        });

        veiculosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VeiculosView veiculoView = new VeiculosView();
            }
        });

        buttonPanel.add(aluguelButton);
        buttonPanel.add(clientesButton);
        buttonPanel.add(veiculosButton);

        getContentPane().add(buttonPanel, BorderLayout.EAST);
        setVisible(true);
    }
}

