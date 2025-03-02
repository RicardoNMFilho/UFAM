package controller;

import java.sql.*;
import java.util.ArrayList;

import model.Cliente;

public class ClienteController {
    private Connection connection;

    public ClienteController() {
        // Conectar ao banco de dados SQLite
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Locadora.db");
            criarTabelaClientes();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void criarTabelaClientes() {
        String query = "CREATE TABLE IF NOT EXISTS clientes (" +
                "nome TEXT, " +
                "cpf INTEGER, " +
                "endereco TEXT, " +
                "telefone TEXT, " +
                "idade INTEGER" +
                ")";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
            System.out.println("Tabela clientes criada com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cadastrarCliente(String nome, long cpf, String endereco, String telefone, int idade) {
        String query = "INSERT INTO clientes (nome, cpf, endereco, telefone, idade) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nome);
            statement.setFloat(2, cpf);
            statement.setString(3, endereco);
            statement.setString(4, telefone);
            statement.setInt(5, idade);
            statement.executeUpdate();
            statement.close();
            System.out.println("Cliente cadastrado com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void alterarCliente(String nome, long cpf, String endereco, String telefone, int idade) {
        String query = "UPDATE clientes SET nome = ?, cpf = ?, endereco = ?, telefone = ?, idade = ? WHERE cpf = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nome);
            statement.setFloat(2, cpf);
            statement.setString(3, endereco);
            statement.setString(4, telefone);
            statement.setInt(5, idade);
            statement.setFloat(6, cpf);
            statement.executeUpdate();
            statement.close();
            System.out.println("Cliente alterado com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removerCliente(long l) {
        String query = "DELETE FROM clientes WHERE cpf = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setFloat(1, l);
            statement.executeUpdate();
            statement.close();
            System.out.println("Cliente removido com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Cliente> listarClientes() {
        ArrayList<Cliente> listaClientes = new ArrayList<>();

        String query = "SELECT * FROM clientes";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                int cpf = resultSet.getInt("cpf");
                String endereco = resultSet.getString("endereco");
                String telefone = resultSet.getString("telefone");
                int idade = resultSet.getInt("idade");

                Cliente cliente = new Cliente(nome, cpf, endereco, telefone, idade);

                listaClientes.add(cliente);
            }
            resultSet.close();
            statement.close();

            return listaClientes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
