package test.post;

import dto.TestBody;
import configuration.TestService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.apache.http.HttpStatus.SC_REQUEST_TOO_LONG;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CheckPost extends TestService {

    @BeforeAll
    public void deleteTestData() {
        List<TestBody> arr = Arrays.stream(get(defaultLimit, dafaultOffset).getBody().as(TestBody[].class)).toList();
        for (TestBody el : arr) {
            assertEquals(delete(el.getId()).getStatusCode(), SC_NO_CONTENT);
        }

        assertFalse(get(defaultLimit, dafaultOffset).getBody().as(TestBody[].class).length == 0, "Data from DB didn't deleted");
    }

    @Test
    public void sendEmptyString() {
        assertEquals(post(new TestBody(new BigInteger("0"), "", true)).getStatusCode(), SC_CREATED);
    }

    @Test
    public void sendLargeString() {
        int maxSymbols = 100;
        StringBuilder bigString = new StringBuilder();

        bigString.append("a".repeat(maxSymbols + 1));

        assertEquals(post(new TestBody(new BigInteger("1"), bigString.toString(), true)).getStatusCode(), SC_REQUEST_TOO_LONG);
    }

    @Test
    public void checkNegativeCase() {
        softAssertions.assertThat(post(new TestBody()).getStatusCode()).isEqualTo(SC_BAD_REQUEST).as("With empty body");
        softAssertions.assertThat(post(new TestBody(new BigInteger("-1"), "", true)).getStatusCode()).isEqualTo(SC_BAD_REQUEST).as("Check u64 min value");
        softAssertions.assertThat(post(new TestBody(new BigInteger("18446744073709551616"), "", true)).getStatusCode()).isEqualTo(SC_BAD_REQUEST).as("Check u64 max value");
        softAssertions.assertThat(post(new TestBody(new BigInteger("18446744073709551615"), "", true)).getStatusCode()).isEqualTo(SC_BAD_REQUEST).as("Check u64 max value -1");
    }
}
