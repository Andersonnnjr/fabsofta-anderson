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

import br.univille.projetofabsofttreinoedieta.entity.Refeicao;
import br.univille.projetofabsofttreinoedieta.repository.RefeicaoRepository;
import br.univille.projetofabsofttreinoedieta.service.RefeicaoService;

@Service
public class RefeicaoServiceimpl implements RefeicaoService {

    @Autowired
    private RefeicaoRepository repository;

    @Value("${fabrica2025.tempfolder}")
    private String tempFolder;
    private Path root = null;

    @Override
    public Refeicao save(Refeicao refeicao) {
        saveFoto(refeicao);
        repository.save(refeicao);
        return refeicao;
    }

    @Override
    public List<Refeicao> getAll() {
        var listaRefeicoes = repository.findAll();
        listaRefeicoes.stream()
            .filter(refeicao -> refeicao.getArquivoFoto() != null && !refeicao.getArquivoFoto().isEmpty())
            .forEach(this::carregaFoto);
        return listaRefeicoes;
    }

    @Override
    public Refeicao getById(Long id) {
        var retorno = repository.findById(id);
        if (retorno.isPresent()) {
            var refeicao = retorno.get();
            carregaFoto(refeicao);
            return refeicao;
        }
        return null;
    }

    @Override
    public Refeicao delete(Long id) {
        var refeicao = getById(id);
        if (refeicao != null)
            repository.deleteById(id);
        return refeicao;
    }

    private void saveFoto(Refeicao refeicao) {
        if (refeicao.getFoto() == null || refeicao.getFoto().equals("")) {
            return;
        }

        if (refeicao.getMimeType() == null || !refeicao.getMimeType().startsWith("image/")) {
            return;
        }

        byte[] imageBytes = Base64.getDecoder().decode(refeicao.getFoto());
        InputStream imageStream = new ByteArrayInputStream(imageBytes);

        File dir = new File(tempFolder);
        if (!dir.exists()) {
            dir.mkdir();
        }

        root = Paths.get(tempFolder);
        UUID uuid = UUID.randomUUID();
        String extensao = "jpg";
        if (refeicao.getArquivoFoto() != null && refeicao.getArquivoFoto().contains(".")) {
            String[] partes = refeicao.getArquivoFoto().split("\\.");
            extensao = partes[partes.length - 1];
        }
        String novoNome = String.format("%s.%s", uuid.toString(), extensao);
        Path nomeArquivo = this.root.resolve(novoNome);
        try {
            Files.copy(imageStream, nomeArquivo);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível salvar o arquivo. Error: " + e.getMessage());
        }
        refeicao.setArquivoFoto(nomeArquivo.toAbsolutePath().toString());
    }

    private Refeicao carregaFoto(Refeicao refeicao) {
        if (refeicao.getArquivoFoto() == null || refeicao.getArquivoFoto().equals("")) {
            return refeicao;
        }

        File file = new File(refeicao.getArquivoFoto());
        if (!file.exists()) {
            return refeicao;
        }

        try {
            byte[] imageBytes = Files.readAllBytes(file.toPath());
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            refeicao.setFoto(base64Image);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível carregar a foto. Error: " + e.getMessage());
        }

        return refeicao;
    }
}
