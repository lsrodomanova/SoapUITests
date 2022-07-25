package guru.qa;

import guru.qa.domain.GetCountryResponse;
import io.restassured.RestAssured;
import io.restassured.internal.util.IOUtils;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SoapTests {
    @Test
    void getCountries() throws Exception {
        JAXBContext jbCtx = JAXBContext.newInstance(GetCountryResponse.class);
        Unmarshaller unmarshaller = jbCtx.createUnmarshaller();

        InputStream is = SoapTests.class.getClassLoader().getResourceAsStream("getCountryRequest.xml");
        final String request = new String(IOUtils.toByteArray(is));

        RestAssured.baseURI = "http://localhost:8080/ws";

        Response response = given()
                .header("Content-Type", "text/xml")
                .and()
                .body(request)
                .when()
                .post("/getCountry")
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println(response.asString());
        assertTrue(response.asString().contains("Madrid"));
        // TODO implement jaxb unmarshalling
    }
}
