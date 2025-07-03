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

import br.univille.projetofabsofttreinoedieta.entity.Aluno;
import br.univille.projetofabsofttreinoedieta.repository.AlunoRepository;
import br.univille.projetofabsofttreinoedieta.service.AlunoService;

@Service
public class AlunoServiceimpl implements AlunoService {

    @Autowired
    private AlunoRepository repository;

    @Value("${fabrica2025.tempfolder}")
    private String tempFolder;
    private Path root = null;

    @Override
    public Aluno save(Aluno aluno) {
        saveFoto(aluno);
        repository.save(aluno);
        return aluno;
    }

    @Override
    public List<Aluno> getAll() {
        var listaAlunos = repository.findAll();
        listaAlunos.stream()
            .filter(aluno -> aluno.getArquivoFoto() != null && !((String) aluno.getArquivoFoto()).isEmpty())
            .forEach(this::carregaFoto);
        return listaAlunos;
    }

    @Override
    public Aluno getById(Long id) {
        var retorno = repository.findById(id);
        if (retorno.isPresent()) {
            var aluno = retorno.get();
            carregaFoto(aluno);
            return aluno;
        }
        return null;
    }

    @Override
    public Aluno delete(Long id) {
        var aluno = getById(id);
        if (aluno != null)
            repository.deleteById(id);
        return aluno;
    }

    private void saveFoto(Aluno aluno) {
        if (aluno.getArquivoFoto() == null || aluno.getArquivoFoto().equals("")) {
            return;
        }

        if (aluno.getMimeType() == null || !((Path) aluno.getMimeType()).startsWith("image/")) {
            return;
        }

        byte[] imageBytes = Base64.getDecoder().decode((byte[]) aluno.getArquivoFoto());
        InputStream imageStream = new ByteArrayInputStream(imageBytes);

        File dir = new File(tempFolder);
        if (!dir.exists()) {
            dir.mkdir();
        }

        root = Paths.get(tempFolder);
        UUID uuid = UUID.randomUUID();
        String extensao = "jpg";
        if (aluno.getArquivoFoto() != null && ((String) aluno.getArquivoFoto()).contains(".")) {
            String[] partes = ((String) aluno.getArquivoFoto()).split("\\.");
            extensao = partes[partes.length - 1];
        }
        String novoNome = String.format("%s.%s", uuid.toString(), extensao);
        Path nomeArquivo = this.root.resolve(novoNome);
        try {
            Files.copy(imageStream, nomeArquivo);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível salvar o arquivo. Error: " + e.getMessage());
        }
        aluno.setArquivoFoto(nomeArquivo.toAbsolutePath().toString());
    }

    private Aluno carregaFoto(Aluno aluno) {
        if (aluno.getArquivoFoto() == null || aluno.getArquivoFoto().equals("")) {
            return aluno;
        }

        File file = new File((String) aluno.getArquivoFoto());
        if (!file.exists()) {
            return aluno;
        }

        try {
            byte[] imageBytes = Files.readAllBytes(file.toPath());
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            aluno.setArquivoFoto(base64Image);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível carregar a foto. Error: " + e.getMessage());
        }

        return aluno;
    }
}
