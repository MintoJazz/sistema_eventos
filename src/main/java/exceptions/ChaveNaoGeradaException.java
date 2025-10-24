package exceptions;

/**
 * Exceção lançada quando uma operação de inserção no banco de dados
 * falha em retornar a chave primária gerada esperada.
 * Estende RuntimeException para ser uma exceção não checada (unchecked).
 */
public class ChaveNaoGeradaException extends RuntimeException {
    
    public ChaveNaoGeradaException(String mensagem) {
        super(mensagem);
    }

    public ChaveNaoGeradaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}