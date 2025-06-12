package com.project.ToDoList.services.users;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Serviço para lidar com operações de armazenamento de ficheiros.
 * Esta classe é responsável por guardar os ficheiros enviados (como imagens de perfil)
 * num diretório no servidor.
 */
@Service
public class FileStorageService {

    // O caminho para o diretório onde os ficheiros serão guardados.
    // É uma boa prática tornar este caminho configurável no ficheiro 'application.properties'.
    private final Path root = Paths.get("uploads");

    /**
     * Construtor que inicializa o serviço.
     * Tenta criar o diretório de upload se ele ainda não existir.
     */
    public FileStorageService() {
        try {
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }
        } catch (IOException e) {
            // Lança uma exceção em tempo de execução se não for possível criar o diretório.
            throw new RuntimeException("Não foi possível inicializar a pasta para upload!", e);
        }
    }

    /**
     * Guarda um ficheiro no diretório de upload.
     *
     * @param file O ficheiro MultipartFile recebido na requisição.
     * @return O nome único do ficheiro que foi guardado.
     */
    public String save(MultipartFile file) {
        try {
            // Gera um nome de ficheiro aleatório e único usando UUID para evitar ficheiros com o mesmo nome.
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String filename = UUID.randomUUID().toString() + extension;

            // Resolve o caminho completo do ficheiro e copia os bytes do ficheiro enviado para o destino.
            // O Files.copy irá substituir um ficheiro existente com o mesmo nome se ele existir.
            Files.copy(file.getInputStream(), this.root.resolve(filename));

            return filename;
        } catch (Exception e) {
            // Lança uma exceção se ocorrer algum erro durante o processo de guardar o ficheiro.
            throw new RuntimeException("Ocorreu um erro ao tentar guardar o ficheiro: " + e.getMessage());
        }
    }
}