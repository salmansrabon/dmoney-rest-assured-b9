import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class User {
    public Properties props;
    @BeforeTest
    public void setup() throws IOException {
        props=new Properties();
        FileInputStream fs=new FileInputStream("./src/test/resources/config.properties");
        props.load(fs);
    }
    @Test(priority = 1, enabled = true)
    public void login() throws ConfigurationException, IOException {
        RestAssured.baseURI=props.getProperty("baseUrl");
        Response res= given().contentType("application/json")
                .body("{\n" +
                        "    \"emailOrPhoneNumber\":\"01686606909\",\n" +
                        "    \"password\":\"1234\"\n" +
                        "}\n")
                .when()
                .post("/user/login");
        JsonPath jsonPath= res.jsonPath();
        String token = jsonPath.get("token").toString();
        Utils.setEnvVariable("token",token);
    }
    @Test(priority = 2)
    public void createUser() throws IOException {
        RestAssured.baseURI=props.getProperty("baseUrl");
        Response res= given().contentType("application/json")
                .header("Authorization",props.getProperty("token"))
                .header("X-AUTH-SECRET-KEY","ROADTOSDET")
                .body("{\n" +
                        "    \"name\":\"Rest assured B9 user 1\",\n" +
                        "    \"email\":\"restassuredb9user1@test.com\",\n" +
                        "    \"password\":\"1234\",\n" +
                        "    \"phone_number\":\"01478856977\",\n" +
                        "    \"nid\":\"123456789\",\n" +
                        "    \"role\":\"Customer\"\n" +
                        "}\n")
                .when()
                .post("/user/create");
        System.out.println(res.asString());
    }
}
