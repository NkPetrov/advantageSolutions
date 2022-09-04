import Configuration.TestBody;
import Configuration.TestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class CheckСrud  extends TestService {
    private final int cntData = 10;

    @BeforeAll
    public void generateTestData() throws Exception {
        List<TestBody> arr = Arrays.stream(get(defaultLimit,dafaultOffset).getBody().as(TestBody[].class)).toList();
        for (TestBody el: arr) {
            Assertions.assertEquals(delete(el.getId()).getStatusCode(), SC_NO_CONTENT);
        }

        if(get(defaultLimit,dafaultOffset).getBody().as(TestBody[].class).length == 0) throw new Exception("Data from DB didn't delete") ;

        for (int i = 0; i <= cntData; i++) {
            if(post(new TestBody(
                    i,
                    i % 2 == 0 ? "text" + i : "",
                    i % 2 == 0))
                    .getStatusCode() != SC_CREATED){
                throw new Exception("POST doesn't work");
            }
        }
    }

    @Test
    public void callGet(){
        Assertions.assertEquals(Arrays.stream(get(0,0).getBody().as(TestBody[].class)).toList().size(),0);
        Assertions.assertEquals(Arrays.stream(get(0,11).getBody().as(TestBody[].class)).toList().size(),11);
    }

    @Test
    public void callPost(){
        Assertions.assertEquals(post(new TestBody(0,"test0",true))
                .getStatusCode(), SC_BAD_REQUEST);
        Assertions.assertEquals(post(new TestBody(0,"test1",true))
                .getStatusCode(), SC_BAD_REQUEST);
        Assertions.assertEquals(post(new TestBody(0,"test1",false))
                .getStatusCode(), SC_BAD_REQUEST);
    }

    @Test
    public void callPut(){
        TestBody newBody = new TestBody(0,"test00",false);
        List<TestBody> arr = Arrays.stream(get(defaultLimit,dafaultOffset).getBody().as(TestBody[].class)).toList();
        for (TestBody el: arr) {
            if (el.getId() == 0){
                Assertions.assertNotEquals(el, newBody);
            }
        }

        Assertions.assertEquals(put(newBody,0).getStatusCode(), SC_CREATED);
        arr = Arrays.stream(get(defaultLimit,dafaultOffset).getBody().as(TestBody[].class)).toList();
        for (TestBody el: arr) {
            if (el.getId() == 0){
                Assertions.assertEquals(el, newBody);
            }
        }
    }

    @Test
    public void callDelete() throws Exception {
        int indexOfElement = 0;
        List<TestBody> arr = Arrays.stream(get(defaultLimit,dafaultOffset).getBody().as(TestBody[].class)).toList();
        for (TestBody el: arr) {
            if (el.getId() == indexOfElement){
                break;
            }
            throw new Exception("Test data doesn't found");
        }

        Assertions.assertEquals(delete(indexOfElement).getStatusCode(),SC_NO_CONTENT);

        arr = Arrays.stream(get(defaultLimit,dafaultOffset).getBody().as(TestBody[].class)).toList();
        for (TestBody el: arr) {
            if (el.getId() == indexOfElement){
                throw new Exception("DELETE doesn't work");
            }
        }
    }
}
