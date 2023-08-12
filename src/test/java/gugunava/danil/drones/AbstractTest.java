package gugunava.danil.drones;

import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public abstract class AbstractTest {

	@MockBean
	private SqlDataSourceScriptDatabaseInitializer dataSourceScriptDatabaseInitializer;
}
