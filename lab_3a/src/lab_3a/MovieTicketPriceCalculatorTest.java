package lab_3a;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovieTicketPriceCalculatorTest {

    private MovieTicketPriceCalculator calculator;

    @BeforeEach
    public void setUp() {
        LocalTime startMatineeTime = LocalTime.of(10, 0); // 10:00 AM
        LocalTime endMatineeTime = LocalTime.of(16, 0); // 4:00 PM
        int maxChildAge = 12;
        int minSeniorAge = 65;

        calculator = new MovieTicketPriceCalculator(startMatineeTime, endMatineeTime, maxChildAge, minSeniorAge);
    }

    @Test
    public void testComputePrice_standardAdult() {
        LocalTime scheduledTime = LocalTime.of(18, 0); // 6:00 PM (not a matinee)
        int age = 30; // adult

        int expectedPrice = 2700; // standard price
        int actualPrice = calculator.computePrice(scheduledTime, age);

        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void testComputePrice_matineeAdult() {
        LocalTime scheduledTime = LocalTime.of(11, 0); // 11:00 AM (matinee)
        int age = 30; // adult

        int expectedPrice = 2400; // matinee price
        int actualPrice = calculator.computePrice(scheduledTime, age);

        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void testComputePrice_childMatinee() {
        LocalTime scheduledTime = LocalTime.of(14, 0); // 2:00 PM (matinee)
        int age = 10; // child

        int expectedPrice = 2100; // matinee price - child discount
        int actualPrice = calculator.computePrice(scheduledTime, age);

        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void testComputePrice_seniorStandard() {
        LocalTime scheduledTime = LocalTime.of(19, 0); // 7:00 PM (not a matinee)
        int age = 70; // senior

        int expectedPrice = 2300; // standard price - senior discount
        int actualPrice = calculator.computePrice(scheduledTime, age);

        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void testComputePrice_matineeBoundary() {
        LocalTime scheduledTime = LocalTime.of(10, 0); // 10:00 AM (start of matinee)
        int age = 30; // adult

        int expectedPrice = 2400; // matinee price
        int actualPrice = calculator.computePrice(scheduledTime, age);

        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void testInvalidMatineeTimeConfigurationWrongTime() {
        LocalTime startMatineeTime = LocalTime.of(16, 0); // 4:00 PM
        LocalTime endMatineeTime = LocalTime.of(10, 0); // 10:00 AM

        assertThrows(IllegalArgumentException.class, () -> 
            new MovieTicketPriceCalculator(startMatineeTime, endMatineeTime, 12, 65)
        );
    }
    @Test
    public void testInvalidMatineeTimeConfigurationNull1() {
        LocalTime endMatineeTime = LocalTime.of(10, 0); // 10:00 AM

        assertThrows(NullPointerException.class, () -> 
            new MovieTicketPriceCalculator(null, endMatineeTime, 12, 65)
        );
    }
    @Test
    public void testInvalidMatineeTimeConfigurationNull2() {
        LocalTime startMatineeTime = LocalTime.of(16, 0); // 4:00 PM

        assertThrows(NullPointerException.class, () -> 
            new MovieTicketPriceCalculator(startMatineeTime, null, 12, 65)
        );
    }
}
