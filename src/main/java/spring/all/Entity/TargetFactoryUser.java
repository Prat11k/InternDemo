package spring.all.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="TargetUser")
@Entity
public class TargetFactoryUser {
	@Id
	Integer id;
	String name;
	String City;
}
