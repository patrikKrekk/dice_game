import model.PreviousRoll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class DiceControllerTest {

    @Test
    public void testPreviousRollValue() {
        PreviousRoll previousRoll = new PreviousRoll();
        previousRoll.addRoll(4);
        previousRoll.addRoll(2);
        previousRoll.addRoll(6);

        List<Integer> rolls = previousRoll.getRolls();

        for (Integer roll : rolls) {
            Assertions.assertTrue(roll <= 6, "Nem lehet nagyobb, mint 6");
        }
    }
}
