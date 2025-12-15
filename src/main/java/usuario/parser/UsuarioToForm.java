package usuario.parser;

import persistencias.IParser;
import usuario.Usuario;
import usuario.dto.UsuarioForm;

public class UsuarioToForm implements IParser<Usuario, UsuarioForm> {

    @Override public UsuarioForm toDTO(Usuario entidade) {
        UsuarioForm dto = new UsuarioForm();
        
        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        dto.setCpf(entidade.getCpf());
        dto.setEmail(entidade.getEmail());
        dto.setSenha(entidade.getSenha()); 
        
        return dto;
    }

    @Override public Usuario toEntidade(UsuarioForm dto) {
        return new Usuario(
            dto.getId(),
            dto.getNome(),
            dto.getCpf(),
            dto.getEmail(),
            dto.getSenha()
        );
    }

    @Override public Class<UsuarioForm> getDTOClass() {
        return UsuarioForm.class;
    }
}