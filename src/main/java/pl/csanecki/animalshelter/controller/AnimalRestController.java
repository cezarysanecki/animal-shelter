package pl.csanecki.animalshelter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/animals")
public class AnimalRestController {

    private AnimalSerivce animalSerivce;

    @Autowired
    public AnimalRestController(AnimalSerivce animalSerivce) {
        this.animalSerivce = animalSerivce;
    }
}
