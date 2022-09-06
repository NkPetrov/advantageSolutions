package dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;

import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TestBody {
    private BigInteger id;
    private String text;
    private boolean completed;
}
