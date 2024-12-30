package ec.epn.edu.lashuequitas.modelo.jpa;

import ec.epn.edu.lashuequitas.modelo.entidades.Comentario;
import ec.epn.edu.lashuequitas.modelo.entidades.Resena;
import ec.epn.edu.lashuequitas.modelo.entidades.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.Serializable;
import java.util.List;

public class ComentarioJPA implements Serializable {

    private EntityManagerFactory emf = null;

    public ComentarioJPA(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public ComentarioJPA() {
        emf = Persistence.createEntityManagerFactory("JavaWebLasHuequitas");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean create(Comentario comentario) {
    EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            // Asociar el comentario con el usuario, si no está desvinculado
            Usuario usuario = comentario.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getId());
                comentario.setUsuario(usuario);
            }
            // Asociar el comentario con la reseña, si no está desvinculado
            Resena resena = comentario.getResena();
            if (resena != null) {
                resena = em.getReference(resena.getClass(), resena.getId());
                comentario.setResena(resena);
            }

            em.persist(comentario);
            em.getTransaction().commit();
            return true;
        }catch (Exception e){
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        }finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comentario> findAllComentariosByResena(Long resenaId) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Comentario c WHERE c.resena.id = :resenaId", Comentario.class)
                    .setParameter("resenaId", resenaId)
                    .getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
