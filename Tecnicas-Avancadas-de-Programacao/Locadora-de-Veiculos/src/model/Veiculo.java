package model;

public class Veiculo {
	private String placa;
    private String categoria;
    private int numeroMaxPassageiros;
    private double tamanhoBagageiro;
    private String tipoCambio;
    private boolean possuiArCondicionado;
    private double mediaConsumo;
    private boolean possuiAirbag;
    private boolean possuiFreioABS;
    private boolean possuiDVD;
    private double custoPorDia;

    // Construtor
    public Veiculo(String placa, String categoria, int numeroMaxPassageiros, double tamanhoBagageiro, String tipoCambio,
                   boolean possuiArCondicionado, double mediaConsumo, boolean possuiAirbag, boolean possuiFreioABS,
                   boolean possuiDVD, double custoPorDia) {
    	this.placa = placa;
        this.categoria = categoria;
        this.numeroMaxPassageiros = numeroMaxPassageiros;
        this.tamanhoBagageiro = tamanhoBagageiro;
        this.tipoCambio = tipoCambio;
        this.possuiArCondicionado = possuiArCondicionado;
        this.mediaConsumo = mediaConsumo;
        this.possuiAirbag = possuiAirbag;
        this.possuiFreioABS = possuiFreioABS;
        this.possuiDVD = possuiDVD;
        this.custoPorDia = custoPorDia;
    }

    // Getters e Setters
    public String getPlaca() {
    	return placa;
    }
    
    public void setPlaca(String placa) {
    	this.placa = placa;
    }
    
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getNumeroMaxPassageiros() {
        return numeroMaxPassageiros;
    }

    public void setNumeroMaxPassageiros(int numeroMaxPassageiros) {
        this.numeroMaxPassageiros = numeroMaxPassageiros;
    }

    public double getTamanhoBagageiro() {
        return tamanhoBagageiro;
    }

    public void setTamanhoBagageiro(double tamanhoBagageiro) {
        this.tamanhoBagageiro = tamanhoBagageiro;
    }

    public String getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(String tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public boolean isPossuiArCondicionado() {
        return possuiArCondicionado;
    }

    public void setPossuiArCondicionado(boolean possuiArCondicionado) {
        this.possuiArCondicionado = possuiArCondicionado;
    }

    public double getMediaConsumo() {
        return mediaConsumo;
    }

    public void setMediaConsumo(double mediaConsumo) {
        this.mediaConsumo = mediaConsumo;
    }

    public boolean isPossuiAirbag() {
        return possuiAirbag;
    }

    public void setPossuiAirbag(boolean possuiAirbag) {
        this.possuiAirbag = possuiAirbag;
    }

    public boolean isPossuiFreioABS() {
        return possuiFreioABS;
    }

    public void setPossuiFreioABS(boolean possuiFreioABS) {
        this.possuiFreioABS = possuiFreioABS;
    }

    public boolean isPossuiDVD() {
        return possuiDVD;
    }

    public void setPossuiDVD(boolean possuiDVD) {
        this.possuiDVD = possuiDVD;
    }

    public double getCustoPorDia() {
        return custoPorDia;
    }

    public void setCustoPorDia(double custoPorDia) {
        this.custoPorDia = custoPorDia;
    }
}
