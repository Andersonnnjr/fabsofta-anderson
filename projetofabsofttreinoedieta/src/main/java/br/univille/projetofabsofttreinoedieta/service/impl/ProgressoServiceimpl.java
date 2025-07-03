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

import br.univille.projetofabsofttreinoedieta.entity.Progresso;
import br.univille.projetofabsofttreinoedieta.repository.ProgressoRepository;
import br.univille.projetofabsofttreinoedieta.service.ProgressoService;

@Service
public class ProgressoServiceimpl implements ProgressoService {

    @Autowired
    private ProgressoRepository repository;

    @Value("${fabrica2025.tempfolder}")
    private String tempFolder;
    private Path root = null;

    @Override
    public Progresso save(Progresso progresso) {
        saveFoto(progresso);
        repository.save(progresso);
        return progresso;
    }

    @Override
    public List<Progresso> getAll() {
        var listaProgresso = repository.findAll();
        listaProgresso.stream()
            .filter(progresso -> progresso.getArquivoFoto() != null && !progresso.getArquivoFoto().isEmpty())
            .forEach(this::carregaFoto);
        return listaProgresso;
    }

    @Override
    public Progresso getById(Long id) {
        var retorno = repository.findById(id);
        if (retorno.isPresent()) {
            var progresso = retorno.get();
            carregaFoto(progresso);
            return progresso;
        }
        return null;
    }

    @Override
    public Progresso delete(Long id) {
        var progresso = getById(id);
        if (progresso != null)
            repository.deleteById(id);
        return progresso;
    }

    private void saveFoto(Progresso progresso) {
        if (progresso.getFoto() == null || progresso.getFoto().equals("")) {
            return;
        }

        if (progresso.getMimeType() == null || !progresso.getMimeType().startsWith("image/")) {
            return;
        }

        byte[] imageBytes = Base64.getDecoder().decode(progresso.getFoto());
        InputStream imageStream = new ByteArrayInputStream(imageBytes);

        File dir = new File(tempFolder);
        if (!dir.exists()) {
            dir.mkdir();
        }

        root = Paths.get(tempFolder);
        UUID uuid = UUID.randomUUID();
        String extensao = "jpg";
        if (progresso.getArquivoFoto() != null && progresso.getArquivoFoto().contains(".")) {
            String[] partes = progresso.getArquivoFoto().split("\\.");
            extensao = partes[partes.length - 1];
        }
        String novoNome = String.format("%s.%s", uuid.toString(), extensao);
        Path nomeArquivo = this.root.resolve(novoNome);
        try {
            Files.copy(imageStream, nomeArquivo);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível salvar o arquivo. Error: " + e.getMessage());
        }
        progresso.setArquivoFoto(nomeArquivo.toAbsolutePath().toString());
    }

    private Progresso carregaFoto(Progresso progresso) {
        if (progresso.getArquivoFoto() == null || progresso.getArquivoFoto().equals("")) {
            return progresso;
        }

        File file = new File(progresso.getArquivoFoto());
        if (!file.exists()) {
            return progresso;
        }

        try {
            byte[] imageBytes = Files.readAllBytes(file.toPath());
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            progresso.setFoto(base64Image);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível carregar a foto. Error: " + e.getMessage());
        }

        return progresso;
    }

}
