package functional;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import controllers.Doppelganger;
import play.mvc.Result;
import util.Base;
import util.DuplicateIdentifier;

public class DoppelgangerTest extends Base {
	
	@Mock
	public static DuplicateIdentifier duplicateIdentifier;
	
	@Before
	public static void setUp() {
		Doppelganger.setDuplicateIdentifier(duplicateIdentifier);
	}
	
	@Test
	public void shouldRetrieveEmptyDuplicates() throws Exception {
		Result result = callAction(controllers.routes.ref.Doppelganger.duplicates());
		assertThat(status(result), is(OK));
		assertThat(contentType(result), is("application/json"));
		assertThat(contentAsString(result), is("[]"));
	}
	
	@Test
	public void shouldRetrieveValidDuplicates() throws Exception {
		
		
		Result result = callAction(controllers.routes.ref.Doppelganger.duplicates());
		assertThat(status(result), is(OK));
		assertThat(contentType(result), is("application/json"));
		assertThat(contentAsString(result), is("[]"));
	}
}
