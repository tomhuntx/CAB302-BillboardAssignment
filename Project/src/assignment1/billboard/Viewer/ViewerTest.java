package assignment1.billboard.Viewer;

import org.junit.jupiter.api.*;

/**
 * JUnit Testing Class for Viewer program
 */
public class ViewerTest {

    /**
     * Class which tests Display class
     */
    public class DisplayTest {
        private Display display;

        /**
         * new display object
         */
        @BeforeEach
        public void init(){
            display = new Display();
        }

        /**
         * tests runDisplay method
         */
        @Test
        public void testRunDisplay(){
            display.runDisplay();
        }

        /**
         * tests delayDisplay method
         */
        @Test
        public void testDelayDisplay(){
            display.delayDisplay(1000);
        }
    }

    /**
     * Class which tests BillboardContents class
     */
    public class BillboardContentsTest {
        private BillboardContents billboardContents;
        private String[] billboards = new String[]{"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<billboard>\n" +
                "    <message>Basic message-only billboard</message>\n" +
                "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard>\n" +
                        "    <information>Billboard with an information tag and nothing else. Note that the text is word-wrapped. The quick brown fox jumped over the lazy dogs.</information>\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard>\n" +
                        "    <picture url=\"https://cloudstor.aarnet.edu.au/plus/s/62e0uExNviPanZE/download\" />\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard>\n" +
                        "    <message>Default-coloured message</message>\n" +
                        "    <picture url=\"https://cloudstor.aarnet.edu.au/plus/s/X79GyWIbLEWG4Us/download\" />\n" +
                        "    <information colour=\"#60B9FF\">Custom-coloured information text</information>\n" +
                        "</billboard>"};

        /**
         * shows GUI
         */
        @AfterEach
        public void showGUI() {
            billboardContents.showGUI();
        }

        /**
         * tests message only billboard
         */
        @Test
        public void testMessageOnly() {
            billboardContents = new BillboardContents(billboards[0]);
        }

        /**
         * tests information only billboard
         */
        @Test
        public void testInformationOnly() {
            billboardContents = new BillboardContents(billboards[1]);
        }

        /**
         * tests picture URL only billboard
         */
        @Test
        public void testPictureURLOnly() {
            billboardContents = new BillboardContents(billboards[2]);
        }

        /**
         * tests picture data only billboard
         */
        @Test
        public void testPictureDataOnly() {
            billboardContents = new BillboardContents(billboards[3]);
        }

        /**
         * tests all labels billboard
         */
        @Test
        public void testAllLabels() {
            billboardContents = new BillboardContents(billboards[4]);
        }
    }
}
