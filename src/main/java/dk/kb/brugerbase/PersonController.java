package dk.kb.brugerbase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dgj on 08-06-2018.
 */
@Controller
public class PersonController {

    private static Logger log = LoggerFactory.getLogger(PersonController.class);


    @Autowired
    private PersonRepository personRepository;

    @RequestMapping("/all")
    public String allPersons(Model model) {
        log.debug("PERSONCONTROLLER all");
        model.addAttribute("people",personRepository.getPeople());
        return "greeting";
    }

}
