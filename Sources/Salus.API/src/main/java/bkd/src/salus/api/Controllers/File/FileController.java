package bkd.src.salus.api.Controllers.File;

import bkd.src.salus.api.Domain.DTO.File.FileResponseDTO;
import bkd.src.salus.api.Domain.Interface.Application.Channel.IChannelService;
import bkd.src.salus.api.Domain.Interface.Application.FileManager.IFileManager;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/File")
public class FileController {

    private final IFileManager fileManager;

    public FileController(IFileManager fileManager) {
        this.fileManager = fileManager;
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> ListAll(@PathVariable String fileName) throws IOException {

        FileResponseDTO response = fileManager.GetFile(fileName);
        String contentType = Files.probeContentType(Path.of(response.getName()));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(response.getContent());
    }
}
