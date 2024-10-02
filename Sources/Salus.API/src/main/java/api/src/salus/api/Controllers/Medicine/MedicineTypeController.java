package api.src.salus.api.Controllers.Medicine;

import api.src.salus.api.Domain.DTO.Medicine.RegisterMedicineDTO;
import api.src.salus.api.Domain.Interface.Application.Auth.ICheckVisibilite;
import api.src.salus.api.Domain.Interface.Application.Medicine.IMedicineService;
import api.src.salus.api.Domain.Interface.Application.Medicine.IMedicineTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Medicine")
public class MedicineTypeController {

    private final IMedicineTypeService typeService;

    @Autowired
    public MedicineTypeController(IMedicineTypeService typeService){
        this.typeService = typeService;
    }

    @GetMapping("/Types")
    public ResponseEntity ReadById(){
        return new ResponseEntity<>(typeService.ListAll(), HttpStatus.OK);
    }
}
