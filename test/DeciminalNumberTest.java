import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
class DeciminalNumberTest {

    @Test
    public void addition() {
        assertEquals(new DeciminalNumber("-14", "1452"), new DeciminalNumber(13.1).addition(new DeciminalNumber(-27.2452)));
        assertEquals(new DeciminalNumber("-1", "06"), new DeciminalNumber(-0.08).addition(new DeciminalNumber(-0.98)));
        assertEquals(new DeciminalNumber("-4", "93"), new DeciminalNumber(0.09).addition(new DeciminalNumber(-5.0200)));
        assertEquals(new DeciminalNumber("-6", "367"), new DeciminalNumber(0.38).addition(new DeciminalNumber(-6.747)));
        assertEquals(new DeciminalNumber("9", "8"), new DeciminalNumber(2.3).addition(new DeciminalNumber(7.5)));
    }

    @Test
    public void substraction() {
        assertEquals(new DeciminalNumber("40", "3452"), new DeciminalNumber(13.1).subtraction(new DeciminalNumber(-27.2452)));
        assertEquals(new DeciminalNumber("-7", "027"), new DeciminalNumber(-6.025).subtraction(new DeciminalNumber(1.002)));
        assertEquals(new DeciminalNumber("-17", "617"), new DeciminalNumber(4.009).subtraction(new DeciminalNumber(21.626)));
        assertEquals(new DeciminalNumber("-0", "056"), new DeciminalNumber(-0.003).subtraction(new DeciminalNumber(0.053)));
    }

    @Test
    public void multiplication() {
        assertEquals(new DeciminalNumber("-356", "91212"), new DeciminalNumber(13.1).multiplication(new DeciminalNumber(-27.2452)));
        assertEquals(new DeciminalNumber("-10", "1109816"), new DeciminalNumber(-10.0908).multiplication(new DeciminalNumber(1.002)));
        assertEquals(new DeciminalNumber("0", "00018"), new DeciminalNumber(-0.001).multiplication(new DeciminalNumber(-0.18)));

    }
}