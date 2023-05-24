package spring.all.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.all.Entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username); 
}
