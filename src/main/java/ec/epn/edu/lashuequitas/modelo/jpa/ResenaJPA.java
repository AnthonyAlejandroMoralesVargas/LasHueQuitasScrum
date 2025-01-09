package ec.epn.edu.lashuequitas.modelo.jpa;

import ec.epn.edu.lashuequitas.modelo.entidades.Resena;
import ec.epn.edu.lashuequitas.modelo.entidades.Usuario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.io.Serializable;
import java.util.List;

public class ResenaJPA implements Serializable {

    private EntityManagerFactory emf;

    // Constructor con EntityManagerFactory
    public ResenaJPA(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // Constructor por defecto que crea el EntityManagerFactory
    public ResenaJPA() {
        this.emf = Persistence.createEntityManagerFactory("JavaWebLasHuequitas");
    }

    // Obtener el EntityManager
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Crear una nueva reseña
    public boolean create(Resena resena) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            // Asociar usuario si existe
            Usuario usuario = resena.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getId());
                resena.setUsuario(usuario);
            }

            em.persist(resena);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Resena> findAllResenasConImagenes() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT r FROM Resena r LEFT JOIN FETCH r.imagenes", Resena.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Consultar todas las reseñas sin imágenes
    public List<Resena> findAllResenas() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT r FROM Resena r", Resena.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Consultar reseña por ID
    public Resena findById(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Resena.class, id);
        } finally {
            em.close();
        }
    }
}
