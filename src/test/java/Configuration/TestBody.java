package Configuration;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TestBody {
    private int id;
    private String text;
    private boolean completed;
}
