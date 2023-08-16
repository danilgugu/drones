package gugunava.danil.drones.repository;

import gugunava.danil.drones.entity.DroneEntity;
import gugunava.danil.drones.entity.DroneState;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DroneRepositoryTest {

	@Autowired
	private DroneRepository droneRepository;

	@Test
	@Sql(scripts = "/sql/drone/insert_one.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void whenDeleteExistingEntity_thenDbShouldContainOneLessEntity() {
		long countBeforeDeletion = droneRepository.count();
		long droneId = 1L;
		assertTrue(droneRepository.existsById(droneId));

		droneRepository.deleteById(droneId);

		long countAfterDeletion = droneRepository.count();
		assertEquals(countBeforeDeletion, countAfterDeletion + 1);
		assertFalse(droneRepository.existsById(droneId));
	}

	@Test
	void whenDeleteNotExistingEntity_thenShouldThrowException() {
		long countBeforeDeletion = droneRepository.count();
		long droneId = -1L;
		assertFalse(droneRepository.existsById(droneId));

		ThrowableAssert.ThrowingCallable delete = () -> droneRepository.deleteById(droneId);

		thenThrownBy(delete)
				.isInstanceOf(EmptyResultDataAccessException.class)
				.hasMessage("No class gugunava.danil.drones.entity.DroneEntity entity with id -1 exists!");
		long countAfterDeletion = droneRepository.count();
		assertEquals(countBeforeDeletion, countAfterDeletion);
		assertFalse(droneRepository.existsById(droneId));
	}

	@Test
	@Sql(scripts = "/sql/drone/delete_one.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void whenAddNewEntity_dbShouldContainOneMoreEntity() {
		long countBeforeAdding = droneRepository.count();
		DroneEntity droneEntity = new DroneEntity(null, "test_serial", "new", 145, 80, DroneState.IDLE);

		DroneEntity saved = droneRepository.save(droneEntity);

		long countAfterAdding = droneRepository.count();
		assertEquals(countBeforeAdding, countAfterAdding - 1);
		assertTrue(droneRepository.existsById(saved.getId()));
	}

	@Sql(scripts = "/sql/drone/insert_one.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(scripts = "/sql/drone/delete_one.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void checkExistsBySerialNumber() {
		assertTrue(droneRepository.existsBySerialNumber("1"));
		assertFalse(droneRepository.existsBySerialNumber("2"));
	}
}
