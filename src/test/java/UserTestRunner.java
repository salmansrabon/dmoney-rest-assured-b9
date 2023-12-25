import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class UserTestRunner extends Setup {
    @Test(priority = 1, description = "User can login successfully")
    public void login() throws ConfigurationException, IOException {
        UserController user=new UserController(props.getProperty("baseUrl"));
        String token= user.login("salman@roadtocareer.net","1234");
        System.out.println(token);
    }
    @Test(priority = 2, description = "Admin can create new user")
    public void createNewUser() throws IOException, ConfigurationException {
        UserController user=new UserController(props.getProperty("baseUrl"), props.getProperty("token"));
        Faker faker=new Faker();
        UserModel userModel=new UserModel();
        userModel.setName(faker.name().fullName());
        userModel.setEmail(faker.internet().emailAddress());
        userModel.setPassword("1234");
        userModel.setPhone_number("0140"+Utils.generateRandom(1000000,9999999));
        userModel.setNid("123456789");
        userModel.setRole("Customer");
        Response res= user.createUser(userModel);
        System.out.println(res.asString());

        JsonPath jsonPath= res.jsonPath();
        String message = jsonPath.get("message").toString();

        Assert.assertTrue(message.contains("User created"));
        String userId= jsonPath.get("user.id").toString();
        Utils.setEnvVariable("userId",userId);
    }
    @Test(priority = 3, description = "Admin can search user by id")
    public void searchUser(){
        UserController user=new UserController(props.getProperty("baseUrl"), props.getProperty("token"));
        Response res= user.searchUser(props.getProperty("userId"));
        JsonPath jsonPath= res.jsonPath();
        System.out.println(jsonPath.get().toString());
    }
}
