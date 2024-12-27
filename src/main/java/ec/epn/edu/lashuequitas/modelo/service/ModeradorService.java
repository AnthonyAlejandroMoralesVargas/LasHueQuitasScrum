package ec.epn.edu.lashuequitas.modelo.service;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ModeradorService {
    private static final String[] PALABRAS_OFENSIVAS = {
            "puta", "zorra", "mierda", "tonto", "estúpida", "idiota", "imbécil", "feo", "asqueroso", "burro", "tarado", "bobo",
            "adefecioso", "malo", "estúpido", "zopenco", "patán", "cretino", "baboso", "loco", "menso", "cobarde",
            "holgazán", "gusano", "mocoso", "caradura", "bruto", "mugroso", "animal", "cerdo",
            "vago", "chismoso", "hipócrita", "mentiroso", "rata", "traidor", "insolente",
            "ignorante", "payaso", "sucio", "maleducado", "necio", "desgraciado", "bastardo", "basura"
    };

    private static final Pattern PATRON_OFENSIVO;

    static {
        // Construcción del patrón usando delimitadores de palabras
        String pattern = "\\b(" + String.join("|", PALABRAS_OFENSIVAS) + ")\\b";
        PATRON_OFENSIVO = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    }

    public boolean verificarOfensivo(String contenido) {
        // Verificar si el contenido es nulo o vacío
        if (contenido == null || contenido.trim().isEmpty()) {
            return false;
        }

        // Buscar palabras ofensivas en el contenido
        Matcher matcher = PATRON_OFENSIVO.matcher(contenido);
        return matcher.find();
    }
}
