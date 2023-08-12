package gugunava.danil.drones.repository;

import gugunava.danil.drones.entity.DroneMedicationEntity;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DroneMedicationRepositoryTest {

	@Autowired
	private DroneMedicationRepository droneMedicationRepository;

	@Test
	@Sql(scripts = "/sql/medication/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(scripts = "/sql/droneMedication/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(scripts = "/sql/droneMedication/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	@Sql(scripts = "/sql/medication/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void whenDeleteExistingEntity_thenDbShouldContainOneLessEntity() {
		long countBeforeDeletion = droneMedicationRepository.count();
		DroneMedicationEntity id = new DroneMedicationEntity(1L, 1L);
		assertTrue(droneMedicationRepository.existsById(id));

		droneMedicationRepository.deleteById(id);

		long countAfterDeletion = droneMedicationRepository.count();
		assertEquals(countBeforeDeletion, countAfterDeletion + 1);
		assertFalse(droneMedicationRepository.existsById(id));
	}

	@Test
	void whenDeleteNotExistingEntity_thenShouldThrowException() {
		long countBeforeDeletion = droneMedicationRepository.count();
		DroneMedicationEntity id = new DroneMedicationEntity(-1L, -1L);
		assertFalse(droneMedicationRepository.existsById(id));

		ThrowableAssert.ThrowingCallable delete = () -> droneMedicationRepository.deleteById(id);

		thenThrownBy(delete)
				.isInstanceOf(EmptyResultDataAccessException.class)
				.hasMessage("No class gugunava.danil.drones.entity.DroneMedicationEntity entity with id DroneMedicationEntity(droneId=-1, medicationId=-1) exists!");
		long countAfterDeletion = droneMedicationRepository.count();
		assertEquals(countBeforeDeletion, countAfterDeletion);
		assertFalse(droneMedicationRepository.existsById(id));
	}

	@Test
	@Sql(scripts = "/sql/medication/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(scripts = "/sql/droneMedication/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	@Sql(scripts = "/sql/medication/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void whenAddNewEntity_dbShouldContainOneMoreEntity() {
		long countBeforeAdding = droneMedicationRepository.count();
		DroneMedicationEntity droneMedicationEntity = new DroneMedicationEntity(1L, 1L);
		assertFalse(droneMedicationRepository.existsById(droneMedicationEntity));

		DroneMedicationEntity saved = droneMedicationRepository.save(droneMedicationEntity);

		long countAfterAdding = droneMedicationRepository.count();
		assertEquals(countBeforeAdding, countAfterAdding - 1);
		assertTrue(droneMedicationRepository.existsById(saved));
	}

	@Test
	void whenTryingToAddNewEntityWithoutMedicationPresent_thenShouldThrowException() {
		long countBeforeAdding = droneMedicationRepository.count();
		DroneMedicationEntity droneMedicationEntity = new DroneMedicationEntity(1L, 1L);
		assertFalse(droneMedicationRepository.existsById(droneMedicationEntity));

		ThrowableAssert.ThrowingCallable save = () -> droneMedicationRepository.save(droneMedicationEntity);

		thenThrownBy(save)
				.isInstanceOf(DataIntegrityViolationException.class)
				.hasMessage("could not execute statement; SQL [n/a]; constraint [CONSTRAINT_FA3]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement");
		long countAfterAdding = droneMedicationRepository.count();
		assertEquals(countBeforeAdding, countAfterAdding);
		assertFalse(droneMedicationRepository.existsById(droneMedicationEntity));
	}
}
