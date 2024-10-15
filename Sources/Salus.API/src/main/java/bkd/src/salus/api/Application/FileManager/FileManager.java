package bkd.src.salus.api.Application.FileManager;

import bkd.src.salus.api.Domain.DTO.File.FileResponseDTO;
import bkd.src.salus.api.Domain.Entity.SQL.Picture.MedicinePicture;
import bkd.src.salus.api.Domain.Entity.SQL.Picture.ProfilePicture;
import bkd.src.salus.api.Domain.Exception.ArgumentException;
import bkd.src.salus.api.Domain.Exception.ValidationException;
import bkd.src.salus.api.Domain.Interface.Application.FileManager.ICheckFile;
import bkd.src.salus.api.Repository.SQL.Picture.IMedicinePictureRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.Picture.IProfilePictureRepositoryJPA;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FileManager implements ICheckFile, bkd.src.salus.api.Domain.Interface.Application.FileManager.IFileManager {

    private final String[] IMAGE_EXTENSIONS = { "jpg", "jpeg", "png", "gif", "bmp", "tiff", "webp" };
    private final IProfilePictureRepositoryJPA profilePictureRepositoryJPA;
    private final IMedicinePictureRepositoryJPA medicinePictureRepositoryJPA;

    @Autowired
    public FileManager(IProfilePictureRepositoryJPA profilePictureRepositoryJPA, IMedicinePictureRepositoryJPA medicinePictureRepositoryJPA) {
        this.profilePictureRepositoryJPA = profilePictureRepositoryJPA;
        this.medicinePictureRepositoryJPA = medicinePictureRepositoryJPA;
    }

    @Override
    public FileResponseDTO GetFile(String fileName){
        ProfilePicture profilePicture = profilePictureRepositoryJPA.findByName(fileName).orElse(null);

        if(profilePicture == null){
            MedicinePicture medicinePicture = medicinePictureRepositoryJPA.findByName(fileName).orElse(null);

            if (medicinePicture != null) {
                return new FileResponseDTO(medicinePicture.getFileData(), medicinePicture.getFileName());
            }
        }
        else {
            return new FileResponseDTO(profilePicture.getFileData(), profilePicture.getFileName());
        }

        return new FileResponseDTO(null,"");
    }

    @Override
    public String ConvertFileName(String fileName) throws Exception {
        String extension = FilenameUtils.getExtension(fileName);

        if(!CheckifIsImage(extension)){
            throw new ValidationException("The file submitted are not a image");
        }

        String newFileName = UUID.randomUUID().toString();

        return String.format("%s.%s", newFileName, extension);
    }

    private boolean CheckifIsImage(String extension){
        for(String ext : IMAGE_EXTENSIONS){
            if(extension.equals(ext)){
                return true;
            }
        }

        return false;
    }
}
