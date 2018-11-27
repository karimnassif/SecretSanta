package com.secret.santa.Repository;

import com.secret.santa.Model.Pair;
import com.secret.santa.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface pairRepo extends JpaRepository<Pair, Long> {

    Pair getByGiver(Person giver);

}

