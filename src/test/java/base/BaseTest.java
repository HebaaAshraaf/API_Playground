package base;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    protected RequestSpecification request;
    protected String baseURL;
    protected Response response;

    @BeforeClass
    public void setTheBaseURL() {
        baseURL = "http://localhost:3030/";
    }
}
