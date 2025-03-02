package view;

import javax.swing.*;

import model.Aluguel;
import model.Cliente;
import model.Veiculo;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AluguelView extends JFrame {

    private JTextArea alugueisTextArea;
    private JComboBox<String> filterComboBox;
    private List<Aluguel> alugueis;

    public AluguelView() {
        setTitle("Aluguéis");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel filterPanel = new JPanel();
        JLabel filterLabel = new JLabel("Filtrar por:");
        String[] filters = {"Sem Filtro", "Aberto", "Fechado"};
        filterComboBox = new JComboBox<>(filters);
        JButton filterButton = new JButton("Filtrar");

        filterButton.addActionListener(e -> {
            String selectedFilter = (String) filterComboBox.getSelectedItem();
            populateAlugueisList(selectedFilter);
        });

        filterPanel.add(filterLabel);
        filterPanel.add(filterComboBox);
        filterPanel.add(filterButton);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());

        alugueisTextArea = new JTextArea(20, 50);
        alugueisTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(alugueisTextArea);

        listPanel.add(scrollPane, BorderLayout.CENTER);

        alugueis = createSampleAlugueis(); // Lista de aluguéis fictícia

        populateAlugueisList(""); // Preenche inicialmente com todos os aluguéis

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(filterPanel, BorderLayout.NORTH);
        getContentPane().add(listPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton novoAluguelButton = new JButton("Novo Aluguel");

        novoAluguelButton.addActionListener(e -> openNovoAluguelDialog());

        buttonPanel.add(novoAluguelButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private List<Aluguel> createSampleAlugueis() {
        List<Aluguel> alugueis = new ArrayList<>();
        Cliente cliente1 = new Cliente("João", 123456789, "Rua A", "123456789", 25);
        Veiculo veiculo1 = new Veiculo("ABC123", "Compacto", 4, 300, "Manual", true, 12.5, true, true, false, 100);
        alugueis.add(new Aluguel(1, cliente1, veiculo1, LocalDate.of(2023, 6, 1),
                null, 0.0));

        Cliente cliente2 = new Cliente("Maria", 987654321, "Rua B", "987654321", 30);
        Veiculo veiculo2 = new Veiculo("DEF456", "Standard", 5, 400, "Automático", true, 11.0, true, true, true, 150);
        alugueis.add(new Aluguel(2, cliente2, veiculo2, LocalDate.of(2023, 6, 2),
                null, 0.0));

        // Adicione mais exemplos de aluguéis aqui, se necessário

        return alugueis;
    }

    private void populateAlugueisList(String filterValue) {
        alugueisTextArea.setText("");
        for (Aluguel aluguel : alugueis) {
            if (filterValue.equalsIgnoreCase("Sem Filtro")
                    || (filterValue.equalsIgnoreCase("Aberto") && aluguel.getDataFim() == null)
                    || (filterValue.equalsIgnoreCase("Fechado") && aluguel.getDataFim() != null)) {
                alugueisTextArea.append("ID: " + aluguel.getId() + "\n");
                alugueisTextArea.append("CPF do Cliente: " + aluguel.getCliente().getCpf() + "\n");
                alugueisTextArea.append("Placa do Veículo: " + aluguel.getVeiculo().getPlaca() + "\n");
                alugueisTextArea.append("Data de Início: " + aluguel.getDataInicio() + "\n");
                if (aluguel.getDataFim() != null) {
                    alugueisTextArea.append("Data de Fim: " + aluguel.getDataFim() + "\n");
                } else {
                    alugueisTextArea.append("Status: Em aberto\n");
                }
                alugueisTextArea.append("Valor Total: " + aluguel.getValorTotal() + "\n");
                alugueisTextArea.append("\n");
            }
        }
    }

    private void openNovoAluguelDialog() {
        JFrame novoAluguelFrame = new JFrame("Novo Aluguel");
        novoAluguelFrame.setSize(300, 200);
        novoAluguelFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        novoAluguelFrame.setLayout(new BorderLayout());

        JPanel novoAluguelPanel = new JPanel();
        novoAluguelPanel.setLayout(new GridLayout(4, 2));

        JLabel clienteLabel = new JLabel("CPF do Cliente:");
        JTextField clienteTextField = new JTextField();
        JLabel veiculoLabel = new JLabel("Placa do Veículo:");
        JTextField veiculoTextField = new JTextField();
        JLabel dataInicioLabel = new JLabel("Data de Início (AAAA-MM-DD):");
        JTextField dataInicioTextField = new JTextField();

        JButton adicionarButton = new JButton("Adicionar");

        adicionarButton.addActionListener(e -> {
            String clienteCPF = clienteTextField.getText();
            int cpf = Integer.parseInt(clienteCPF);
            Cliente cliente = encontrarClientePorCPF(cpf);

            String veiculoPlaca = veiculoTextField.getText();
            Veiculo veiculo = encontrarVeiculoPorPlaca(veiculoPlaca);

            String dataInicioString = dataInicioTextField.getText();
            LocalDate dataInicio = LocalDate.parse(dataInicioString);

            if (cliente != null && veiculo != null) {
                Aluguel aluguel = new Aluguel(alugueis.size() + 1, cliente, veiculo, dataInicio, null, 0.0);
                alugueis.add(aluguel);
                populateAlugueisList("");
                novoAluguelFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Cliente ou veículo não encontrado.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        novoAluguelPanel.add(clienteLabel);
        novoAluguelPanel.add(clienteTextField);
        novoAluguelPanel.add(veiculoLabel);
        novoAluguelPanel.add(veiculoTextField);
        novoAluguelPanel.add(dataInicioLabel);
        novoAluguelPanel.add(dataInicioTextField);
        novoAluguelPanel.add(new JLabel());
        novoAluguelPanel.add(adicionarButton);

        novoAluguelFrame.add(novoAluguelPanel, BorderLayout.CENTER);
        novoAluguelFrame.setVisible(true);
    }

    private Cliente encontrarClientePorCPF(int cpf) {
        // Implemente a lógica para encontrar o cliente pelo CPF aqui
        return null;
    }

    private Veiculo encontrarVeiculoPorPlaca(String placa) {
        // Implemente a lógica para encontrar o veículo pela placa aqui
        return null;
    }

    private Aluguel encontrarAluguelPorId(int idAluguel) {
        for (Aluguel aluguel : alugueis) {
            if (aluguel.getId() == idAluguel) {
                return aluguel;
            }
        }
        return null;
    }

    private void openDetalhesAluguelDialog(int idAluguel) {
        Aluguel aluguelSelecionado = encontrarAluguelPorId(idAluguel);

        if (aluguelSelecionado != null && aluguelSelecionado.getDataFim() == null) {
            JFrame detalhesAluguelFrame = new JFrame("Detalhes do Aluguel");
            detalhesAluguelFrame.setSize(300, 200);
            detalhesAluguelFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            detalhesAluguelFrame.setLayout(new BorderLayout());

            JPanel detalhesAluguelPanel = new JPanel();
            detalhesAluguelPanel.setLayout(new GridLayout(2, 2));

            JLabel dataFimLabel = new JLabel("Data de Fim (AAAA-MM-DD):");
            JTextField dataFimTextField = new JTextField();

            JButton fecharButton = new JButton("Fechar Aluguel");

            fecharButton.addActionListener(e -> {
                String dataFimString = dataFimTextField.getText();
                LocalDate dataFim = LocalDate.parse(dataFimString);

                aluguelSelecionado.setDataFim(dataFim);
                populateAlugueisList("");

                detalhesAluguelFrame.dispose();
            });

            detalhesAluguelPanel.add(dataFimLabel);
            detalhesAluguelPanel.add(dataFimTextField);
            detalhesAluguelPanel.add(new JLabel());
            detalhesAluguelPanel.add(fecharButton);

            detalhesAluguelFrame.add(detalhesAluguelPanel, BorderLayout.CENTER);
            detalhesAluguelFrame.setVisible(true);
        }
    }

    private void showAllAlugueis() {
        populateAlugueisList("Sem Filtro");
    }

    
}
