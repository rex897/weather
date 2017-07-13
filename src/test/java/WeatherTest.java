import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by user on 13.07.2017.
 */
public class WeatherTest {

  @Test
  public void ftoC() throws Exception {
    Weather weather = new Weather();
    int c1 = 0;
    int c2 = 15;
    int c3 = 25;
    int c4 = 100;
    int f1 = 32;
    int f2 = 59;
    int f3 = 77;
    int f4 = 212;
    assertEquals(c1, weather.FtoC(f1));
    assertEquals(c2, weather.FtoC(f2));
    assertEquals(c3, weather.FtoC(f3));
    assertEquals(c4, weather.FtoC(f4));
  }

}