package exceptions;

public class PermissaoNegadaException extends RuntimeException {  
    public PermissaoNegadaException(String mensagem) {
        super(mensagem);
    }

    public PermissaoNegadaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}