import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class UserController {
    private String baseUrl;
    private String token;
    public UserController(String baseUrl, String token){
        this.baseUrl=baseUrl;
        this.token=token;
    }
    public UserController(String baseUrl){
        this.baseUrl=baseUrl;
    }
    public String login(String email, String password) throws ConfigurationException, IOException {
        RestAssured.baseURI=baseUrl;
        UserModel userModel=new UserModel();
        userModel.setEmailOrPhoneNumber(email);
        userModel.setPassword(password);
        Response res= given().contentType("application/json")
                .body(userModel)
                .when()
                .post("/user/login");
        JsonPath jsonPath= res.jsonPath();
        String token = jsonPath.get("token").toString();
        Utils.setEnvVariable("token",token);
        return token;
    }
    public Response createUser(UserModel userModel ) throws IOException {
        RestAssured.baseURI=baseUrl;
        Response res= given().contentType("application/json")
                .header("Authorization",token)
                .header("X-AUTH-SECRET-KEY","ROADTOSDET")
                .body(userModel)
                .when()
                .post("/user/create");
        return res;
    }
    public Response searchUser(String userId){
        RestAssured.baseURI=baseUrl;
        Response res= given().contentType("application/json")
                .header("Authorization",token)
                .header("X-AUTH-SECRET-KEY","ROADTOSDET")
                .when()
                .get("/user/search/id/"+userId);
        return res;
    }
}
