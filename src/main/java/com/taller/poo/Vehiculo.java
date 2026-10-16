package com.taller.poo;

public class Vehiculo {
    private String marcaVehiculo;
    private String modeloVehiculo;
    private double kilometrajeActual;
    private int velocidadMaxima;
    private boolean estadoMotor;
/* escogi primitivos para que el consumo de memoria sea menor */

    public Vehiculo(String marcaVehiculo, String modeloVehiculo, double kilometrajeActual, int velocidadMaxima, boolean estadoMotor) {
        this.marcaVehiculo = marcaVehiculo;
        this.modeloVehiculo = modeloVehiculo;
        this.kilometrajeActual = kilometrajeActual;
        this.velocidadMaxima = velocidadMaxima;
        this.estadoMotor = estadoMotor;
    }

    public String getMarcaVehiculo() {
        return marcaVehiculo;
    }

    public void setMarcaVehiculo(String marcaVehiculo) {
        this.marcaVehiculo = marcaVehiculo;
    }

    public String getModeloVehiculo() {
        return modeloVehiculo;
    }

    public void setModeloVehiculo(String modeloVehiculo) {
        this.modeloVehiculo = modeloVehiculo;
    }

    public double getKilometrajeActual() {
        return kilometrajeActual;
    }

    public void setKilometrajeActual(double kilometrajeActual) {
        this.kilometrajeActual = kilometrajeActual;
    }

    public int getVelocidadMaxima() {
        return velocidadMaxima;
    }

    public void setVelocidadMaxima(int velocidadMaxima) {
        this.velocidadMaxima = velocidadMaxima;
    }

    public boolean isEstadoMotor() {
        return estadoMotor;
    }

    public void setEstadoMotor(boolean estadoMotor) {
        this.estadoMotor = estadoMotor;
    }

    public Vehiculo() {
    }

}
