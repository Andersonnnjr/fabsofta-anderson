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

import br.univille.projetofabsofttreinoedieta.entity.Configuracao;
import br.univille.projetofabsofttreinoedieta.repository.ConfiguracaoRepository;
import br.univille.projetofabsofttreinoedieta.service.ConfiguracaoService;

@Service
public class ConfiguracaoServiceimpl implements ConfiguracaoService {

    @Autowired
    private ConfiguracaoRepository repository;

    @Value("${fabrica2025.tempfolder}")
    private String tempFolder;
    private Path root = null;

    @Override
    public Configuracao save(Configuracao configuracao) {
        saveFoto(configuracao);
        repository.save(configuracao);
        return configuracao;
    }

    @Override
    public List<Configuracao> getAll() {
        var listaConfiguracoes = repository.findAll();
        listaConfiguracoes.stream()
            .filter(config -> config.getArquivoFoto() != null && !config.getArquivoFoto().isEmpty())
            .forEach(this::carregaFoto);
        return listaConfiguracoes;
    }

    @Override
    public Configuracao getById(Long id) {
        var retorno = repository.findById(id);
        if (retorno.isPresent()) {
            var config = retorno.get();
            carregaFoto(config);
            return config;
        }
        return null;
    }

    @Override
    public Configuracao delete(Long id) {
        var config = getById(id);
        if (config != null)
            repository.deleteById(id);
        return config;
    }

    private void saveFoto(Configuracao config) {
        if (config.getFoto() == null || config.getFoto().equals("")) {
            return;
        }

        if (config.getMimeType() == null || !config.getMimeType().startsWith("image/")) {
            return;
        }

        byte[] imageBytes = Base64.getDecoder().decode(config.getFoto());
        InputStream imageStream = new ByteArrayInputStream(imageBytes);

        File dir = new File(tempFolder);
        if (!dir.exists()) {
            dir.mkdir();
        }

        root = Paths.get(tempFolder);
        UUID uuid = UUID.randomUUID();
        String extensao = "jpg";
        if (config.getArquivoFoto() != null && config.getArquivoFoto().contains(".")) {
            String[] partes = config.getArquivoFoto().split("\\.");
            extensao = partes[partes.length - 1];
        }
        String novoNome = String.format("%s.%s", uuid.toString(), extensao);
        Path nomeArquivo = this.root.resolve(novoNome);
        try {
            Files.copy(imageStream, nomeArquivo);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível salvar o arquivo. Error: " + e.getMessage());
        }
        config.setArquivoFoto(nomeArquivo.toAbsolutePath().toString());
    }

    private Configuracao carregaFoto(Configuracao config) {
        if (config.getArquivoFoto() == null || config.getArquivoFoto().equals("")) {
            return config;
        }

        File file = new File(config.getArquivoFoto());
        if (!file.exists()) {
            return config;
        }

        try {
            byte[] imageBytes = Files.readAllBytes(file.toPath());
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            config.setFoto(base64Image);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível carregar a foto. Error: " + e.getMessage());
        }

        return config;
    }

}
