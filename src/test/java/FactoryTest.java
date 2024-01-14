import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyFirstJUnitJupiterTests {

    @BeforeEach
    void setUp() {

    }


    @Test
    void addition() {
        assertEquals(2, 1 + 1);
    }

}