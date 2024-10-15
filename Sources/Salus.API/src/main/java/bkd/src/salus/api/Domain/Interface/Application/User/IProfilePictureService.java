package bkd.src.salus.api.Domain.Interface.Application.User;

import org.springframework.web.multipart.MultipartFile;

public interface IProfilePictureService {
    String SaveImage(MultipartFile file, int userId) throws Exception;
}
