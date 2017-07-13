import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by user on 13.07.2017.
 */
public class WeatherTest {
    @Test
    public void ftoC() throws Exception {
        Weather weather = new Weather();
        int f = 90;
        int c = (( f - 32) * 5) / 9;
        assertEquals(c, weather.FtoC(f));
    }

}