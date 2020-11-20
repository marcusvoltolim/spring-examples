package marcusvoltolim.example.enum_generic_converter;

import marcusvoltolim.example.enum_generic_converter.enums.UserType;
import marcusvoltolim.example.enum_generic_converter.model.User;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EnumGenericConverterTest {

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	void usingEnumConverterGeneric() {
		var id = testEntityManager.persistAndGetId(new User(UserType.ADMIN));
		var typeBd = testEntityManager.getEntityManager()
									  .createNativeQuery("SELECT user_type_converter FROM User WHERE id = :id")
									  .setParameter("id", id)
									  .getSingleResult();

		assertThat(typeBd)
			.isEqualTo(UserType.ADMIN.getId())
			.isNotEqualTo(UserType.ADMIN.ordinal())
			.isNotEqualTo(UserType.ADMIN.name());
	}


	@Test
	void usingOrdinalValue() {
		var id = testEntityManager.persist(new User(UserType.ADMIN));
		var typeBd = testEntityManager.getEntityManager()
									  .createNativeQuery("SELECT user_type_ordinal FROM User WHERE id = :id")
									  .setParameter("id", id)
									  .getSingleResult();

		assertThat(typeBd)
			.isNotEqualTo(UserType.ADMIN.getId())
			.isEqualTo(UserType.ADMIN.ordinal())
			.isNotEqualTo(UserType.ADMIN.name());

	}

	@Test
	void usingStringValue() {
		var id = testEntityManager.persistAndGetId(new User(UserType.ADMIN));
		var typeBd = testEntityManager.getEntityManager()
									  .createNativeQuery("SELECT user_type_string FROM User WHERE id = :id")
									  .setParameter("id", id)
									  .getSingleResult();

		assertThat(typeBd)
			.isNotEqualTo(UserType.ADMIN.getId())
			.isNotEqualTo(UserType.ADMIN.ordinal())
			.isEqualTo(UserType.ADMIN.name());
	}

}