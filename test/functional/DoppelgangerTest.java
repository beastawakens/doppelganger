package functional;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.CREATED;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.test.Helpers.*;

import org.junit.Test;

import play.mvc.Result;
import util.Base;

public class DoppelgangerTest extends Base {
	
	@Test
	public void shouldRetrieveDuplicates() throws Exception {
		Result result = callAction(controllers.routes.ref.Doppelganger.duplicates());
		assertThat(status(result), is(OK));
	}
}
