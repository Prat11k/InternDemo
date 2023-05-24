package spring.all.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.all.Entity.TargetFactoryUser;

public interface FactoryKafkaRepository extends JpaRepository<TargetFactoryUser, Integer>{

}
