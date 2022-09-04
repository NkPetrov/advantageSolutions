import Configuration.TestBody;
import Configuration.TestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.apache.http.HttpStatus.SC_REQUEST_TOO_LONG;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_CREATED;

public class CheckPost extends TestService {

    @BeforeAll
    public void deleteTestData() throws Exception {
        List<TestBody> arr = Arrays.stream(get(defaultLimit,dafaultOffset).getBody().as(TestBody[].class)).toList();
        for (TestBody el: arr) {
            Assertions.assertEquals(delete(el.getId()).getStatusCode(), SC_NO_CONTENT);
        }

        if(get(defaultLimit,dafaultOffset).getBody().as(TestBody[].class).length == 0) throw new Exception("Data from DB didn't deleted");
    }

    @Test
    public void sendEmptyString(){
       Assertions.assertEquals(post(new TestBody(0,"",true)).getStatusCode(), SC_CREATED);
    }

    @Test
    public void sendLargeString(){
        int maxSymbols = 100;
        StringBuilder bigString = new StringBuilder();

        bigString.append("a".repeat(maxSymbols + 1));

      Assertions.assertEquals(post(new TestBody(1, bigString.toString(), true)).getStatusCode(), SC_REQUEST_TOO_LONG);
    }
}
