package ru.karpenko.practice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karpenko.practice.models.Person;
import ru.karpenko.practice.repositories.PeopleRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findOne(long id){
        Optional<Person> foundPeople = peopleRepository.findById(id);
        return foundPeople.orElse(null);
    }

    public Person findByUsername(String username){
        Optional<Person> foundPeople = Optional.ofNullable(peopleRepository.findByUsername(username));
        return foundPeople.orElse(null);
    }

    public boolean isAdmin(String username) {
        Person foundPeople = peopleRepository.findByUsername(username);
        if(foundPeople.getRole().equals("ADMIN")){
            return true;
        }else
            return false;
    }


    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }
    @Transactional
    public void update(long id, Person updatedPerson){
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }
    @Transactional
    public void delete(long id){
        peopleRepository.deleteById(id);
    }

}
