package com.secret.santa.Service;

import com.secret.santa.Model.Pair;
import com.secret.santa.Model.Person;
import com.secret.santa.Repository.pairRepo;
import com.secret.santa.Repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class MainService {

    private pairRepo pairs;
    private PersonRepository persons;

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

    public List<Pair> createPairs(){
        List<Person> personList = persons.findAll();
        Collections.shuffle(personList);
        System.out.println(personList);
        for(int i =0;i<=personList.size()-2;i+=2){
            addPair(Arrays.asList(personList.get(i).getId(), personList.get(i+1).getId()));
        }
        return getAllPair();
    }

    public Person getPersonByUsername(String username){
        return persons.getByUsername(username);
    }

}
