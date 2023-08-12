package gugunava.danil.drones.repository;

import gugunava.danil.drones.entity.MedicationEntity;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MedicationRepositoryTest {

	@Autowired
	private MedicationRepository medicationRepository;

	@Test
	@Sql(scripts = "/sql/medication/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void whenDeleteExistingEntity_thenDbShouldContainOneLessEntity() {
		long countBeforeDeletion = medicationRepository.count();
		long medicationId = 1L;
		assertTrue(medicationRepository.existsById(medicationId));

		medicationRepository.deleteById(medicationId);

		long countAfterDeletion = medicationRepository.count();
		assertEquals(countBeforeDeletion, countAfterDeletion + 1);
		assertFalse(medicationRepository.existsById(medicationId));
	}

	@Test
	void whenDeleteNotExistingEntity_thenShouldThrowException() {
		long countBeforeDeletion = medicationRepository.count();
		long medicationId = -1L;
		assertFalse(medicationRepository.existsById(medicationId));

		ThrowableAssert.ThrowingCallable delete = () -> medicationRepository.deleteById(medicationId);

		thenThrownBy(delete)
				.isInstanceOf(EmptyResultDataAccessException.class)
				.hasMessage("No class gugunava.danil.drones.entity.MedicationEntity entity with id -1 exists!");
		long countAfterDeletion = medicationRepository.count();
		assertEquals(countBeforeDeletion, countAfterDeletion);
		assertFalse(medicationRepository.existsById(medicationId));
	}

	@Test
	@Sql(scripts = "/sql/medication/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void whenAddNewEntity_dbShouldContainOneMoreEntity() {
		long countBeforeAdding = medicationRepository.count();
		MedicationEntity medicationEntity = new MedicationEntity(null, "test_name", 50, "code", "image".getBytes());

		MedicationEntity saved = medicationRepository.save(medicationEntity);

		long countAfterAdding = medicationRepository.count();
		assertEquals(countBeforeAdding, countAfterAdding - 1);
		assertTrue(medicationRepository.existsById(saved.getId()));
	}
}
