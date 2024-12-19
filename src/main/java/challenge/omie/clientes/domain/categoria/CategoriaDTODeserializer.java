package challenge.omie.clientes.domain.categoria;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class CategoriaDTODeserializer extends JsonDeserializer<CategoriaDTO> {
    @Override
    public CategoriaDTO deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        
        if (node.isNumber()) {
            return new CategoriaDTO(node.asLong(), null);
        } else if (node.isObject()) {
            Long id = node.has("id") ? node.get("id").asLong() : null;
            String nome = node.has("nome") ? node.get("nome").asText() : null;
            return new CategoriaDTO(id, nome);
        }

        context.reportInputMismatch(CategoriaDTO.class, "Invalid data for CategoriaDTO: " + node.toString());
        return null;
    }
}
