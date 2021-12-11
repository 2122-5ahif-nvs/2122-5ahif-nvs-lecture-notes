package at.htl.testdemo.control;

import at.htl.testdemo.entity.Person;
import io.agroal.api.AgroalDataSource;
import io.quarkus.test.junit.QuarkusTest;
import org.assertj.db.type.Table;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import static org.assertj.db.api.Assertions.assertThat;
import static org.assertj.db.output.Outputs.output;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonRepositoryTest {

    @Inject
    Logger LOG;

    @Inject
    PersonRepository personRepository;

    @Inject
    AgroalDataSource ds;

    @Inject
    UserTransaction tx;

    @Test
    @Order(1000)
    void createPersonWithPersist() throws Exception {

        // arrange
        Person susi = new Person("Susi");

        // act
        tx.begin();
        LOG.info(susi);
        personRepository.persist(susi);
        LOG.info(susi);
        susi.setName("Susanne");
        LOG.info(personRepository.isPersistent(susi));
        tx.commit();
        LOG.info(personRepository.isPersistent(susi));

        // assert
        Table table = new Table(ds, "PERSON");
        output(table).toConsole();
        assertThat(table).row(0)
                .value("NAME").isEqualTo("Susanne");
    }


    @Test
    @Order(1010)
    void createPersonWithMerge() throws Exception {

        // arrange
        Person hansi = new Person("Susi");

        // act
        tx.begin();
        LOG.info(hansi);
        hansi = personRepository.getEntityManager().merge(hansi);
        LOG.info(hansi);
        hansi.setName("Hansi");
        LOG.info(personRepository.isPersistent(hansi));
        tx.commit();
        LOG.info(personRepository.isPersistent(hansi));

        // assert
        Table table = new Table(ds, "PERSON");
        output(table).toConsole();
        assertThat(table)
                .row(1)
                .value("NAME").isEqualTo("Hansi");
    }

}