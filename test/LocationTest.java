import api.Location;
import org.junit.*;

import static org.junit.Assert.*;

public class LocationTest {


    Location instance;

    public LocationTest() {
    }


    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        instance = new Location("Strathgou Dagkli", "Thessaloniki", "12121");
    }

    @After
    public void tearDown() {
    }


    @Test
    public void getSet() {
        assertEquals("Strathgou Dagkli", instance.getAddress());
        assertEquals("Thessaloniki", instance.getTown());
        assertEquals("12121", instance.getPostCode());
    }


}