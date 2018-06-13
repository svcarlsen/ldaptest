package dk.kb.brugerbase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    
    @Value("${test.size}")
    private long testSize;
    
    @Value("${test.cleanuprepository.before}")
    private boolean cleanuprepositoryBefore;
    
    @Value("${test.cleanuprepository.after}")
    private boolean cleanuprepositoryAfter;
    		
    @Value("${test.showLimit}")
    private int showLimit;
    
    
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

        if (cleanuprepositoryBefore) {
        	log.info("Doing cleanup before starting test");
        	int deleted = 0;
        	while(!personRepository.getPeople().isEmpty()) {
        		List<Person> people = personRepository.getPeople();
        		for (Person p : people){
        			personRepository.deletePerson(p);
        			deleted++;
        			log.info("Deleted person #{}: {}",deleted, p);
        		}
        	}
        	log.info("Deleted {} persons", deleted);
        }
        List<Person> people = personRepository.getPeople();
        log.info("Found {} people", people.size());
        if (!people.isEmpty()) {
        	log.info("The first {} persons are", showLimit);
        	int i=1;
        	for (Person p: people) {
        		log.info("#{}: {}", i, p);i++;
        		if (i > showLimit) {
        			break;
        		}
        	}
        }
        

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
        log.info("Starting to create {} persons", testSize);
        createPersons(testSize);

        if (cleanuprepositoryAfter) {
        	log.info("Doing cleanup after finishing starting test");
        	int deleted = 0;
        	while(!personRepository.getPeople().isEmpty()) {
        		List<Person> people1 = personRepository.getPeople();
        		for (Person p : people1){
        			personRepository.deletePerson(p);
        			deleted++;
        			log.info("Deleted person #{}: {}",deleted, p);
        		}
        	}
        	log.info("Deleted {} persons", deleted);
        }
        
        System.exit(-1);
    }

	private void createPersons(long testSize) {
		long uidPrefix = System.currentTimeMillis();
		String mailprefix = "@kb.dk";
		String lastNamePrefix = "Testersen";
		String firstNamePrefix = "Test";
		for (int i=0; i < testSize; i++) {
			String uid = uidPrefix + "_" + i;
			String mail = uid + mailprefix;
			String lastname = lastNamePrefix + i;
			String fullname = firstNamePrefix + i + " " + lastname;
			
			Person person = new Person(uid, fullname, lastname, mail);
			personRepository.createPerson(person);
		}
		log.info("Created {} persons", testSize);	
	}

}
