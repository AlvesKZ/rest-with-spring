package com.noxus.services;

import com.noxus.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public List<Person> findAll() {
        logger.info("Finding all people");

        List<Person> people = new ArrayList<Person>();
        for (int i = 0; i < 8; i++) {
            Person person = mockPerson(i);
            people.add(person);
        }
        return people;
    }

    public Person findById(String id) {
        logger.info("Finding one Person!");

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Alves");
        person.setLastName("KheyZZ");
        person.setAddress("São Paulo - SP");
        person.setGender("Male");
        return person;
    }

    public Person create(Person person) {
        logger.info("Creating one Person!");

        return person;
    }

    public Person update(Person person) {
        logger.info("Updating one Person!");

        return person;
    }

    public void delete(String id) {
        logger.info("Deleting one Person!");
    }

    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("FirstName" + i);
        person.setLastName("LastName" + i);
        person.setAddress("São Paulo - SP");
        person.setGender("Male");
        return person;
    }
}
