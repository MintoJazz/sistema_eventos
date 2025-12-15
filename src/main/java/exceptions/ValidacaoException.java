package exceptions;

import java.util.Map;

public class ValidacaoException extends RuntimeException {
    
    private final Map<String, String> errors;

    // Construtor aceitando EXATAMENTE o que seu service devolve
    public ValidacaoException(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}