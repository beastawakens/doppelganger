package integration;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.junit.Test;

import play.libs.F.Callback;
import play.test.TestBrowser;

public class DoppelgangerIntegrationTest {
	
    private static final int PORT = 3333;
	private static final String SERVER = "http://localhost:"+PORT;

	@Test
    public void shouldShowTheInfoPage() {
        running(testServer(PORT, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo(SERVER);
                assertThat(browser.pageSource()).contains("Doppelgänger");
            }
        });
    }
    


}
