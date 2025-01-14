
package ec.epn.edu.lashuequitas.modelo.jpa;

import jakarta.persistence.*;
import ec.epn.edu.lashuequitas.modelo.entidades.Resena;
import ec.epn.edu.lashuequitas.modelo.entidades.Usuario;
import java.io.Serializable;
import java.util.List;

public class ResenaJPA implements Serializable {

    private EntityManagerFactory emf = null;

    // Constructor con EntityManagerFactory
    public ResenaJPA(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // Constructor por defecto que crea el EntityManagerFactory
    public ResenaJPA() {
        emf = Persistence.createEntityManagerFactory("JavaWebLasHuequitas");
    }

    // Obtener el EntityManager
    public EntityManager getEntityManager() {

        return emf.createEntityManager();
    }

    // Método para crear una nueva reseña
    public boolean create(Resena resena) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            // Asociar la reseña con el usuario, si no está desvinculado
            Usuario usuario = resena.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getId());
                resena.setUsuario(usuario);
            }

            em.persist(resena); // Persistir la reseña
            em.getTransaction().commit();
            return true; // Operación exitosa
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Revertir en caso de error
            }
            return false; // Operación fallida
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Resena> findAllResenas() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT r FROM Resena r", Resena.class)
                    .getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Método para buscar una reseña por ID
    public Resena findById(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Resena.class, id); // Busca y devuelve la reseña por ID
        } finally {
            if (em != null) {
                em.close(); // Asegura que el EntityManager se cierre
            }
        }
    }
}