package minibank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountNumberTest {
    @Test
    void validNumberIsAccepted() {
        AccountNumber number = new AccountNumber("1234567890");
        assertEquals("1234567890", number.value());
    }

    @Test
    void nullNumberIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new AccountNumber(null));
    }

    @Test
    void emptyNumberIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new AccountNumber(""));
    }

    @Test
    void shortNumberIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new AccountNumber("123"));
    }

    @Test
    void numberWithLettersIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new AccountNumber("12345abcde"));
    }
}
