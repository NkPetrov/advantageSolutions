package configuration;

import dto.TestBody;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.TestInstance;

import java.math.BigInteger;

import static io.restassured.RestAssured.given;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class TestService extends TestCheck {
    private static final String uri = "http://localhost:8080/todos";
    private static final String authHash = "Basic YWRtaW46YWRtaW4=";
    private static final String authName = "Authorization";
    public static final int defaultLimit = 0;
    public static final int dafaultOffset = 2147483647;

    public static Response get(int limit, int offset) {
        return given()
                .param("limit", limit)
                .param("offset", offset)
                .contentType(ContentType.JSON)
                .get(uri);
    }

    public static Response get() {
        return given()
                .contentType(ContentType.JSON)
                .get(uri);
    }

    public static Response post(TestBody request) {
        return given()
                .contentType(ContentType.JSON)
                .body(request)
                .post(uri);
    }

    public static Response put(TestBody request, BigInteger id) {
        return given()
                .contentType(ContentType.JSON)
                .body(request)
                .put(uri + "/" + id);
    }

    public static Response delete(BigInteger id) {
        return given()
                .header(authName, authHash)
                .contentType(ContentType.JSON)
                .delete(uri + "/" + id);
    }
}
