package com.secret.santa.Service;

import com.secret.santa.Model.Pair;
import com.secret.santa.Model.Person;
import com.secret.santa.Repository.PersonRepository;
import com.secret.santa.Repository.pairRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MainService {

    private pairRepo pairs;
    private PersonRepository persons;
    private int counter = 0;

    public MainService(pairRepo pairs, PersonRepository persons) { this.pairs = pairs; this.persons = persons; }

    public ResponseEntity<Pair> addPair(List<Long> ids){
        Pair pair = new Pair();
        pair.setGiver(persons.getOne(ids.get(0)));
        pair.setReceiver(persons.getOne(ids.get(1)));
        pairs.save(pair);
        return new ResponseEntity<>(pair, HttpStatus.CREATED);
    }

    public ResponseEntity<Person> addPerson(Person person){
        persons.save(person);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    public List<Person> getAllPerson(){
        return persons.findAll();
    }

    public List<Pair> getAllPair(){
        return pairs.findAll();
    }

    public Person getReceiver(String username) {
        Pair pair = pairs.getByGiver(persons.getByUsername(username));
        if(pair == null){
            pair = new Pair();
        }
        return pair.getReceiver();
    }

    public Person getPerson(Long id){
        return persons.findById(id).get();
    }

    public void deletePersons(){
        persons.deleteAll();
    }

    public void deletePairs(){
        pairs.deleteAll();
    }

    public Person getPersonByUsername(String username){
        return persons.getByUsername(username);
    }

    public List<Pair> createPairs(){
        List<Person> personList = persons.findAll();
        List<Person> availableReceivers = personList.stream().collect(Collectors.toList());
        personList.forEach(person -> {
            Collections.shuffle(availableReceivers);
            addPair(Arrays.asList(person.getId(), availableReceivers.get(0).getId()));
            availableReceivers.remove(availableReceivers.get(0));
        });
        if(checkRepeats()){
            deletePairs();
            createPairs();
        }
        return getAllPair();
    }

    public Boolean checkRepeats(){
        List<Pair> pairList = pairs.findAll();
        for(Pair pair:pairList){
            if(pair.getReceiver().getId() == pair.getGiver().getId()){
                counter+=1;
                System.out.println(counter);
                return true;
            }
            if(getReceiver(pair.getReceiver().getUsername()) == pair.getGiver()){
                counter+=1;
                System.out.println(counter);
                return true;
            }
        }
        return false;
    }

}
