package com.cinthya.crudiot;

public class alumnos {
    String alu_ID;
    String matricula;
    String nombre;
    String correo;
    long telefono;
    String direccion;

    public alumnos() {
    }

    public alumnos(String alu_ID, String matricula, String nombre, String correo, long telefono, String direccion) {
        this.alu_ID = alu_ID;
    }

    public String getAlu_ID() {
        return alu_ID;
    }

    public void setAlu_ID(String alu_ID) {
        this.alu_ID = alu_ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return nombre + " " + correo;
    }
}
