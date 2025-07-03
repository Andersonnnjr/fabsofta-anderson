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

import br.univille.projetofabsofttreinoedieta.entity.Dieta;
import br.univille.projetofabsofttreinoedieta.repository.DietaRepository;
import br.univille.projetofabsofttreinoedieta.service.DietaService;

@Service
public class DietaServiceimpl implements DietaService {

    @Autowired
    private DietaRepository repository;

    @Value("${fabrica2025.tempfolder}")
    private String tempFolder;
    private Path root = null;

    @Override
    public Dieta save(Dieta dieta) {
        saveFoto(dieta);
        repository.save(dieta);
        return dieta;
    }

    @Override
    public List<Dieta> getAll() {
        var listaDietas = repository.findAll();
        listaDietas.stream()
            .filter(dieta -> dieta.getArquivoFoto() != null && !dieta.getArquivoFoto().isEmpty())
            .forEach(this::carregaFoto);
        return listaDietas;
    }

    @Override
    public Dieta getById(Long id) {
        var retorno = repository.findById(id);
        if (retorno.isPresent()) {
            var dieta = retorno.get();
            carregaFoto(dieta);
            return dieta;
        }
        return null;
    }

    @Override
    public Dieta delete(Long id) {
        var dieta = getById(id);
        if (dieta != null)
            repository.deleteById(id);
        return dieta;
    }

    private void saveFoto(Dieta dieta) {
        if (dieta.getFoto() == null || dieta.getFoto().equals("")) {
            return;
        }

        if (dieta.getMimeType() == null || !dieta.getMimeType().startsWith("image/")) {
            return;
        }

        byte[] imageBytes = Base64.getDecoder().decode(dieta.getFoto());
        InputStream imageStream = new ByteArrayInputStream(imageBytes);

        File dir = new File(tempFolder);
        if (!dir.exists()) {
            dir.mkdir();
        }

        root = Paths.get(tempFolder);
        UUID uuid = UUID.randomUUID();
        String extensao = "jpg";
        if (dieta.getArquivoFoto() != null && dieta.getArquivoFoto().contains(".")) {
            String[] partes = dieta.getArquivoFoto().split("\\.");
            extensao = partes[partes.length - 1];
        }
        String novoNome = String.format("%s.%s", uuid.toString(), extensao);
        Path nomeArquivo = this.root.resolve(novoNome);
        try {
            Files.copy(imageStream, nomeArquivo);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível salvar o arquivo. Error: " + e.getMessage());
        }
        dieta.setArquivoFoto(nomeArquivo.toAbsolutePath().toString());
    }

    private Dieta carregaFoto(Dieta dieta) {
        if (dieta.getArquivoFoto() == null || dieta.getArquivoFoto().equals("")) {
            return dieta;
        }

        File file = new File(dieta.getArquivoFoto());
        if (!file.exists()) {
            return dieta;
        }

        try {
            byte[] imageBytes = Files.readAllBytes(file.toPath());
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            dieta.setFoto(base64Image);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível carregar a foto. Error: " + e.getMessage());
        }

        return dieta;
    }
}
