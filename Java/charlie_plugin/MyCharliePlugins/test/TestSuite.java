import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

// section 1 imports 12 to 21
import charlie.bs.section1.Test00_19_7;
import charlie.bs.section1.Test00_12_2;
import charlie.bs.section1.Test00_12_7;
import charlie.bs.section1.Test00_14_6;

// section 2 imports 5 to 11
import charlie.bs.section2.Test00_10_10;
import charlie.bs.section2.Test00_11_10;
import charlie.bs.section2.Test00_11_A;
import charlie.bs.section2.Test00_9_7;
import charlie.bs.section2.Test00_9_3;
import charlie.bs.section2.Test00_9_2;
import charlie.bs.section2.Test00_5_7;
import charlie.bs.section2.Test00_5_2;

// section 3 imports A,2 to A,10
import charlie.bs.section3.Test00_A2_2;
import charlie.bs.section3.Test00_A2_4;
import charlie.bs.section3.Test00_A2_5;
import charlie.bs.section3.Test00_A2_7;
import charlie.bs.section3.Test00_A3_4;
import charlie.bs.section3.Test00_A3_5;
import charlie.bs.section3.Test00_A7_2;
import charlie.bs.section3.Test00_A7_6;
import charlie.bs.section3.Test00_A7_7;
import charlie.bs.section3.Test00_A8_7;

/**
 *
 * @author Vivek Vellaiyappan Surulimuthu
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    // Section 1 Test Cases from 12 to 21
    Test00_14_6.class,
    Test00_12_2.class,
    Test00_12_7.class,    
    Test00_19_7.class,
    
    // Section 2 Test Cases from 5 to 11
    Test00_10_10.class,     
    Test00_11_10.class, 
    Test00_11_A.class, 
    Test00_9_7.class,     
    Test00_9_3.class, 
    Test00_9_2.class, 
    Test00_5_7.class,
    Test00_5_2.class,    
    
    // Section 3 Test Cases from A,2 to A,10
    Test00_A2_2.class,
    Test00_A2_4.class,
    Test00_A2_5.class,
    Test00_A2_7.class,
    Test00_A3_4.class,
    Test00_A3_5.class,
    Test00_A7_2.class,
    Test00_A7_6.class,
    Test00_A7_7.class,
    Test00_A8_7.class,    
})

public class TestSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {        
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
