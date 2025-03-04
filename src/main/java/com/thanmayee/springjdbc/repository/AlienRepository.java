package com.thanmayee.springjdbc.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.thanmayee.springjdbc.model.Alien;

@Repository
public class AlienRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Alien> alienRowMapper = (rs, rowNum) -> {
        Alien alien = new Alien();
        alien.setId(rs.getInt("id"));
        alien.setName(rs.getString("name"));
        alien.setTech(rs.getString("tech"));
        return alien;
    };

    // Save an Alien record
    public void save(Alien alien) {
        String sql = "INSERT INTO alien (id, name, tech) VALUES (?, ?, ?)";
        int rows = jdbcTemplate.update(sql, alien.getId(), alien.getName(), alien.getTech());
        System.out.println(rows + " row/s affected");
    }

    // Fetch all Alien records
    public List<Alien> findAll() {
        String sql = "SELECT * FROM alien";
        return jdbcTemplate.query(sql, alienRowMapper);
    }

    // Find Alien by ID
    public Alien findById(int id) {
        String sql = "SELECT * FROM alien WHERE id = ?";
        
            return jdbcTemplate.queryForObject(sql, alienRowMapper, id);
        
    }

    // Update an Alien record
    public boolean update(Alien alien) {
        String sql = "UPDATE alien SET name = ?, tech = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql, alien.getName(), alien.getTech(), alien.getId());
        return rows > 0;
    }

    // Delete an Alien record by ID
    public boolean deleteById(int id) {
        String sql = "DELETE FROM alien WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        return rows > 0;
    }

    // Delete all Alien records
    public void deleteAll() {
        String sql = "DELETE FROM alien";
        jdbcTemplate.update(sql);
    }

    // Count total Alien records
    public int count() {
        String sql = "SELECT COUNT(*) FROM alien";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // Check if an Alien exists by ID
    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM alien WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}
