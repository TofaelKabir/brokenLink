package testPages;

import base.CommonAPI;
import org.testng.annotations.Test;

import java.io.IOException;

public class HomePage extends CommonAPI {
    @Test
    public void testBrokenLinks() throws IOException {
        brokenLink();

    }
}
