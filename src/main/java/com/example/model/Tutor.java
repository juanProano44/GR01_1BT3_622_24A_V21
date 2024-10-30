package com.example.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tutor")
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String estadoCuenta;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "tutor_materia",
            joinColumns = @JoinColumn(name = "tutor_id"),
            inverseJoinColumns = @JoinColumn(name = "materia_id")
    )
    private List<Materia> materias;
    private String rolID;

    @OneToMany(mappedBy = "tutor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Tutoria> tutorias = new HashSet<>();

    public String getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(String estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    // Constructor por defecto que asigna 'estadoCuenta' a 'activo'
    public Tutor() {
        this.estadoCuenta = "activo"; // Valor por defecto al crear un alumno
    }
    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRolID() {
        return rolID;
    }

    public void setRolID(String rolID) {
        this.rolID = rolID;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }


    public Set<Tutoria> getTutorias() {
        return tutorias;
    }

    public void setTutorias(Set<Tutoria> tutorias) {
        this.tutorias = tutorias;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
