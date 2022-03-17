package GetTests;

import base.BaseTest;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GetCategories extends BaseTest {
    @BeforeMethod
    public void setEndPoint(){
        RestAssured.baseURI = baseURL + "categories";
        request = RestAssured.given();
    }

    @DataProvider public Object[][] idNumbers(){
        return new Object[][] {{"abcat0010000"}};
    }

    @DataProvider public Object[][] limitNumbers(){
        return new Object[][] {{"1"},{"2"}};
    }

    @DataProvider public Object[][] idAndName(){
        return new Object[][] {{"abcat0010000","Gift Ideas"},
                {"abcat0020001","Learning Toys"},
                {"abcat0020002","DVD Games"},
                {"abcat0020004","Unique Gifts"}};
    }

    @Test(dataProvider = "limitNumbers", description = "Test That when user need categories limit" +
            " to any number the api response with the same number")
    public void getCategoriesWithLimit(String limit){
        response = request.param("$limit",limit).get();
        Assert.assertEquals(response.getStatusCode(),200);
        System.out.println(response.then().extract().path("limit").toString());
        Assert.assertEquals(response.then().extract().path("limit").toString(),limit);
    }

    @Test(dataProvider = "idNumbers", description = "Test That User can Search about a specific ID in Categories")
    public void getCategoryWithRightID(String id){
        response = request.get("/" + id);
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.then().extract().path("id").toString(),id);
    }

    @Test(description = "Test That when user search about a wrong ID in categories" +
            "the API will response with 404")
    public void getCategoryWithWrongID(){
        String id = "121313";
        response = request.get("/"+ id);
        Assert.assertEquals(response.getStatusCode(),404);
        Assert.assertEquals(response.then().extract().path("message").toString(),"No record found for id '" + id + "'");
        System.out.println(response.then().extract().path("message").toString());
    }

    @Test(description = "Test That User can Read the 10 records after 4000 record")
    public void getCategoriesWithSkip4000(){
        response = request.param("$skip","4000").get();
        Assert.assertEquals(response.getStatusCode(),200);
        System.out.println(response.then().extract().path("skip").toString());
        Assert.assertEquals(response.then().extract().path("skip").toString(),"4000");
    }

    @Test(dataProvider = "idAndName", description = "Check the Name of the specific Category that will be searched by ID")
    public void getNameOfCategoryID(String id, String name){
        response = request.get("/"+ id);
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.then().extract().path("name").toString(),name);
    }
}
