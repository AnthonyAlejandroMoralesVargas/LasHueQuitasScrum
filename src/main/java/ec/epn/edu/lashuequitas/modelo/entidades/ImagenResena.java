package ec.epn.edu.lashuequitas.modelo.entidades;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Base64;
import java.util.List;

@Entity
@Table(name = "imagenes_resena")
public class ImagenResena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resena_id", nullable = false)
    private Resena resena;

    @Lob
    @Column(name = "datos_imagen", nullable = true)
    private byte[] datosImagen;


    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Resena getResena() {
        return resena;
    }

    public void setResena(Resena resena) {
        this.resena = resena;
    }




    public byte[] getDatosImagen() {
        return datosImagen;
    }


    public void setDatosImagen(byte[] datosImagen) {
        this.datosImagen = datosImagen;
    }

    public String getBase64DatosImagen() {
        return Base64.getEncoder().encodeToString(datosImagen);
    }
}
