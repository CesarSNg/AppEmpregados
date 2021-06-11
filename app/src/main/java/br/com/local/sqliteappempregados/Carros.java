package br.com.local.sqliteappempregados;

public class Carros {
    int id;
    String modelo, marca, dataEntrada;
    double km;

    public Carros() {
    }

    public Carros(int id, String modelo, String marca, String dataEntrada, double km) {
        this.id = id;
        this.modelo = modelo;
        this.marca = marca;
        this.dataEntrada = dataEntrada;
        this.km = km;
    }

    public int getId() {
        return id;
    }

    public String getModelo() {
        return modelo;
    }

    public String getMarca() {
        return marca;
    }

    public String getDataEntrada() {
        return dataEntrada;
    }

    public double getKm() {
        return km;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setDataEntrada(String dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public void setKm(double km) {
        this.km = km;
    }
}
