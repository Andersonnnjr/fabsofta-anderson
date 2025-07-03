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

import br.univille.projetofabsofttreinoedieta.entity.Alimento;
import br.univille.projetofabsofttreinoedieta.repository.AlimentoRepository;
import br.univille.projetofabsofttreinoedieta.service.AlimentoService;

@Service
public class AlimentoServiceimpl implements AlimentoService {

    @Autowired
    private AlimentoRepository repository;

    @Value("${fabrica2025.tempfolder}")
    private String tempFolder;
    private Path root = null;

    @Override
    public Alimento save(Alimento alimento) {
        saveFoto(alimento);
        repository.save(alimento);
        return alimento;
    }

    @Override
    public List<Alimento> getAll() {
        var listaAlimentos = repository.findAll();
        listaAlimentos.stream()
            .filter(alimento -> alimento.getArquivoFoto() != null && !alimento.getArquivoFoto().isEmpty())
            .forEach(this::carregaFoto);
        return listaAlimentos;
    }

    @Override
    public Alimento getById(Long id) {
        var retorno = repository.findById(id);
        if (retorno.isPresent()) {
            var alimento = retorno.get();
            carregaFoto(alimento);
            return alimento;
        }
        return null;
    }

    @Override
    public Alimento delete(Long id) {
        var alimento = getById(id);
        if (alimento != null)
            repository.deleteById(id);
        return alimento;
    }

    private void saveFoto(Alimento alimento) {
        if (alimento.getFoto() == null || alimento.getFoto().equals("")) {
            return;
        }

        if (alimento.getMimeType() == null || !alimento.getMimeType().startsWith("image/")) {
            return;
        }

        byte[] imageBytes = Base64.getDecoder().decode(alimento.getFoto());
        InputStream imageStream = new ByteArrayInputStream(imageBytes);

        File dir = new File(tempFolder);
        if (!dir.exists()) {
            dir.mkdir();
        }

        root = Paths.get(tempFolder);
        UUID uuid = UUID.randomUUID();
        String extensao = "jpg";
        if (alimento.getArquivoFoto() != null && alimento.getArquivoFoto().contains(".")) {
            String[] partes = alimento.getArquivoFoto().split("\\.");
            extensao = partes[partes.length - 1];
        }
        String novoNome = String.format("%s.%s", uuid.toString(), extensao);
        Path nomeArquivo = this.root.resolve(novoNome);
        try {
            Files.copy(imageStream, nomeArquivo);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível salvar o arquivo. Error: " + e.getMessage());
        }
        alimento.setArquivoFoto(nomeArquivo.toAbsolutePath().toString());
    }

    private Alimento carregaFoto(Alimento alimento) {
        if (alimento.getArquivoFoto() == null || alimento.getArquivoFoto().equals("")) {
            return alimento;
        }

        File file = new File(alimento.getArquivoFoto());
        if (!file.exists()) {
            return alimento;
        }

        try {
            byte[] imageBytes = Files.readAllBytes(file.toPath());
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            alimento.setFoto(base64Image);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível carregar a foto. Error: " + e.getMessage());
        }

        return alimento;
    }
}
