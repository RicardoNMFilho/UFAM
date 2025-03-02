package controller;

import model.Veiculo;

import java.sql.*;
import java.util.ArrayList;

import org.sqlite.*;

public class VeiculoController {
    private Connection connection;

    public VeiculoController() {
        // Conectar ao banco de dados SQLite
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Locadora.db");
            criarTabelaVeiculos();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void criarTabelaVeiculos() {
        String query = "CREATE TABLE IF NOT EXISTS veiculos (" +
                "placa TEXT, " +
                "categoria TEXT, " +
                "passageiros INTEGER, " +
                "bagageiro REAL, " +
                "cambio TEXT, " +
                "ar INTEGER, " +
                "consumo REAL, " +
                "airbag INTEGER, " +
                "abs INTEGER, " +
                "dvd INTEGER, " +
                "custo REAL" +
                ")";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
            System.out.println("Tabela veiculos criada com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cadastrarVeiculo(String placa, String categoria, int numeroPassageiros, double tamanhoBagageiro, String tipoCambio,
                                 boolean possuiArCondicionado, double mediaConsumo, boolean possuiAirbag,
                                 boolean possuiFreioABS, boolean possuiDVD, double custoPorDia) {
        String query = "INSERT INTO veiculos (placa, categoria, passageiros, bagageiro, cambio, ar, " +
                "consumo, airbag, abs, dvd, custo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, placa);
            statement.setString(2, categoria);
            statement.setInt(3, numeroPassageiros);
            statement.setDouble(4, tamanhoBagageiro);
            statement.setString(5, tipoCambio);
            statement.setBoolean(6, possuiArCondicionado);
            statement.setDouble(7, mediaConsumo);
            statement.setBoolean(8, possuiAirbag);
            statement.setBoolean(9, possuiFreioABS);
            statement.setBoolean(10, possuiDVD);
            statement.setDouble(11, custoPorDia);
            statement.executeUpdate();
            statement.close();
            System.out.println("Veículo cadastrado com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void alterarVeiculo(String placa, String categoria, int numeroPassageiros, double tamanhoBagageiro, String tipoCambio,
                               boolean possuiArCondicionado, double mediaConsumo, boolean possuiAirbag,
                               boolean possuiFreioABS, boolean possuiDVD, double custoPorDia) {
        String query = "UPDATE veiculos SET placa = ?, categoria = ?, passageiros = ?, bagageiro = ?, cambio = ?, " +
                "ar_condicionado = ?, consumo = ?, airbag = ?, freio_abs = ?, dvd = ?, custo = ? WHERE placa = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, placa);
            statement.setString(2, categoria);
            statement.setInt(3, numeroPassageiros);
            statement.setDouble(4, tamanhoBagageiro);
            statement.setString(5, tipoCambio);
            statement.setBoolean(6, possuiArCondicionado);
            statement.setDouble(7, mediaConsumo);
            statement.setBoolean(8, possuiAirbag);
            statement.setBoolean(9, possuiFreioABS);
            statement.setBoolean(10, possuiDVD);
            statement.setDouble(11, custoPorDia);
            statement.setString(12, placa);
            statement.executeUpdate();
            statement.close();
            System.out.println("Veículo alterado com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removerVeiculo(String placa) {
        String query = "DELETE FROM veiculos WHERE placa = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, placa);
            statement.executeUpdate();
            statement.close();
            System.out.println("Veículo removido com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Veiculo> listarVeiculos() {
    	
    	ArrayList<Veiculo> listaVeiculos = new ArrayList<Veiculo>();
    	
        String query = "SELECT * FROM veiculos";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String placa = resultSet.getString("placa");
                String categoria = resultSet.getString("categoria");
                int numeroPassageiros = resultSet.getInt("passageiros");
                double tamanhoBagageiro = resultSet.getDouble("bagageiro");
                String tipoCambio = resultSet.getString("cambio");
                boolean possuiArCondicionado = resultSet.getBoolean("ar");
                double mediaConsumo = resultSet.getDouble("consumo");
                boolean possuiAirbag = resultSet.getBoolean("airbag");
                boolean possuiFreioABS = resultSet.getBoolean("abs");
                boolean possuiDVD = resultSet.getBoolean("dvd");
                double custoPorDia = resultSet.getDouble("custo");

                Veiculo veiculo = new Veiculo(placa, categoria, numeroPassageiros, tamanhoBagageiro, tipoCambio,
                        possuiArCondicionado, mediaConsumo, possuiAirbag, possuiFreioABS, possuiDVD, custoPorDia);

                listaVeiculos.add(veiculo);
            }
            resultSet.close();
            statement.close();
            
            return listaVeiculos;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
