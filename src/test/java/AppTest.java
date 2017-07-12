import org.junit.Test;

import static org.junit.Assert.*;

public class AppTest {
    @Test
    public void isCommand() throws Exception {
        App app = new App();

        assertTrue("Test Failed! /weather must be considered as command",
                app.isCommand("/weather"));

        assertTrue("Test Failed! /weather ufa must be considered as command",
                app.isCommand("/weather ufa"));

        assertTrue("Test Failed! /weather ufa must be considered as command",
                app.isCommand("/start"));

        assertFalse("Test failed! asdds is not command, but was considered as command",
                app.isCommand("asdds"));

        assertFalse("Test Failed! 'asd/weather ufa' must not be considered as command",
                app.isCommand("asd/weather ufa"));

        assertFalse("Test Failed! '/ping' must not be considered as command",
                app.isCommand("/ping"));

        assertFalse("Test Failed! null must not be considered as command",
                app.isCommand(null));

    }

}