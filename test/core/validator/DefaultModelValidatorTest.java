package core.validator;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DefaultModelValidatorTest {

	class DefaultModelValidator implements IValidator {
	}

	private DefaultModelValidator validator = new DefaultModelValidator();

	@Test
	public void getDataTest() {
		ObjectNode data = JsonNodeFactory.instance.objectNode();

		Assert.assertNull("1", validator.getData(null, null));
		Assert.assertNull("2", validator.getData(data, null));
		Assert.assertNull("3", validator.getData(null, ""));
		Assert.assertNull("4", validator.getData(data, ""));
		Assert.assertNull("5", validator.getData(data, "test"));
		data.put("test", "test");
		Assert.assertEquals("6", "test", validator.getData(data, "test"));
	}
}
