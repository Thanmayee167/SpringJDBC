package com.thanmayee.springjdbc;

import com.thanmayee.springjdbc.model.Alien;
import com.thanmayee.springjdbc.repository.AlienRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringJDBCApplication {

  public static void main(String[] args) {
    ApplicationContext applicationContext =
        SpringApplication.run(SpringJDBCApplication.class, args);

    AlienRepository alienRepository = applicationContext.getBean(AlienRepository.class);

    // Clean up the database first
    System.out.println("\n--- Cleaning up database ---");
    alienRepository.deleteAll();
    System.out.println("Database cleaned. Current count: " + alienRepository.count());

    // 1. Create Operation
    System.out.println("\n--- Create Operations ---");
    Alien alien1 = new Alien();
    alien1.setId(101);
    alien1.setName("Sai Thanmayee");
    alien1.setTech("Java");
    alienRepository.save(alien1);
    System.out.println("Saved alien1: " + alien1.getName());

    Alien alien2 = new Alien();
    alien2.setId(102);
    alien2.setName("John Doe");
    alien2.setTech("Python");
    alienRepository.save(alien2);
    System.out.println("Saved alien2: " + alien2.getName());

    // 2. Read Operations
    System.out.println("\n--- Read Operations ---");
    System.out.println("Finding alien by ID 101: " + alienRepository.findById(101));
    System.out.println("Total count of aliens: " + alienRepository.count());
    System.out.println("Does alien 101 exist? " + alienRepository.existsById(101));
    System.out.println("Does alien 999 exist? " + alienRepository.existsById(999));
    System.out.println("All aliens: " + alienRepository.findAll());

    // 3. Update Operation
    System.out.println("\n--- Update Operation ---");
    alien1.setTech("Spring Boot");
    boolean updated = alienRepository.update(alien1);
    System.out.println("Update successful? " + updated);
    System.out.println("Updated alien: " + alienRepository.findById(101));

    // 4. Delete Operations
    System.out.println("\n--- Delete Operations ---");
    System.out.println("Deleting alien with ID 102");
    boolean deleted = alienRepository.deleteById(102);
    System.out.println("Delete successful? " + deleted);
    System.out.println("Remaining aliens: " + alienRepository.findAll());

    System.out.println("\n--- Final Cleanup ---");
    alienRepository.deleteAll();
    System.out.println("Final count: " + alienRepository.count());
  }
}
