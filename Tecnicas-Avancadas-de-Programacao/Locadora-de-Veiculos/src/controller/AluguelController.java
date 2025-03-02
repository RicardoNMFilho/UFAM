package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AluguelController {
    private Connection connection;

    public AluguelController() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:locadora.db");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void cadastrarAluguel(int id, String cpfCliente, String placaVeiculo, LocalDate dataInicio, LocalDate dataFim, double valorTotal) {
        // Verificar se o veículo está disponível
        if (isVeiculoDisponivel(placaVeiculo)) {
            // Verificar se o cliente existe
            if (isClienteExistente(cpfCliente)) {
                String query = "INSERT INTO alugueis (id, cpf_cliente, placa_veiculo, data_inicio, data_fim, valor_total) VALUES (?, ?, ?, ?, ?, ?)";
                try {
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, id);
                    statement.setString(2, cpfCliente);
                    statement.setString(3, placaVeiculo);
                    statement.setString(4, dataInicio.toString());
                    statement.setString(5, dataFim.toString());
                    statement.setDouble(6, valorTotal);
                    statement.executeUpdate();
                    statement.close();
                    System.out.println("Aluguel cadastrado com sucesso!");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Cliente não encontrado. Não foi possível cadastrar o aluguel.");
            }
        } else {
            System.out.println("Veículo não está disponível. Não foi possível cadastrar o aluguel.");
        }
    }

    private boolean isVeiculoDisponivel(String placaVeiculo) {
        String query = "SELECT disponivel FROM veiculos WHERE placa = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, placaVeiculo);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                boolean disponivel = resultSet.getBoolean("disponivel");
                statement.close();
                return disponivel;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isClienteExistente(String cpfCliente) {
        String query = "SELECT COUNT(*) AS count FROM clientes WHERE cpf = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, cpfCliente);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                statement.close();
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
