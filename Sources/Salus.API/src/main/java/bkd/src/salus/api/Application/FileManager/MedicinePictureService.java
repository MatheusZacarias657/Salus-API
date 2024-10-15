package bkd.src.salus.api.Application.FileManager;

import bkd.src.salus.api.Domain.DTO.File.FileDetailingDTO;
import bkd.src.salus.api.Domain.Entity.SQL.Medicine.Medicine;
import bkd.src.salus.api.Domain.Entity.SQL.Picture.MedicinePicture;
import bkd.src.salus.api.Domain.Exception.ValidationException;
import bkd.src.salus.api.Domain.Interface.Application.FileManager.ICheckFile;
import bkd.src.salus.api.Domain.Interface.Application.FileManager.IMedicinePictureService;
import bkd.src.salus.api.Repository.SQL.Medicine.IMedicineRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.Picture.IMedicinePictureRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class MedicinePictureService implements IMedicinePictureService {

    private final IMedicinePictureRepositoryJPA medicinePictureRepositoryJPA;
    private final ICheckFile checkFile;
    private final IMedicineRepositoryJPA medicineRepositoryJPA;

    @Autowired
    public MedicinePictureService(IMedicinePictureRepositoryJPA medicinePictureRepositoryJPA, ICheckFile checkFile, IMedicineRepositoryJPA medicineRepositoryJPA) {
        this.medicinePictureRepositoryJPA = medicinePictureRepositoryJPA;
        this.checkFile = checkFile;
        this.medicineRepositoryJPA = medicineRepositoryJPA;
    }

    @Override
    public List<FileDetailingDTO> SaveImages(MultipartFile[] files, int userId, int medicineId) throws Exception {
        Medicine medicine = medicineRepositoryJPA.findMedicineByUserIdAndId(medicineId, userId);

        if(medicine == null){
            throw new ValidationException("This medicine doesn't exists");
        }

        List<MedicinePicture> entities = new ArrayList<>();
        List<FileDetailingDTO> responses = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = checkFile.ConvertFileName(file.getOriginalFilename());
            MedicinePicture medicinePicture = new MedicinePicture(medicine, fileName, file.getBytes());
            entities.add(medicinePicture);

            URI uri = UriComponentsBuilder.fromPath("/File/{fileName}").buildAndExpand(fileName).toUri();
            responses.add(new FileDetailingDTO(fileName, uri.toString()));
        }

        medicinePictureRepositoryJPA.saveAll(entities);

        return responses;
    }

    @Override
    public boolean RemovePicture(String fileName) {
        MedicinePicture medicinePicture = medicinePictureRepositoryJPA.findByName(fileName).orElse(null);

        if(medicinePicture != null){
            medicinePictureRepositoryJPA.delete(medicinePicture);
            return true;
        }

        return false;
    }

    @Override
    public List<FileDetailingDTO> FindByMedicineId(int medicineId, int userId){
        List<MedicinePicture> pictures = medicinePictureRepositoryJPA.findByMedicineIdAndUserId(medicineId, userId).orElse(null);
        List<FileDetailingDTO> responses = new ArrayList<>();

        assert pictures != null;
        for (MedicinePicture picture : pictures) {
            URI uri = UriComponentsBuilder.fromPath("/File/{fileName}").buildAndExpand(picture.getFileName()).toUri();
            responses.add(new FileDetailingDTO(picture.getFileName(), uri.toString()));
        }

        return responses;
    }


}
