package com.thanmayee.springjdbc.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.thanmayee.springjdbc.model.Alien;

@SpringBootTest
class AlienRepositoryTest {

    @Autowired
    private AlienRepository alienRepository;

    private Alien testAlien;

    @BeforeEach
    void setUp() {
        // Clean up the database before each test
        alienRepository.deleteAll();

        // Create a test alien
        testAlien = new Alien();
        testAlien.setId(999);
        testAlien.setName("Test Alien");
        testAlien.setTech("Test Tech");
    }

    @Test
    void testSave() {
        // When
        alienRepository.save(testAlien);

        // Then
        Alien found = alienRepository.findById(testAlien.getId());
        assertNotNull(found);
        assertEquals(testAlien.getName(), found.getName());
        assertEquals(testAlien.getTech(), found.getTech());
    }

    @Test
    void testFindAll() {
        // Given
        alienRepository.save(testAlien);
        Alien secondAlien = new Alien();
        secondAlien.setId(998);
        secondAlien.setName("Second Alien");
        secondAlien.setTech("Second Tech");
        alienRepository.save(secondAlien);

        // When
        var aliens = alienRepository.findAll();

        // Then
        assertEquals(2, aliens.size());
        assertTrue(aliens.stream().anyMatch(a -> a.getId() == testAlien.getId()));
        assertTrue(aliens.stream().anyMatch(a -> a.getId() == secondAlien.getId()));
    }

    @Test
    void testFindById() {
        // Given
        alienRepository.save(testAlien);

        // When
        Alien found = alienRepository.findById(testAlien.getId());

        // Then
        assertNotNull(found);
        assertEquals(testAlien.getId(), found.getId());
        assertEquals(testAlien.getName(), found.getName());
        assertEquals(testAlien.getTech(), found.getTech());
    }

    @Test
    void testFindByIdNotFound() {
        // When
        Alien found = alienRepository.findById(99999);

        // Then
        assertNull(found);
    }

    @Test
    void testUpdate() {
        // Given
        alienRepository.save(testAlien);
        String updatedName = "Updated Alien";
        String updatedTech = "Updated Tech";
        testAlien.setName(updatedName);
        testAlien.setTech(updatedTech);

        // When
        boolean updated = alienRepository.update(testAlien);

        // Then
        assertTrue(updated);
        Alien found = alienRepository.findById(testAlien.getId());
        assertNotNull(found);
        assertEquals(updatedName, found.getName());
        assertEquals(updatedTech, found.getTech());
    }

    @Test
    void testDeleteById() {
        // Given
        alienRepository.save(testAlien);

        // When
        boolean deleted = alienRepository.deleteById(testAlien.getId());

        // Then
        assertTrue(deleted);
        assertNull(alienRepository.findById(testAlien.getId()));
    }

    @Test
    void testDeleteAll() {
        // Given
        alienRepository.save(testAlien);
        Alien secondAlien = new Alien();
        secondAlien.setId(998);
        secondAlien.setName("Second Alien");
        secondAlien.setTech("Second Tech");
        alienRepository.save(secondAlien);

        // When
        alienRepository.deleteAll();

        // Then
        assertEquals(0, alienRepository.count());
    }

    @Test
    void testCount() {
        // Given
        alienRepository.save(testAlien);
        Alien secondAlien = new Alien();
        secondAlien.setId(998);
        secondAlien.setName("Second Alien");
        secondAlien.setTech("Second Tech");
        alienRepository.save(secondAlien);

        // When
        int count = alienRepository.count();

        // Then
        assertEquals(2, count);
    }

    @Test
    void testExistsById() {
        // Given
        alienRepository.save(testAlien);

        // When & Then
        assertTrue(alienRepository.existsById(testAlien.getId()));
        assertFalse(alienRepository.existsById(99999));
    }
} 