package bkd.src.salus.api.Controllers.Medicine;

import bkd.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import bkd.src.salus.api.Domain.Interface.Application.FileManager.IMedicinePictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/Medicine")
public class MedicinePictureController {

    private final ICheckVisibilite checkVisibility;
    private final IMedicinePictureService medicinePictureService;

    @Autowired
    public MedicinePictureController(ICheckVisibilite checkVisibility, IMedicinePictureService medicinePictureService){
        this.checkVisibility = checkVisibility;
        this.medicinePictureService = medicinePictureService;
    }

    @PostMapping("/Image/{medicineId}")
    public ResponseEntity Create(@RequestParam("files") MultipartFile[] files,
                                 @RequestHeader("Authorization") String authHeader,
                                 @RequestParam(required = false, defaultValue = "0") Integer userId,
                                 @PathVariable int medicineId) throws Exception {
        int id = (userId != 0) ? checkVisibility.CheckAccess(authHeader, userId) : checkVisibility.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(medicinePictureService.SaveImages(files, id, medicineId), HttpStatus.OK);
    }

    @DeleteMapping("/Image/{fileName}")
    public ResponseEntity Delete(@PathVariable String fileName){
        HttpStatus status = medicinePictureService.RemovePicture(fileName) ? HttpStatus.NO_CONTENT : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(null, status);
    }

    @GetMapping("/Image/{medicineId}")
    public ResponseEntity ListImages(@PathVariable int medicineId,
                                     @RequestHeader("Authorization") String authHeader,
                                     @RequestParam(required = false, defaultValue = "0") Integer userId){
        int id = (userId != 0) ? checkVisibility.CheckAccess(authHeader, userId) : checkVisibility.ExtractIdFromToken(authHeader);
        return new ResponseEntity<>(medicinePictureService.FindByMedicineId(medicineId, id), HttpStatus.OK);
    }
}
