package assignment1.billboard;

import assignment1.billboard.Viewer.BillboardContents;
import assignment1.billboard.Viewer.Display;
import org.junit.jupiter.api.*;


public class ViewerTest {


    public class DisplayTest {
        private Display display;

        @BeforeEach
        public void init(){
            display = new Display();
        }

        @Test
        public void testNewXML(){
            String test = display.newXML();
            assert(!test.isEmpty());
        }
    }
}
