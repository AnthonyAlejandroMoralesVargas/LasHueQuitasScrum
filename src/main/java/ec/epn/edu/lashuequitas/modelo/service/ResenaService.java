package ec.epn.edu.lashuequitas.modelo.service;

import ec.epn.edu.lashuequitas.modelo.entidades.Resena;
import ec.epn.edu.lashuequitas.modelo.jpa.ResenaJPA;

public class ResenaService {

    private final ResenaJPA resenaJPA;

    public ResenaService() {
        this.resenaJPA = new ResenaJPA();
    }

    public ResenaService(ResenaJPA resenaJPA) {
        this.resenaJPA = resenaJPA;
    }

    public boolean crear(Resena resena) {
        return resenaJPA.create(resena);
    }
}

