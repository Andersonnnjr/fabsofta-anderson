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

import br.univille.projetofabsofttreinoedieta.entity.Relatorio;
import br.univille.projetofabsofttreinoedieta.repository.RelatorioRepository;
import br.univille.projetofabsofttreinoedieta.service.RelatorioService;

@Service
public class RelatorioServiceimpl implements RelatorioService {

    @Autowired
    private RelatorioRepository repository;

    @Value("${fabrica2025.tempfolder}")
    private String tempFolder;
    private Path root = null;

    @Override
    public Relatorio save(Relatorio relatorio) {
        saveFoto(relatorio);
        repository.save(relatorio);
        return relatorio;
    }

    @Override
    public List<Relatorio> getAll() {
        var listaRelatorios = repository.findAll();
        listaRelatorios.stream()
            .filter(relatorio -> relatorio.getArquivoFoto() != null && !relatorio.getArquivoFoto().isEmpty())
            .forEach(this::carregaFoto);
        return listaRelatorios;
    }

    @Override
    public Relatorio getById(Long id) {
        var retorno = repository.findById(id);
        if (retorno.isPresent()) {
            var relatorio = retorno.get();
            carregaFoto(relatorio);
            return relatorio;
        }
        return null;
    }

    @Override
    public Relatorio delete(Long id) {
        var relatorio = getById(id);
        if (relatorio != null)
            repository.deleteById(id);
        return relatorio;
    }

    private void saveFoto(Relatorio relatorio) {
        if (relatorio.getFoto() == null || relatorio.getFoto().equals("")) {
            return;
        }

        if (relatorio.getMimeType() == null || !relatorio.getMimeType().startsWith("image/")) {
            return;
        }

        byte[] imageBytes = Base64.getDecoder().decode(relatorio.getFoto());
        InputStream imageStream = new ByteArrayInputStream(imageBytes);

        File dir = new File(tempFolder);
        if (!dir.exists()) {
            dir.mkdir();
        }

        root = Paths.get(tempFolder);
        UUID uuid = UUID.randomUUID();
        String extensao = "jpg";
        if (relatorio.getArquivoFoto() != null && relatorio.getArquivoFoto().contains(".")) {
            String[] partes = relatorio.getArquivoFoto().split("\\.");
            extensao = partes[partes.length - 1];
        }
        String novoNome = String.format("%s.%s", uuid.toString(), extensao);
        Path nomeArquivo = this.root.resolve(novoNome);
        try {
            Files.copy(imageStream, nomeArquivo);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível salvar o arquivo. Error: " + e.getMessage());
        }
        relatorio.setArquivoFoto(nomeArquivo.toAbsolutePath().toString());
    }

    private Relatorio carregaFoto(Relatorio relatorio) {
        if (relatorio.getArquivoFoto() == null || relatorio.getArquivoFoto().equals("")) {
            return relatorio;
        }

        File file = new File(relatorio.getArquivoFoto());
        if (!file.exists()) {
            return relatorio;
        }

        try {
            byte[] imageBytes = Files.readAllBytes(file.toPath());
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            relatorio.setFoto(base64Image);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível carregar a foto. Error: " + e.getMessage());
        }

        return relatorio;
    }

}
