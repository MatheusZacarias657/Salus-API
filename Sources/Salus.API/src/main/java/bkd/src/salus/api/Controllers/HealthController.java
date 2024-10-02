package bkd.src.salus.api.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Health")
public class HealthController {

    @GetMapping("/isAlive")
    public ResponseEntity<Object> isAlive(){

        Map<String, Object> respose = new HashMap<>();
        respose.put("alive", true);

        return new ResponseEntity<>(respose, HttpStatus.OK);
    }

    @GetMapping("/AuthTest")
    public ResponseEntity<Object> AuthTest(){

        Map<String, Object> respose = new HashMap<>();
        respose.put("working", true);

        return new ResponseEntity<>(respose, HttpStatus.OK);
    }
}
