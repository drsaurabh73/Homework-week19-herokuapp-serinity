package com.herokuapp.restful.herokuappinfo;

import com.herokuapp.restful.constant.EndPoints;
import com.herokuapp.restful.testbase.TestBase;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Test;

public class FirstTest extends TestBase {
    @Test
    public void getAllBooking(){
        SerenityRest.given()
                .when()
                .get(EndPoints.GET_ALL_BOOKING)
                .then()
                .log().all()
                .statusCode(200);
    }

}
