package functional;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;
import models.Customer;

import org.junit.Test;

import play.libs.F.Callback;
import play.test.TestBrowser;
import util.CustomerHelper;

public class DopplegangerTest {
	
    private static final int PORT = 3333;
	private static final String SERVER = "http://localhost:"+PORT;

	@Test
    public void shouldShowTheInfoPage() {
        running(testServer(PORT, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo(SERVER);
                assertThat(browser.pageSource()).contains("Dopplegänger");
            }
        });
    }
    
    @Test
    public void shouldRetrieveListOfCustomers() {
        running(testServer(PORT, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo(SERVER + controllers.routes.Customers.list());
                assertThat(browser.pageSource()).contains("[]");
                
                Customer firstCustomer = CustomerHelper.newRandomCustomer();
                firstCustomer.save();

                browser.goTo(SERVER + controllers.routes.Customers.list());
                assertThat(browser.pageSource()).contains("\"firstName\":" + "\"" + firstCustomer.firstName + "\"");
                assertThat(browser.pageSource()).contains("\"lastName\":" + "\"" + firstCustomer.lastName + "\"");

                Customer secondCustomer = CustomerHelper.newRandomCustomer();
                secondCustomer.save();
                
                browser.goTo(SERVER + controllers.routes.Customers.list());
                assertThat(browser.pageSource()).contains("\"firstName\":" + "\"" + firstCustomer.firstName + "\"");
                assertThat(browser.pageSource()).contains("\"lastName\":" + "\"" + firstCustomer.lastName + "\"");
                assertThat(browser.pageSource()).contains("\"firstName\":" + "\"" + secondCustomer.firstName + "\"");
                assertThat(browser.pageSource()).contains("\"lastName\":" + "\"" + secondCustomer.lastName + "\"");
            }
        });
    }

}
