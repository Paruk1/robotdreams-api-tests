package api;

import requestData.UserData;
import responseData.UserLogin;
import responseData.UserRegister;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HeaderConfig;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class BaseTest {

    private static final Logger log = LogManager.getLogger(BaseTest.class);

    protected String username = "Parail";
    protected String password = "1234567";
    protected String token;
    protected UserRegister userRegisterResponse;
    protected UserLogin userLoginResponse;


    @BeforeTest
    public void setup() {
        RestAssured.baseURI = "http://www.robotdreams.karpenko.cc/";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
        RestAssured.config =
                RestAssured.config
                .headerConfig(HeaderConfig.headerConfig().overwriteHeadersWithName("user-token"));

        username += username + randomAlphanumeric(7);
        log.info("Generated user with name = {}",username);

        log.info("Send request for Creating user with data: name={}, password={}",username,password);
        userRegisterResponse = RestAssured.given()
                .body(new UserData(username, password))
                .when()
                .post("/user")
                .then().statusCode(200)
                .extract().body().as(UserRegister.class);

        log.info("User was created with params: {}",userRegisterResponse);

        log.info("Send request for Login user with params: name={}, password={}",userRegisterResponse.username,userRegisterResponse.password);
        userLoginResponse = RestAssured.given()
                .queryParams("username",userRegisterResponse.username)
                .queryParams("password",userRegisterResponse.password)
                .when()
                .get("/login")
                .then().statusCode(200).extract().as(UserLogin.class);

        token = userLoginResponse.session_token;

        log.info("Successful login: {}",userLoginResponse);


        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("user-token",token)
                .build();
    }
}
