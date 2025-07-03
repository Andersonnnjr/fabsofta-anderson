package br.univille.projetofabsofttreinoedieta.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.univille.projetofabsofttreinoedieta.entity.Usuario;
import br.univille.projetofabsofttreinoedieta.repository.UsuarioRepository;
import br.univille.projetofabsofttreinoedieta.service.UsuarioService;

@Service
public class UsuarioServiceimpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Value("${fabrica2025.tempfolder}")
    private String tempFolder;
    private Path root = null;

    @Override
    public Usuario save(Usuario usuario) {
        saveFoto(usuario);
        repository.save(usuario);
        return usuario;
    }

    @Override
    public List<Usuario> getAll() {
        var listaUsuarios = repository.findAll();
        listaUsuarios.stream()
            .filter(usuario -> usuario.getArquivoFoto() != null && !usuario.getArquivoFoto().isEmpty())
            .forEach(this::carregaFoto);
        return listaUsuarios;
    }

    @Override
    public Usuario getById(Long id) {
        var retorno = repository.findById(id);
        if (retorno.isPresent()) {
            var usuario = retorno.get();
            carregaFoto(usuario);
            return usuario;
        }
        return null;
    }

    @Override
    public Usuario delete(Long id) {
        var usuario = getById(id);
        if (usuario != null)
            repository.deleteById(id);
        return usuario;
    }

    private void saveFoto(Usuario usuario) {
        if (usuario.getFoto() == null || usuario.getFoto().equals("")) {
            return;
        }

        if (usuario.getMimeType() == null || !usuario.getMimeType().startsWith("image/")) {
            return;
        }

        byte[] imageBytes = Base64.getDecoder().decode(usuario.getFoto());
        InputStream imageStream = new ByteArrayInputStream(imageBytes);

        File dir = new File(tempFolder);
        if (!dir.exists()) {
            dir.mkdir();
        }

        root = Paths.get(tempFolder);
        UUID uuid = UUID.randomUUID();
        String extensao = "jpg";
        if (usuario.getArquivoFoto() != null && usuario.getArquivoFoto().contains(".")) {
            String[] partes = usuario.getArquivoFoto().split("\\.");
            extensao = partes[partes.length - 1];
        }
        String novoNome = String.format("%s.%s", uuid.toString(), extensao);
        Path nomeArquivo = this.root.resolve(novoNome);
        try {
            Files.copy(imageStream, nomeArquivo);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível salvar o arquivo. Error: " + e.getMessage());
        }
        usuario.setArquivoFoto(nomeArquivo.toAbsolutePath().toString());
    }

    private Usuario carregaFoto(Usuario usuario) {
        if (usuario.getArquivoFoto() == null || usuario.getArquivoFoto().equals("")) {
            return usuario;
        }

        File file = new File(usuario.getArquivoFoto());
        if (!file.exists()) {
            return usuario;
        }

        try {
            byte[] imageBytes = Files.readAllBytes(file.toPath());
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            usuario.setFoto(base64Image);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível carregar a foto. Error: " + e.getMessage());
        }

        return usuario;
    }
}
