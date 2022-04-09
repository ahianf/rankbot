package cl.ahianf.rankbot.rest;

import cl.ahianf.rankbot.entity.Results;
import cl.ahianf.rankbot.service.ResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/results")
public class ResultsRestController {
    @Autowired
    private ResultsService resultsService;

    @Autowired
    public ResultsRestController(ResultsService theResultsService) {
        resultsService = theResultsService;

    }

    @GetMapping("{theId}")
    public Results getMatch(@PathVariable int theId){
        Results theResults = resultsService.findById(theId);

        return theResults;

    }


}
