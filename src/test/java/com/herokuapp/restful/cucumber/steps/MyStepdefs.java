package com.herokuapp.restful.cucumber.steps;

import com.herokuapp.restful.herocupsteps.HerokuAppSteps;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

public class MyStepdefs {

    static ValidatableResponse response;
    static String firstname = "Jim";
    static String lastName = "Brown";
    static int totalPrice = 110;
    static Boolean depositpaid = true;
    static String additionalneeds = "Breakfast";
    static int bookingID;

    @Steps
    HerokuAppSteps herokuAppSteps;

    @Given("^I am on Homepage of application herokuapp$")
    public void iAmOnHomepageOfApplicationHerokuapp() {
    }

    @When("^User send a GET Request to list endpoint booking$")
    public void userSendAGETRequestToListEndpointBooking() {
    response =  herokuAppSteps.getAllBooking();
    }

    @Then("^User can get back a valid status code (\\d+) of booking$")
    public void userCanGetBackAValidStatusCodeOfBooking(int code) {
        response.statusCode(code);
        response.assertThat().statusCode(code);
    }

    @When("^User can create new booking using POST method in booking application$")
    public void userCanCreateNewBookingUsingPOSTMethodInBookingApplication() {
        HashMap<Object, Object> dates = new HashMap<>();
        dates.put("checkin", "2018-01-01");
        dates.put("checkout", "2019-01-01");

        ValidatableResponse response = herokuAppSteps.createBooking(firstname,lastName,totalPrice,depositpaid,dates,additionalneeds);
        response.log().all().statusCode(200);
        bookingID = response.log().all().extract().path("bookingid");
        System.out.println(bookingID);
    }

    @When("^User can update new booking using PUT method in booking application$")
    public void userCanCreateNewBookingUsingPUTMethodInBookingApplication() {
        HashMap<Object, Object> dates = new HashMap<>();
        dates.put("checkin", "2018-01-01");
        dates.put("checkout", "2019-01-01");

        firstname = "James";
        depositpaid = false;
      response =  herokuAppSteps.updateBooking(bookingID,firstname,lastName,totalPrice,depositpaid,dates,additionalneeds).log().all().statusCode(200);
        HashMap<String, Object> bookingMap = herokuAppSteps.getBookingMapInfoByID(bookingID);
        Assert.assertThat(bookingMap, hasValue(firstname));
    }

    @When("^User can Delete new booking using DELETE method in booking application$")
    public void userCanDeleteNewBookingUsingDELETEMethodInBookingApplication() {
       response = herokuAppSteps.deleteBooking(bookingID);
        response.assertThat().statusCode(201);
    }

    @And("^User verify that the product is deleted successfully from product$")
    public void userVerifyThatTheProductIsDeletedSuccessfullyFromProduct() {
      response =   herokuAppSteps.getBookingById(bookingID);
        response.assertThat().statusCode(404);
    }
}
