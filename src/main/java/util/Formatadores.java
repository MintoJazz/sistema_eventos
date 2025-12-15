package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.http.UploadedFile;

public class Formatadores {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <K, V> Map<K, V> mapear(List<V> lista, Function<V, K> extratorDeChave) {
        return lista.stream().collect(Collectors.toMap(extratorDeChave, item -> item));
    }

    public static String data2String(LocalDate data) {
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static String dataTime2String(LocalDateTime data) {
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public static Map<String, Object> parseJsonStringToMap(String jsonString) {
        if (jsonString != null && !jsonString.isBlank() && !jsonString.equals("{}")) try {
            return objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Falha ao parsear a string JSON: " + jsonString, e);
        } return new HashMap<>();
    }
    
    public static String parseMapToJsonString(Map<String, Object> map) {
        if (map != null) try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Falha ao converter Map para String JSON", e);
        } return null;
    }

    public static <Classe> Classe json2Classe(String jsonDados, Class<Classe> classe) {
        if (jsonDados != null && !jsonDados.isBlank()) try {
            return objectMapper.readValue(jsonDados, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Falha ao parsear a string JSON para " + classe.getName(), e);
        } return null;
    }
    
    public static String arquivoParaJsonString(UploadedFile arquivo) {
        if (arquivo != null) try {
            byte[] bytes = arquivo.content().readAllBytes();
            String base64Content = Base64.getEncoder().encodeToString(bytes);

            Map<String, Object> docInfo = new HashMap<>();
            docInfo.put("nome_arquivo", arquivo.filename());
            docInfo.put("tipo_mime", arquivo.contentType());
            docInfo.put("tamanho_bytes", arquivo.size());
            docInfo.put("conteudo_base64", base64Content);

            return parseMapToJsonString(Map.of("documentacao_legal", docInfo));

        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter arquivo para JSONB: " + e.getMessage(), e);
        } return "{}";
    }

    public static Map<String, Object> jsonParaArquivo(String jsonString) {
        if (jsonString != null && !jsonString.isBlank()) try {
            Map<String, Object> root = parseJsonStringToMap(jsonString);

            if (!root.containsKey("documentacao_legal")) return null;

            @SuppressWarnings("unchecked") Map<String, Object> doc = (Map<String, Object>) root.get("documentacao_legal");
            String base64 = (String) doc.get("conteudo_base64");
            if (base64 == null || base64.isBlank()) return null;

            byte[] bytes = Base64.getDecoder().decode(base64);
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("bytes", bytes);
            resultado.put("nome", (String) doc.get("nome_arquivo"));
            resultado.put("tipo", (String) doc.get("tipo_mime"));

            return resultado;

        } catch (Exception e) {
            System.err.println("Erro ao converter JSON para Arquivo: " + e.getMessage());
            return null;
        } return null;
    }
}