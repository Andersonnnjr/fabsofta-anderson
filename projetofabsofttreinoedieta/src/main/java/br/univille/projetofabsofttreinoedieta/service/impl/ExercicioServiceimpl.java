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

import br.univille.projetofabsofttreinoedieta.entity.Exercicio;
import br.univille.projetofabsofttreinoedieta.repository.ExercicioRepository;
import br.univille.projetofabsofttreinoedieta.service.ExercicioService;

@Service
public class ExercicioServiceimpl implements ExercicioService {

    @Autowired
    private ExercicioRepository repository;

    @Value("${fabrica2025.tempfolder}")
    private String tempFolder;
    private Path root = null;

    @Override
    public Exercicio save(Exercicio exercicio) {
        saveFoto(exercicio);
        repository.save(exercicio);
        return exercicio;
    }

    @Override
    public List<Exercicio> getAll() {
        var listaExercicios = repository.findAll();
        listaExercicios.stream()
            .filter(exercicio -> exercicio.getArquivoFoto() != null && !exercicio.getArquivoFoto().isEmpty())
            .forEach(this::carregaFoto);
        return listaExercicios;
    }

    @Override
    public Exercicio getById(Long id) {
        var retorno = repository.findById(id);
        if (retorno.isPresent()) {
            var exercicio = retorno.get();
            carregaFoto(exercicio);
            return exercicio;
        }
        return null;
    }

    @Override
    public Exercicio delete(Long id) {
        var exercicio = getById(id);
        if (exercicio != null)
            repository.deleteById(id);
        return exercicio;
    }

    private void saveFoto(Exercicio exercicio) {
        if (exercicio.getFoto() == null || exercicio.getFoto().equals("")) {
            return;
        }

        if (exercicio.getMimeType() == null || !exercicio.getMimeType().startsWith("image/")) {
            return;
        }

        byte[] imageBytes = Base64.getDecoder().decode(exercicio.getFoto());
        InputStream imageStream = new ByteArrayInputStream(imageBytes);

        File dir = new File(tempFolder);
        if (!dir.exists()) {
            dir.mkdir();
        }

        root = Paths.get(tempFolder);
        UUID uuid = UUID.randomUUID();
        String extensao = "jpg";
        if (exercicio.getArquivoFoto() != null && exercicio.getArquivoFoto().contains(".")) {
            String[] partes = exercicio.getArquivoFoto().split("\\.");
            extensao = partes[partes.length - 1];
        }
        String novoNome = String.format("%s.%s", uuid.toString(), extensao);
        Path nomeArquivo = this.root.resolve(novoNome);
        try {
            Files.copy(imageStream, nomeArquivo);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível salvar o arquivo. Error: " + e.getMessage());
        }
        exercicio.setArquivoFoto(nomeArquivo.toAbsolutePath().toString());
    }

    private Exercicio carregaFoto(Exercicio exercicio) {
        if (exercicio.getArquivoFoto() == null || exercicio.getArquivoFoto().equals("")) {
            return exercicio;
        }

        File file = new File(exercicio.getArquivoFoto());
        if (!file.exists()) {
            return exercicio;
        }

        try {
            byte[] imageBytes = Files.readAllBytes(file.toPath());
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            exercicio.setFoto(base64Image);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível carregar a foto. Error: " + e.getMessage());
        }

        return exercicio;
    }
}
