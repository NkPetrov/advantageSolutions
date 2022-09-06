package configuration;

import dto.TestBody;
import org.assertj.core.api.SoftAssertions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class TestCheck {

    public static SoftAssertions softAssertions = new SoftAssertions();

    public static void checkIndexOutOfBoundsException(final List<TestBody> list, int indexOfElement) {
        String expectedMessage = "Index 0 out of bounds for length 0";

        Exception exception = assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            list.get(indexOfElement);
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage), expectedMessage);
    }
}
