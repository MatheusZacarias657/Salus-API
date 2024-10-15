package bkd.src.salus.api.Domain.Interface.Application.FileManager;

import bkd.src.salus.api.Domain.DTO.File.FileDetailingDTO;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public interface IMedicinePictureService {
    List<FileDetailingDTO> SaveImages(MultipartFile[] files, int userId, int medicineId) throws Exception;

    boolean RemovePicture(String fileName);

    List<FileDetailingDTO> FindByMedicineId(int medicineId, int userId);
}
