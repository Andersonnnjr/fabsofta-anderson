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

import br.univille.projetofabsofttreinoedieta.entity.Treino;
import br.univille.projetofabsofttreinoedieta.repository.TreinoRepository;
import br.univille.projetofabsofttreinoedieta.service.TreinoService;

@Service
public class TreinoServiceimpl implements TreinoService {

    @Autowired
    private TreinoRepository repository;

    @Value("${fabrica2025.tempfolder}")
    private String tempFolder;
    private Path root = null;

    @Override
    public Treino save(Treino treino) {
        saveFoto(treino);
        repository.save(treino);
        return treino;
    }

    @Override
    public List<Treino> getAll() {
        var listaTreinos = repository.findAll();
        listaTreinos.stream()
            .filter(treino -> treino.getArquivoFoto() != null && !treino.getArquivoFoto().isEmpty())
            .forEach(this::carregaFoto);
        return listaTreinos;
    }

    @Override
    public Treino getById(Long id) {
        var retorno = repository.findById(id);
        if (retorno.isPresent()) {
            var treino = retorno.get();
            carregaFoto(treino);
            return treino;
        }
        return null;
    }

    @Override
    public Treino delete(Long id) {
        var treino = getById(id);
        if (treino != null)
            repository.deleteById(id);
        return treino;
    }

    private void saveFoto(Treino treino) {
        if (treino.getFoto() == null || treino.getFoto().equals("")) {
            return;
        }

        if (treino.getMimeType() == null || !treino.getMimeType().startsWith("image/")) {
            return;
        }

        byte[] imageBytes = Base64.getDecoder().decode(treino.getFoto());
        InputStream imageStream = new ByteArrayInputStream(imageBytes);

        File dir = new File(tempFolder);
        if (!dir.exists()) {
            dir.mkdir();
        }

        root = Paths.get(tempFolder);
        UUID uuid = UUID.randomUUID();
        String extensao = "jpg";
        if (treino.getArquivoFoto() != null && treino.getArquivoFoto().contains(".")) {
            String[] partes = treino.getArquivoFoto().split("\\.");
            extensao = partes[partes.length - 1];
        }
        String novoNome = String.format("%s.%s", uuid.toString(), extensao);
        Path nomeArquivo = this.root.resolve(novoNome);
        try {
            Files.copy(imageStream, nomeArquivo);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível salvar o arquivo. Error: " + e.getMessage());
        }
        treino.setArquivoFoto(nomeArquivo.toAbsolutePath().toString());
    }

    private Treino carregaFoto(Treino treino) {
        if (treino.getArquivoFoto() == null || treino.getArquivoFoto().equals("")) {
            return treino;
        }

        File file = new File(treino.getArquivoFoto());
        if (!file.exists()) {
            return treino;
        }

        try {
            byte[] imageBytes = Files.readAllBytes(file.toPath());
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            treino.setFoto(base64Image);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível carregar a foto. Error: " + e.getMessage());
        }

        return treino;
    }
}
