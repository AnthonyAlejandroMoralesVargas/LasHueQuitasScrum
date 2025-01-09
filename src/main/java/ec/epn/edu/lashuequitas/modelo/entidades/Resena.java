package ec.epn.edu.lashuequitas.modelo.entidades;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "resenas")
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String restaurante;

    @Column(nullable = false)
    private String tipoComida;

    @Column(nullable = false, length = 1000)
    private String descripcion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;



    @OneToMany(mappedBy = "resena", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagenResena> imagenes = new ArrayList<>();


    // Constructores
    public Resena() {
    }

    public Resena(String restaurante, String tipoComida, String descripcion, Usuario usuario) {
        this.restaurante = restaurante;
        this.tipoComida = tipoComida;
        this.descripcion = descripcion;
        this.fechaCreacion = new Date();
        this.usuario = usuario;
        this.imagenes = new ArrayList<>();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRestaurante() {
        return restaurante;
    }

    public List<ImagenResena> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<ImagenResena> imagenes) {
        this.imagenes = imagenes;
    }
    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }

    public String getTipoComida() {
        return tipoComida;
    }

    public void setTipoComida(String tipoComida) {
        this.tipoComida = tipoComida;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
