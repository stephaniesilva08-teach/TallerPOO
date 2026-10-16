package com.taller.poo;

public class Estudiante {
    private String identificacion;
    private String nombreCompleto;
    private double promedio;
    private boolean estadoMatricula;

    public Estudiante(){
    }
    public Estudiante(String identificacion,String nombreCompleto,double promedio,boolean estadoMatricula){
        this.identificacion = identificacion;
        this.nombreCompleto = nombreCompleto;
        this.promedio = promedio;
        this.estadoMatricula = estadoMatricula;
    }

    public String getIdentificacion(){
        return this.identificacion;
    }
    public void setIdentificacion(String identificacion){
        this.identificacion = identificacion;
    }

    public String getNombreCompleto(){
        return this.nombreCompleto;
    }
    public  void setNombreCompleto(String nombreCompleto){
        this.nombreCompleto = nombreCompleto;
    }

    public double getPromedio(){
        return this.promedio;
    }
    public void setPromedio(double promedio){
        this.promedio = promedio;
    }

    public boolean isEstadoMatricula(){
        return this.estadoMatricula;
    }
    public void setEstadoMatricula(boolean estadoMatricula){
        this.estadoMatricula = estadoMatricula;
    }

    public  String obtenerReporteAcademicos(){
        StringBuilder Notas = new StringBuilder();
        Notas.append(String.format("%s, %.2f, %b", this.nombreCompleto, this.promedio, this.estadoMatricula));
        return Notas.toString();
    }
}

