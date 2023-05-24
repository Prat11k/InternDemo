package spring.all.factoryInterfaces;

import java.util.List;
import java.util.Optional;

import spring.all.Entity.FactoryUser;

public interface DataBases {

	List<FactoryUser> getUser();

	Optional<FactoryUser> getUserByid(Integer id);

	String addUser(FactoryUser factoryUser);

	String updateUser(FactoryUser factoryUser);

	String deleteUser(Integer id);
}
