package bkd.src.salus.api.Application.FileManager;

import bkd.src.salus.api.Domain.Entity.SQL.Picture.ProfilePicture;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import bkd.src.salus.api.Domain.Interface.Application.FileManager.ICheckFile;
import bkd.src.salus.api.Domain.Interface.Application.FileManager.IFindProfilePicture;
import bkd.src.salus.api.Domain.Interface.Application.User.IProfilePictureService;
import bkd.src.salus.api.Repository.SQL.Picture.IProfilePictureRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.User.IUserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class ProfilePictureService implements IProfilePictureService, IFindProfilePicture {

    private final IProfilePictureRepositoryJPA profilePictureRepositoryJPA;
    private final IUserRepositoryJPA userRepositoryJPA;
    private final ICheckFile checkFile;

    @Autowired
    public ProfilePictureService(IProfilePictureRepositoryJPA profilePictureRepositoryJPA, IUserRepositoryJPA userRepositoryJPA, ICheckFile checkFile) {
        this.profilePictureRepositoryJPA = profilePictureRepositoryJPA;
        this.userRepositoryJPA = userRepositoryJPA;
        this.checkFile = checkFile;
    }

    @Override
    public String SaveImage(MultipartFile file, int userId) throws Exception {

        String fileName = checkFile.ConvertFileName(file.getOriginalFilename());
        ProfilePicture picture = profilePictureRepositoryJPA.findByUserId(userId).orElse(null);
        if(picture != null){
            RemovePicture(picture.getId());
        }

        UserAccount user = (picture == null) ? userRepositoryJPA.findById(userId).get() : picture.getUser();
        ProfilePicture entity = new ProfilePicture(user, fileName, file.getBytes());
        profilePictureRepositoryJPA.save(entity);

        return fileName;
    }

    @Override
    public String FindProfilePictureName(int userId){
        ProfilePicture picture = profilePictureRepositoryJPA.findByUserId(userId).orElse(null);

        return (picture != null) ? picture.getFileName() : "";
    }

    @Override
    public String FindProfilePicture(int userId){
        ProfilePicture picture = profilePictureRepositoryJPA.findByUserId(userId).orElse(null);

        if(picture == null){
            return "";
        }

        URI uri = UriComponentsBuilder.fromPath("/File/{fileName}").buildAndExpand(picture.getFileName()).toUri();

        return uri.toString();
    }

    private void RemovePicture(int id) {
        if (profilePictureRepositoryJPA.existsById(id)) {
            profilePictureRepositoryJPA.deleteById(id);
        }
    }
}
