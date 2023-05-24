package spring.all.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.all.Entity.FactoryUser;

public interface UserSqlRepo extends JpaRepository<FactoryUser, Integer> {

}
