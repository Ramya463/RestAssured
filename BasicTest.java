import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.Argument;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BasicTest {

    public static String authtoken;

    @Test
    public void testStatusCode(){


        given()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products")
                .then()
                .statusCode(200);
    }

    @Test
    public void testLogging(){
        given()
                .log().all()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
    }

    @Test
    public void testResponse(){
        Response res=given().when()
        .log().all()
        .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");

       // System.out.println(res.asString());
       //System.out.println("*******************");
        System.out.println(res.prettyPrint());

    }

    @Test
    public void currencyTest(){

        Response res=given()

                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");

        JsonPath jsonpathevaluator=res.jsonPath();

        List<Map> products =jsonpathevaluator.getList("data");
        for (Map prod:products) {
            Map attributes=(Map) prod.get("attributes");
            System.out.println("Product ID::"+ prod.get("id") + " Currency::" + attributes.get("currency"));
            Assert.assertEquals(attributes.get("currency"),"USD");

        }


    }

    @Test
    public void testFilter(){
        Response res=given()
                .log().all()
                .queryParam("filter[price]",0 - 19.99)
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");

        System.out.println(res.prettyPrint());
    }

    @BeforeClass
    public void  authToken(){
        Response res=given()
                .formParam("grant_type","password")
                .formParam("username","ramya.panguluri@gmail.com")
                .formParam("password","sam@123")
                .post("https://spree-vapasi-prod.herokuapp.com/spree_oauth/token");

        System.out.println(res.prettyPrint());

        authtoken="Bearer " + res.path("access_token");
        System.out.println(authtoken);

    }


    @Test
    public void testCreateCartCall(){


        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", authtoken);

        Response res = given()
                .headers(headers)
                .when()
                .post("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart");
        Assert.assertEquals(res.statusCode(), 200);

    }

    @Test
    public void getProducts() {
        Response response = given()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        JsonPath jsonPath = response.jsonPath();
        List<Map> products = jsonPath.getList("data");
        System.out.println(products);
    }

    @Test
    public void testPostCall(){
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", authtoken);
        String createBody = "{\n" +
                "  \"variant_id\": \"12\",\n" +
                "  \"quantity\": 5\n" +
                "}";
        Response res = given()
                .headers(headers)
                .body(createBody)
                .when()
                .post("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart/add_item");
        Assert.assertEquals(res.statusCode(), 200);
    }

    @Test
    public void testProduct_DetailsCall(){


        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", authtoken);

        Response res = given()
                .headers(headers)
                .when()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products/2");
        Assert.assertEquals(res.statusCode(), 200);

    }

    @Test
    public void testDeleteCall(){


        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", authtoken);

        Response res = given()
                .headers(headers)
                .when()
                .delete("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart/remove_line_item/416");
        Assert.assertEquals(res.statusCode(), 200);

    }

    @Test
    public void testGetCall(){


        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", authtoken);

        Response res = given()
                .headers(headers)
                .when()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        Assert.assertEquals(res.statusCode(), 200);

    }






}
