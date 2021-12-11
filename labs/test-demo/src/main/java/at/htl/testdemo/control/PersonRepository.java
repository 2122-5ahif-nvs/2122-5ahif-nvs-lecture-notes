package at.htl.testdemo.control;

import at.htl.testdemo.entity.Person;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class PersonRepository implements PanacheRepository<Person> {
}
