package com.secret.santa.Controller;

import com.secret.santa.Model.Pair;
import com.secret.santa.Model.Person;
import com.secret.santa.Service.MainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/santa")
public class MainController {

    private final MainService mainService;

    public MainController(MainService mainService) { this.mainService = mainService; }

    @PostMapping("/person")
    public ResponseEntity<Person> addPerson(@RequestBody Person person){
        return mainService.addPerson(person);
    }

    @PostMapping("/pair")
    public ResponseEntity<Pair> addPair(@RequestBody List<Long> ids){
        return mainService.addPair(ids);
    }

    @GetMapping("/pair/all")
    public List<Pair> getAllPair(){
        return mainService.getAllPair();
    }

    @GetMapping("/person/all")
    public List<Person> getAllPerson(){
        return mainService.getAllPerson();
    }

    @GetMapping("/person/id/{id}")
    public Person getPerson(@PathVariable(value="id") Long id){
        return mainService.getPerson(id);
    }

    @GetMapping("/person/username/{username}")
    public Person getPerson(@PathVariable(value="username") String username){
        return mainService.getPersonByUsername(username);
    }

    @GetMapping("/pair/receiver/{username}")
    public Person getReceiver(@PathVariable(value="username") String username){
        return mainService.getReceiver(username);
    }

    @DeleteMapping("/pair/delete")
    public void deleteAllPairs(){
        mainService.deletePairs();
    }

    @DeleteMapping("/person/delete")
    public void deleteAllPersons(){
        mainService.deletePersons();
    }

    @PostMapping("/pair/create")
    public List<Pair> createPairs(){
        return mainService.createPairs();
    }
}
