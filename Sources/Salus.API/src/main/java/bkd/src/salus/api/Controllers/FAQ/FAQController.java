package bkd.src.salus.api.Controllers.FAQ;

import bkd.src.salus.api.Domain.Interface.Application.FAQ.IFAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/FAQ")
public class FAQController {

    private final IFAQService service;

    @Autowired
    public FAQController(IFAQService service) {
        this.service = service;
    }

    @GetMapping("/Groups")
    public ResponseEntity GetGroups(){
        return new ResponseEntity<>(service.GetGroups(), HttpStatus.OK);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity GetGroups(@PathVariable int groupId){
        return new ResponseEntity<>(service.GetFAQs(groupId), HttpStatus.OK);
    }
}
