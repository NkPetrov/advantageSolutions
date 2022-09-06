package test.crud;

import dto.TestBody;
import configuration.TestService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CheckСrud extends TestService {
    private final int cntData = 10;
    private final BigInteger bigIntegerFromZero = new BigInteger("0");

    @BeforeAll
    public void generateTestData() {
        List<TestBody> arr = Arrays.stream(get(defaultLimit, dafaultOffset).getBody().as(TestBody[].class)).toList();
        for (TestBody el : arr) {
            assertEquals(delete(el.getId()).getStatusCode(), SC_NO_CONTENT);
        }

        assertFalse(get(defaultLimit, dafaultOffset).getBody().as(TestBody[].class).length == 0, "Data from DB didn't delete");

        for (int i = 0; i <= cntData; i++) {
            assertFalse(post(new TestBody(
                    new BigInteger(String.valueOf(i)),
                    i % 2 == 0 ? "text" + i : "",
                    i % 2 == 0))
                    .getStatusCode() != SC_CREATED, "POST doesn't work");
        }
    }

    @Test
    public void callGet() {
        softAssertions.assertThat(get(-1, 0).getStatusCode()).isEqualTo(SC_BAD_REQUEST);
        softAssertions.assertThat(get(0, -1).getStatusCode()).isEqualTo(SC_BAD_REQUEST);

        softAssertions.assertThat(Arrays.stream(get(0, 0).getBody().as(TestBody[].class)).toList().size()).isEqualTo(0);
        softAssertions.assertThat(Arrays.stream(get(0, 11).getBody().as(TestBody[].class)).toList().size()).isEqualTo(11);
        softAssertions.assertAll();
    }

    @Test
    public void callPost() {
        softAssertions.assertThat(post(new TestBody(bigIntegerFromZero, "test0", true))
                .getStatusCode()).isEqualTo(SC_BAD_REQUEST);
        softAssertions.assertThat(post(new TestBody(bigIntegerFromZero, "test1", true))
                .getStatusCode()).isEqualTo(SC_BAD_REQUEST);
        softAssertions.assertThat(post(new TestBody(bigIntegerFromZero, "test1", false))
                .getStatusCode()).isEqualTo(SC_BAD_REQUEST);
        softAssertions.assertAll();
    }

    @Test
    public void callPut() {
        TestBody newBody = new TestBody(bigIntegerFromZero, "test00", false);
        List<TestBody> arr = Arrays.stream(get(defaultLimit, dafaultOffset).getBody().as(TestBody[].class)).toList();
        for (TestBody el : arr) {
            if (el.getId().equals(bigIntegerFromZero)) {
                assertNotEquals(el, newBody);
            }
        }

        assertEquals(put(newBody, bigIntegerFromZero).getStatusCode(), SC_CREATED);
        arr = Arrays.stream(get(defaultLimit, dafaultOffset).getBody().as(TestBody[].class)).toList();
        for (TestBody el : arr) {
            if (el.getId().equals(bigIntegerFromZero)) {
                assertEquals(el, newBody);
            }
        }

        assertEquals(put(
                new TestBody(new BigInteger("20"), "test20", false),
                bigIntegerFromZero).getStatusCode(), SC_NOT_FOUND, "PUT works with wrong id");
    }

    @Test
    public void callDelete() {
        checkIndexOutOfBoundsException(Arrays.stream(get(defaultLimit, dafaultOffset).getBody().as(TestBody[].class)).toList(), bigIntegerFromZero.intValue());

        assertEquals(delete(bigIntegerFromZero).getStatusCode(), SC_NO_CONTENT,"DELETE doesn't work");

        checkIndexOutOfBoundsException(Arrays.stream(get(defaultLimit, dafaultOffset).getBody().as(TestBody[].class)).toList(), bigIntegerFromZero.intValue());

        assertEquals(delete(bigIntegerFromZero).getStatusCode(), SC_NOT_FOUND,"DELETE works with wrong id");
    }
}
