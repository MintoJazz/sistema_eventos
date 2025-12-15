package usuario.parser;

import persistencias.IParser;
import usuario.Usuario;
import usuario.dto.UsuarioPerfil;

public class UsuarioToPerfil implements IParser<Usuario, UsuarioPerfil> {

    @Override public UsuarioPerfil toDTO(Usuario entidade) {
        UsuarioPerfil dto = new UsuarioPerfil();

        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        dto.setCpf(entidade.getCpf());
        dto.setEmail(entidade.getEmail());

        return dto;
    }

    @Override public Usuario toEntidade(UsuarioPerfil dto) {
        throw new UnsupportedOperationException("UsuarioPerfil é apenas para leitura (View). Use UsuarioForm para persistência.");
    }

    @Override public Class<UsuarioPerfil> getDTOClass() {
        return UsuarioPerfil.class;
    }
}