package com.herokuapp.restful.herokuappinfo;

import com.herokuapp.restful.constant.EndPoints;
import com.herokuapp.restful.herocupsteps.HerokuAppSteps;
import com.herokuapp.restful.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class HerokuappCURDTestWithSteps extends TestBase {

    static String firstname = "Jim";
    static String lastName = "Brown";
    static int totalPrice = 110;
    static Boolean depositpaid = true;
    static String additionalneeds = "Breakfast";
    static int bookingID;

    @Steps
    HerokuAppSteps herokuAppSteps;

    @Title("This will create a new booking")
    @Test
    public void test001(){
        HashMap<Object, Object> dates = new HashMap<>();
        dates.put("checkin", "2018-01-01");
        dates.put("checkout", "2019-01-01");

        ValidatableResponse response = herokuAppSteps.createBooking(firstname,lastName,totalPrice,depositpaid,dates,additionalneeds);
        response.log().all().statusCode(200);
        bookingID = response.log().all().extract().path("bookingid");
        System.out.println(bookingID);
    }
    @Title("Verify if the booking was added to the list")
    @Test
    public void test002() {
        HashMap<String, Object> bookingMap = herokuAppSteps.getBookingMapInfoByID(bookingID);
        Assert.assertThat(bookingMap, hasValue(firstname));
        System.out.println(bookingMap);

    }

    @Title("Update the store information and verify updated information")
    @Test
    public void test003() {
        HashMap<Object, Object> dates = new HashMap<>();
        dates.put("checkin", "2018-01-01");
        dates.put("checkout", "2019-01-01");

        firstname = "James";
        depositpaid = false;
        herokuAppSteps.updateBooking(bookingID,firstname,lastName,totalPrice,depositpaid,dates,additionalneeds).log().all().statusCode(200);
        HashMap<String, Object> bookingMap = herokuAppSteps.getBookingMapInfoByID(bookingID);
        Assert.assertThat(bookingMap, hasValue(firstname));
    }

    @Title("Delete the booking and verify if the list is deleted!")
    @Test
    public void test004() {
        herokuAppSteps.deleteBooking(bookingID).statusCode(201);
        herokuAppSteps.getBookingById(bookingID).statusCode(404);
    }
    @Title("Getting all bookings")
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
