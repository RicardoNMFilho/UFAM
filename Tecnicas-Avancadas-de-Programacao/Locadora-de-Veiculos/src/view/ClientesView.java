package view;

import model.Cliente;

import javax.swing.*;

import controller.ClienteController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

class ClientesView extends JFrame {

    private JTextField cpfTextField;
    private JTextArea clienteInfoTextArea;
    private List<Cliente> clientes;

    public ClientesView() {
        setTitle("Clientes");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        

        JPanel filterPanel = new JPanel();
        JLabel filterLabel = new JLabel("Filtrar por CPF:");

        cpfTextField = new JTextField(10);

        JButton filterButton = new JButton("Filtrar");
        filterButton.addActionListener(e -> filterClientesByCPF(cpfTextField.getText()));

        filterPanel.add(filterLabel);
        filterPanel.add(cpfTextField);
        filterPanel.add(filterButton);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());

        clienteInfoTextArea = new JTextArea(10, 30);
        clienteInfoTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(clienteInfoTextArea);

        infoPanel.add(scrollPane, BorderLayout.CENTER);

        clientes = createSampleClientes(); // Lista de clientes fictícia

        populateClienteInfo(""); // Preenche inicialmente com todos os clientes

        JPanel buttonPanel = new JPanel();
        JButton novoClienteButton = new JButton("Novo Cliente");
        JButton fecharButton = new JButton("Fechar");

        buttonPanel.add(novoClienteButton);
        buttonPanel.add(fecharButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(filterPanel, BorderLayout.NORTH);
        getContentPane().add(infoPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Adiciona um listener de clique para abrir a janela de detalhes do cliente
        clienteInfoTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = clienteInfoTextArea.viewToModel(e.getPoint());
                int lineNumber = 0;
                try {
                    lineNumber = clienteInfoTextArea.getLineOfOffset(index);
                    int startIndex = clienteInfoTextArea.getLineStartOffset(lineNumber);
                    int endIndex = clienteInfoTextArea.getLineEndOffset(lineNumber);

                    String lineText = clienteInfoTextArea.getDocument().getText(startIndex, endIndex - startIndex);

                    if (lineText.startsWith("CPF:")) {
                        int clientIndex = lineNumber / 6;
                        if (clientIndex >= 0 && clientIndex < clientes.size()) {
                            Cliente cliente = clientes.get(clientIndex);
                            openClienteDetails(cliente);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        fecharButton.addActionListener(e -> dispose());
        
        novoClienteButton.addActionListener(e -> openNovoClienteDialog());

        setVisible(true);
    }
    
    private void openNovoClienteDialog() {
        JFrame novoClienteFrame = new JFrame("Novo Cliente");
        novoClienteFrame.setSize(400, 300);
        novoClienteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel novoClientePanel = new JPanel(new GridLayout(7, 2, 10, 10));

        JLabel nomeLabel = new JLabel("Nome:");
        JTextField nomeTextField = new JTextField();

        JLabel cpfLabel = new JLabel("CPF:");
        JTextField novoCpfTextField = new JTextField(); // Renomeado para evitar conflito

        JLabel enderecoLabel = new JLabel("Endereço:");
        JTextField enderecoTextField = new JTextField();

        JLabel telefoneLabel = new JLabel("Telefone:");
        JTextField telefoneTextField = new JTextField();

        JLabel idadeLabel = new JLabel("Idade:");
        JTextField idadeTextField = new JTextField();

        JButton adicionarButton = new JButton("Adicionar");
        adicionarButton.addActionListener(e -> {
            String nome = nomeTextField.getText();
            long cpf = Integer.parseInt(novoCpfTextField.getText()); // Atualizado para usar novoCpfTextField
            String endereco = enderecoTextField.getText();
            String telefone = telefoneTextField.getText();
            int idade = Integer.parseInt(idadeTextField.getText());
            
            ClienteController clienteController = new ClienteController();
            clienteController.cadastrarCliente(nome, cpf, endereco, telefone, idade);
            
            populateClienteInfo("");

            novoClienteFrame.dispose();
        });

        novoClientePanel.add(nomeLabel);
        novoClientePanel.add(nomeTextField);
        novoClientePanel.add(cpfLabel);
        novoClientePanel.add(novoCpfTextField); // Atualizado para usar novoCpfTextField
        novoClientePanel.add(enderecoLabel);
        novoClientePanel.add(enderecoTextField);
        novoClientePanel.add(telefoneLabel);
        novoClientePanel.add(telefoneTextField);
        novoClientePanel.add(idadeLabel);
        novoClientePanel.add(idadeTextField);
        novoClientePanel.add(adicionarButton);

        novoClienteFrame.getContentPane().add(novoClientePanel);
        novoClienteFrame.setVisible(true);
    }


    private ArrayList<Cliente> createSampleClientes() {
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        ClienteController clienteController = new ClienteController();
        clientes = clienteController.listarClientes();
        return clientes;
    }

    private void populateClienteInfo(String filterValue) {
        clienteInfoTextArea.setText("");
        
        for (Cliente cliente : createSampleClientes()) {
            if (filterValue.isEmpty() || String.valueOf(cliente.getCpf()).startsWith(filterValue)) {
                clienteInfoTextArea.append("Nome: " + cliente.getNome() + "\n");
                clienteInfoTextArea.append("CPF: " + cliente.getCpf() + "\n");
                clienteInfoTextArea.append("Endereço: " + cliente.getEndereco() + "\n");
                clienteInfoTextArea.append("Telefone: " + cliente.getTelefone() + "\n");
                clienteInfoTextArea.append("Idade: " + cliente.getIdade() + "\n");
                clienteInfoTextArea.append("\n");
            }
        }
    }

    private void filterClientesByCPF(String filterValue) {
        populateClienteInfo(filterValue);
    }

    private void openClienteDetails(Cliente cliente) {
        JFrame detailsFrame = new JFrame("Detalhes do Cliente");
        detailsFrame.setSize(400, 300);
        detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel detailsPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        JLabel nomeLabel = new JLabel("Nome:");
        JTextField nomeTextField = new JTextField(cliente != null ? cliente.getNome() : "");
        nomeTextField.setEditable(true);

        JLabel cpfLabel = new JLabel("CPF:");
        JTextField cpfTextField = new JTextField(cliente != null ? String.valueOf(cliente.getCpf()) : "");
        cpfTextField.setEditable(false);

        JLabel enderecoLabel = new JLabel("Endereço:");
        JTextField enderecoTextField = new JTextField(cliente != null ? cliente.getEndereco() : "");
        enderecoTextField.setEditable(true);

        JLabel telefoneLabel = new JLabel("Telefone:");
        JTextField telefoneTextField = new JTextField(cliente != null ? cliente.getTelefone() : "");
        telefoneTextField.setEditable(true);

        JLabel idadeLabel = new JLabel("Idade:");
        JTextField idadeTextField = new JTextField(cliente != null ? String.valueOf(cliente.getIdade()) : "");
        idadeTextField.setEditable(true);

        JButton atualizarButton = new JButton("Atualizar");
        atualizarButton.addActionListener(e -> {
            if (cliente != null) {
            	ClienteController clienteController = new ClienteController();
            	clienteController.alterarCliente(nomeTextField.getText(), cliente.getCpf(), enderecoTextField.getText(), telefoneTextField.getText(), Integer.parseInt(idadeTextField.getText()));
                populateClienteInfo("");
            }
            detailsFrame.dispose();
        });

        JButton removerButton = new JButton("Remover");
        removerButton.addActionListener(e -> {
            if (cliente != null) {
            	ClienteController clienteController = new ClienteController();
                clienteController.removerCliente(cliente.getCpf());
                populateClienteInfo("");
            }
            detailsFrame.dispose();
        });

        detailsPanel.add(nomeLabel);
        detailsPanel.add(nomeTextField);
        detailsPanel.add(cpfLabel);
        detailsPanel.add(cpfTextField);
        detailsPanel.add(enderecoLabel);
        detailsPanel.add(enderecoTextField);
        detailsPanel.add(telefoneLabel);
        detailsPanel.add(telefoneTextField);
        detailsPanel.add(idadeLabel);
        detailsPanel.add(idadeTextField);
        detailsPanel.add(atualizarButton);
        detailsPanel.add(removerButton);

        detailsFrame.getContentPane().add(detailsPanel);
        detailsFrame.setVisible(true);
    }

   
}
