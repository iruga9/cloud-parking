package br.com.juniormoura.parking.controller;

import br.com.juniormoura.parking.controller.dto.ParkingCreateDto;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingControllerIT extends AbstractContainerBase {

    @LocalServerPort
    private int randomPort;

    @BeforeEach
    public void setUpTest(){
        RestAssured.port = randomPort;
    }

    @Test
    void whenFindAllThenCheckResult() {
        RestAssured.given()
                .auth()
                .basic("user", "123456")
                .when()
                .get("/parking")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void whenCreateThenCheckIsCreated() {
        var createDTO = new ParkingCreateDto();
        createDTO.setColor("AMARELA");
        createDTO.setLicense("WTF-6666");
        createDTO.setModel("BRASILIA");
        createDTO.setState("CE");

        RestAssured.given()
                .when()
                .auth()
                .basic("user", "123456")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createDTO)
                .post("/parking")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("license", Matchers.equalTo("WTF-6666"));
    }
}