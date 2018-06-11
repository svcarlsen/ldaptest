package dk.kb.brugerbase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by dgj on 07-06-2018.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    private static Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private PersonRepository personRepository;


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void setup(){
        log.info("Spring Boot + Spring LDAP Advanced LDAP Queries Example");

        List<Person> people = personRepository.getPeople();
        log.info("people: " + people);

        Person person = personRepository.getPerson("testuser");
        log.info("person " + person);

        person = new Person("testuser2","Test Testersen","Testersen","test@test.dk");
        personRepository.createPerson(person);

        person = personRepository.getPerson("testuser2");
        log.info("person " + person);

        personRepository.modifyAttribute("testuser2","mail","test2@test.com");
        person = personRepository.getPerson("testuser2");
        log.info("person " + person);

        personRepository.deletePerson(person);

        System.exit(-1);
    }

}
