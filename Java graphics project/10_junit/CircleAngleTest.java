import org.junit.jupiter.api.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests of the Circle class
 * To be run with JUnit 5: https://junit.org/junit5/
 * JUnit 5 User Guide: https://junit.org/junit5/docs/current/user-guide/
 *
*/

@DisplayName(" Circle Angle Tests ")
class CircleAngleTest {
    Circle defaultCircle = new Circle();
    Circle testCircle = new Circle(90,0,1);

    @BeforeEach
    void setUpTests() {
        defaultCircle = new Circle(); 
        testCircle = new Circle(90,0,1);
    }

    CircleAngleTest() {
    }

    @Test
    @DisplayName("Test Default Circle")
    void testConstructors() {
        assertEquals(true, defaultCircle.getX()==0 && defaultCircle.getY()==0,"default at origin");
        assertEquals(true, defaultCircle.getRadius()==1,"default radius");
    } // end testConstructor()
	
    @ParameterizedTest(name = "Testing with Angle Parameters")
    @DisplayName("Csv source angle test")
    @CsvSource({
        "0, 45, 90"
    })
    void testWithAnglesCS(int param0, int param1, int param2) {
        
        assertAll(
			"Tests 3 different angle",
			() -> assertEquals(param0, defaultCircle.angleBetween(testCircle), "Angle Between " + param0 + "?"),
            () -> assertEquals(param1, defaultCircle.angleBetween(testCircle), "Angle Between " + param1 + "?"),
            () -> assertEquals(param2, defaultCircle.angleBetween(testCircle), "Angle Between " + param2 + "?")
		); // closes the assertAll(subset)
    }

    @ParameterizedTest(name = "Testing Angles w/ValueSource")
    @DisplayName("ValueSource angle testing")
    @ValueSource(ints = {0,45,90})
    void testWithAnglesVS(int valueSource) {
        assertEquals(valueSource, defaultCircle.angleBetween(testCircle), "Angle Between " + valueSource + "?");
    }
    
} // end class CircleTests