package view;

import model.Veiculo;
import controller.VeiculoController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class VeiculosView extends JFrame {

    private JTextField categoriaTextField;
    private JTextArea veiculosTextArea;
    private List<Veiculo> veiculos;

    private final List<String> categorias = Arrays.asList(
            "Compacto",
            "Standard",
            "Grande",
            "Econômico",
            "Premium",
            "Minivan"
    );

    public VeiculosView() {
        setTitle("Veículos");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel filterPanel = new JPanel();
        JLabel filterLabel = new JLabel("Filtrar por Categoria:");

        categoriaTextField = new JTextField(10);

        JButton filterButton = new JButton("Filtrar");
        filterButton.addActionListener(e -> filterVeiculosByCategoria(categoriaTextField.getText()));

        filterPanel.add(filterLabel);
        filterPanel.add(categoriaTextField);
        filterPanel.add(filterButton);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());

        veiculosTextArea = new JTextArea(10, 30);
        veiculosTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(veiculosTextArea);

        infoPanel.add(scrollPane, BorderLayout.CENTER);

        veiculos = createSampleVeiculos(); // Lista de veículos fictícia

        populateVeiculosList("Sem Filtro"); // Preenche inicialmente com todos os veículos

        JPanel buttonPanel = new JPanel();
        JButton novoVeiculoButton = new JButton("Novo Veículo");
        JButton fecharButton = new JButton("Fechar");

        buttonPanel.add(novoVeiculoButton);
        buttonPanel.add(fecharButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(filterPanel, BorderLayout.NORTH);
        getContentPane().add(infoPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Adiciona um listener de clique para abrir a janela de detalhes do veículo
        veiculosTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = veiculosTextArea.viewToModel(e.getPoint());
                int lineNumber = 0;
                try {
                    lineNumber = veiculosTextArea.getLineOfOffset(index);
                    int startIndex = veiculosTextArea.getLineStartOffset(lineNumber);
                    int endIndex = veiculosTextArea.getLineEndOffset(lineNumber);

                    String lineText = veiculosTextArea.getDocument().getText(startIndex, endIndex - startIndex);

                    if (lineText.startsWith("Placa:")) {
                        int veiculoIndex = lineNumber / 12;
                        if (veiculoIndex >= 0 && veiculoIndex < veiculos.size()) {
                            Veiculo veiculo = veiculos.get(veiculoIndex);
                            openVeiculoDetails(veiculo);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        fecharButton.addActionListener(e -> dispose());
        
        novoVeiculoButton.addActionListener(e -> openNovoVeiculoDialog());
        

        setVisible(true);
    }
    
    private void openNovoVeiculoDialog() {
        JFrame novoVeiculoFrame = new JFrame("Novo Veículo");
        novoVeiculoFrame.setSize(400, 400);
        novoVeiculoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel novoVeiculoPanel = new JPanel(new GridLayout(12, 2, 10, 10));

        JLabel placaLabel = new JLabel("Placa:");
        JTextField placaTextField = new JTextField();

        JLabel categoriaLabel = new JLabel("Categoria:");
        JComboBox<String> categoriaComboBox = new JComboBox<>(categorias.toArray(new String[0]));

        JLabel numPassageirosLabel = new JLabel("Num. Passageiros:");
        JTextField numPassageirosTextField = new JTextField();

        JLabel bagageiroLabel = new JLabel("Tamanho Bagageiro:");
        JTextField bagageiroTextField = new JTextField();

        JLabel cambioLabel = new JLabel("Tipo Câmbio:");
        JTextField cambioTextField = new JTextField();

        JLabel arCondicionadoLabel = new JLabel("Possui Ar Condicionado:");
        JCheckBox arCondicionadoCheckBox = new JCheckBox();

        JLabel consumoLabel = new JLabel("Média Consumo:");
        JTextField consumoTextField = new JTextField();

        JLabel airbagLabel = new JLabel("Possui Airbag:");
        JCheckBox airbagCheckBox = new JCheckBox();

        JLabel freioABSLabel = new JLabel("Possui Freio ABS:");
        JCheckBox freioABSCheckBox = new JCheckBox();

        JLabel dvdLabel = new JLabel("Possui DVD:");
        JCheckBox dvdCheckBox = new JCheckBox();

        JLabel custoPorDiaLabel = new JLabel("Custo Por Dia:");
        JTextField custoPorDiaTextField = new JTextField();

        JButton adicionarButton = new JButton("Adicionar");
        adicionarButton.addActionListener(e -> {
            String placa = placaTextField.getText();
            String categoria = categoriaComboBox.getSelectedItem().toString();
            int numPassageiros = Integer.parseInt(numPassageirosTextField.getText());
            double tamanhoBagageiro = Double.parseDouble(bagageiroTextField.getText());
            String tipoCambio = cambioTextField.getText();
            boolean possuiArCondicionado = arCondicionadoCheckBox.isSelected();
            double mediaConsumo = Double.parseDouble(consumoTextField.getText());
            boolean possuiAirbag = airbagCheckBox.isSelected();
            boolean possuiFreioABS = freioABSCheckBox.isSelected();
            boolean possuiDVD = dvdCheckBox.isSelected();
            double custoPorDia = Double.parseDouble(custoPorDiaTextField.getText());

            Veiculo novoVeiculo = new Veiculo(placa, categoria, numPassageiros, tamanhoBagageiro, tipoCambio, possuiArCondicionado,
                    mediaConsumo, possuiAirbag, possuiFreioABS, possuiDVD, custoPorDia);

            VeiculoController veiculoBD = new VeiculoController();
            
            veiculoBD.cadastrarVeiculo(placa, categoria, numPassageiros, tamanhoBagageiro, tipoCambio, possuiArCondicionado, mediaConsumo, possuiAirbag, possuiFreioABS, possuiDVD, custoPorDia);
            
            veiculos.add(novoVeiculo);
            populateVeiculosList("Sem Filtro");

            novoVeiculoFrame.dispose();
        });

        novoVeiculoPanel.add(placaLabel);
        novoVeiculoPanel.add(placaTextField);
        novoVeiculoPanel.add(categoriaLabel);
        novoVeiculoPanel.add(categoriaComboBox);
        novoVeiculoPanel.add(numPassageirosLabel);
        novoVeiculoPanel.add(numPassageirosTextField);
        novoVeiculoPanel.add(bagageiroLabel);
        novoVeiculoPanel.add(bagageiroTextField);
        novoVeiculoPanel.add(cambioLabel);
        novoVeiculoPanel.add(cambioTextField);
        novoVeiculoPanel.add(arCondicionadoLabel);
        novoVeiculoPanel.add(arCondicionadoCheckBox);
        novoVeiculoPanel.add(consumoLabel);
        novoVeiculoPanel.add(consumoTextField);
        novoVeiculoPanel.add(airbagLabel);
        novoVeiculoPanel.add(airbagCheckBox);
        novoVeiculoPanel.add(freioABSLabel);
        novoVeiculoPanel.add(freioABSCheckBox);
        novoVeiculoPanel.add(dvdLabel);
        novoVeiculoPanel.add(dvdCheckBox);
        novoVeiculoPanel.add(custoPorDiaLabel);
        novoVeiculoPanel.add(custoPorDiaTextField);
        novoVeiculoPanel.add(adicionarButton);

        novoVeiculoFrame.getContentPane().add(novoVeiculoPanel);
        novoVeiculoFrame.setVisible(true);
    }


    private List<Veiculo> createSampleVeiculos() {
    	VeiculoController veiculoController = new VeiculoController();
        ArrayList<Veiculo> veiculos = new ArrayList<Veiculo>();
        veiculos = veiculoController.listarVeiculos();
        return veiculos;
    }

    private void populateVeiculosList(String filterValue) {
        veiculosTextArea.setText("");
        for (Veiculo veiculo : veiculos) {
            if (filterValue.equals("Sem Filtro") || veiculo.getCategoria().equalsIgnoreCase(filterValue)) {
                veiculosTextArea.append("Placa: " + veiculo.getPlaca() + "\n");
                veiculosTextArea.append("Categoria: " + veiculo.getCategoria() + "\n");
                veiculosTextArea.append("Num. Passageiros: " + veiculo.getNumeroMaxPassageiros() + "\n");
                veiculosTextArea.append("Tamanho Bagageiro: " + veiculo.getTamanhoBagageiro() + "\n");
                veiculosTextArea.append("Tipo Câmbio: " + veiculo.getTipoCambio() + "\n");
                veiculosTextArea.append("Possui Ar Condicionado: " + veiculo.isPossuiArCondicionado() + "\n");
                veiculosTextArea.append("Média Consumo: " + veiculo.getMediaConsumo() + "\n");
                veiculosTextArea.append("Possui Airbag: " + veiculo.isPossuiAirbag() + "\n");
                veiculosTextArea.append("Possui Freio ABS: " + veiculo.isPossuiFreioABS() + "\n");
                veiculosTextArea.append("Possui DVD: " + veiculo.isPossuiDVD() + "\n");
                veiculosTextArea.append("Custo Por Dia: " + veiculo.getCustoPorDia() + "\n");
                veiculosTextArea.append("\n");
            }
        }
    }

    private void filterVeiculosByCategoria(String filterValue) {
        populateVeiculosList(filterValue);
    }

    private void openVeiculoDetails(Veiculo veiculo) {
        JFrame detailsFrame = new JFrame("Detalhes do Veículo");
        detailsFrame.setSize(400, 400);
        detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel detailsPanel = new JPanel(new GridLayout(12, 2, 10, 10));

        JLabel placaLabel = new JLabel("Placa:");
        JTextField placaTextField = new JTextField(veiculo != null ? veiculo.getPlaca() : "");
        placaTextField.setEditable(true);

        JLabel categoriaLabel = new JLabel("Categoria:");
        JComboBox<String> categoriaComboBox = new JComboBox<>(categorias.toArray(new String[0]));
        if (veiculo != null) {
            categoriaComboBox.setSelectedItem(veiculo.getCategoria());
        }

        JLabel numPassageirosLabel = new JLabel("Num. Passageiros:");
        JTextField numPassageirosTextField = new JTextField(veiculo != null ? String.valueOf(veiculo.getNumeroMaxPassageiros()) : "");
        numPassageirosTextField.setEditable(true);

        JLabel bagageiroLabel = new JLabel("Tamanho Bagageiro:");
        JTextField bagageiroTextField = new JTextField(veiculo != null ? String.valueOf(veiculo.getTamanhoBagageiro()) : "");
        bagageiroTextField.setEditable(true);

        JLabel cambioLabel = new JLabel("Tipo Câmbio:");
        JTextField cambioTextField = new JTextField(veiculo != null ? veiculo.getTipoCambio() : "");
        cambioTextField.setEditable(true);

        JLabel arCondicionadoLabel = new JLabel("Possui Ar Condicionado:");
        JCheckBox arCondicionadoCheckBox = new JCheckBox();
        if (veiculo != null) {
            arCondicionadoCheckBox.setSelected(veiculo.isPossuiArCondicionado());
        }

        JLabel consumoLabel = new JLabel("Média Consumo:");
        JTextField consumoTextField = new JTextField(veiculo != null ? String.valueOf(veiculo.getMediaConsumo()) : "");
        consumoTextField.setEditable(true);

        JLabel airbagLabel = new JLabel("Possui Airbag:");
        JCheckBox airbagCheckBox = new JCheckBox();
        if (veiculo != null) {
            airbagCheckBox.setSelected(veiculo.isPossuiAirbag());
        }

        JLabel freioABSLabel = new JLabel("Possui Freio ABS:");
        JCheckBox freioABSCheckBox = new JCheckBox();
        if (veiculo != null) {
            freioABSCheckBox.setSelected(veiculo.isPossuiFreioABS());
        }

        JLabel dvdLabel = new JLabel("Possui DVD:");
        JCheckBox dvdCheckBox = new JCheckBox();
        if (veiculo != null) {
            dvdCheckBox.setSelected(veiculo.isPossuiDVD());
        }

        JLabel custoPorDiaLabel = new JLabel("Custo Por Dia:");
        JTextField custoPorDiaTextField = new JTextField(veiculo != null ? String.valueOf(veiculo.getCustoPorDia()) : "");
        custoPorDiaTextField.setEditable(true);

        JButton atualizarButton = new JButton("Atualizar");
        atualizarButton.addActionListener(e -> {
            if (veiculo != null) {
                veiculo.setPlaca(placaTextField.getText());
                veiculo.setCategoria(categoriaComboBox.getSelectedItem().toString());
                veiculo.setNumeroMaxPassageiros(Integer.parseInt(numPassageirosTextField.getText()));
                veiculo.setTamanhoBagageiro(Double.parseDouble(bagageiroTextField.getText()));
                veiculo.setTipoCambio(cambioTextField.getText());
                veiculo.setPossuiArCondicionado(arCondicionadoCheckBox.isSelected());
                veiculo.setMediaConsumo(Double.parseDouble(consumoTextField.getText()));
                veiculo.setPossuiAirbag(airbagCheckBox.isSelected());
                veiculo.setPossuiFreioABS(freioABSCheckBox.isSelected());
                veiculo.setPossuiDVD(dvdCheckBox.isSelected());
                veiculo.setCustoPorDia(Double.parseDouble(custoPorDiaTextField.getText()));
                populateVeiculosList("Sem Filtro");
            }
            detailsFrame.dispose();
        });

        JButton removerButton = new JButton("Remover");
        removerButton.addActionListener(e -> {
            if (veiculo != null) {
                VeiculoController veiculoController = new VeiculoController();
                veiculoController.removerVeiculo(veiculo.getPlaca());
                populateVeiculosList("Sem Filtro");
            }
            detailsFrame.dispose();
        });

        detailsPanel.add(placaLabel);
        detailsPanel.add(placaTextField);
        detailsPanel.add(categoriaLabel);
        detailsPanel.add(categoriaComboBox);
        detailsPanel.add(numPassageirosLabel);
        detailsPanel.add(numPassageirosTextField);
        detailsPanel.add(bagageiroLabel);
        detailsPanel.add(bagageiroTextField);
        detailsPanel.add(cambioLabel);
        detailsPanel.add(cambioTextField);
        detailsPanel.add(arCondicionadoLabel);
        detailsPanel.add(arCondicionadoCheckBox);
        detailsPanel.add(consumoLabel);
        detailsPanel.add(consumoTextField);
        detailsPanel.add(airbagLabel);
        detailsPanel.add(airbagCheckBox);
        detailsPanel.add(freioABSLabel);
        detailsPanel.add(freioABSCheckBox);
        detailsPanel.add(dvdLabel);
        detailsPanel.add(dvdCheckBox);
        detailsPanel.add(custoPorDiaLabel);
        detailsPanel.add(custoPorDiaTextField);
        detailsPanel.add(atualizarButton);
        detailsPanel.add(removerButton);

        detailsFrame.getContentPane().add(detailsPanel);
        detailsFrame.setVisible(true);
    }

}
