package cl.ahianf.rankbot.rest;

import cl.ahianf.rankbot.entity.SinNombreDos;
import cl.ahianf.rankbot.service.SinNombreDosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matches")
public class SinNombreDosRestController {
    @Autowired
    private SinNombreDosService sinNombreDosService;

    @Autowired
    public SinNombreDosRestController(SinNombreDosService theSinNombreDosService) {
        sinNombreDosService = theSinNombreDosService;

    }

    @GetMapping("{theId}")
    public SinNombreDos getMatch(@PathVariable int theId){
        SinNombreDos theSinNombreDos = sinNombreDosService.findById(theId);

        return theSinNombreDos;

    }


}
