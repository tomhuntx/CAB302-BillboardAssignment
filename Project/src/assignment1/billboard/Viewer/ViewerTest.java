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
                        "    <message>Billboard with message and picture with data attribute</message>\n" +
                        "    <picture data=\"iVBORw0KGgoAAAANSUhEUgAAACAAAAAQCAIAAAD4YuoOAAAAKXRFWHRDcmVhdGlvbiBUaW1lAJCFIDI1IDMgMjAyMCAwOTowMjoxNyArMDkwMHlQ1XMAAAAHdElNRQfkAxkAAyQ8nibjAAAACXBIWXMAAAsSAAALEgHS3X78AAAABGdBTUEAALGPC/xhBQAAAS5JREFUeNq1kb9KxEAQxmcgcGhhJ4cnFwP6CIIiPoZwD+ALXGFxj6BgYeU7BO4tToSDFHYWZxFipeksbMf5s26WnAkJki2+/c03OzPZDRJNYcgVwfsU42cmKi5YjS1s4p4DCrkBPc0wTlkdX6bsG4hZQOj3HRDLHqh08U4Adb/zgEMtq5RuH3Axd45PbftdB2wO5OsWc7pOYaOeOk63wYfdFtL5qldB34W094ZfJ+4RlFldTrmW/ZNbn2g0of1vLHdZq77qSDCaSAsLf9kXh9w44PNoR/YSPHycEmbIOs5QzBJsmDHrWLPeF24ZkCe6ZxDCOqHcmxmsr+hsicahss+n8vYb8NHZPTJxi/RGC5IqbRwqH6uxVTX+5LvHtvT/V/R6PGh/iF4GHoBAwz7RD26spwq6Amh/AAAAAElFTkSuQmCC\" />\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard>\n" +
                        "    <picture url=\"https://cloudstor.aarnet.edu.au/plus/s/vYipYcT3VHa1uNt/download\" />\n" +
                        "    <information>Billboard with picture (with URL attribute) and information text only. The picture is now centred within the top 2/3 of the image and the information text is centred in the remaining space below the image.</information>\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard>\n" +
                        "    <message>Billboard with message, GIF and information</message>\n" +
                        "    <picture url=\"https://cloudstor.aarnet.edu.au/plus/s/A26R8MYAplgjUhL/download\" />\n" +
                        "    <information>This billboard has a message tag, a picture tag (linking to a URL with a GIF image) and an information tag. The picture is drawn in the centre and the message and information text are centred in the space between the top of the image and the top of the page, and the space between the bottom of the image and the bottom of the page, respectively.</information>\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard>\n" +
                        "    <message>Billboard with message and info</message>\n" +
                        "    <information>Billboard with a message tag, an information tag, but no picture tag. The message is centred within the top half of the screen while the information is centred within the bottom half.</information>\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard>\n" +
                        "    <message colour=\"#FFC457\">Billboard with default background and custom-coloured message</message>\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard background=\"#7F3FBF\">\n" +
                        "    <message>Billboard with custom background and default-coloured message</message>\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard>\n" +
                        "    <message>Default-coloured message</message>\n" +
                        "    <picture url=\"https://cloudstor.aarnet.edu.au/plus/s/X79GyWIbLEWG4Us/download\" />\n" +
                        "    <information colour=\"#60B9FF\">Custom-coloured information text</information>\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard background=\"#6800C0\">\n" +
                        "    <message colour=\"#FF9E3F\">All custom colours</message>\n" +
                        "    <information colour=\"#3FFFC7\">All custom colours</information>\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard background=\"#555555\">\n" +
                        "    <message colour=\"#FFFFFF\">The information text is always smaller than the message text</message>\n" +
                        "    <information colour=\"#DDDDDD\">The information text is always smaller than the message text</information>\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard background=\"#FFC457\">\n" +
                        "    <picture url=\"https://cloudstor.aarnet.edu.au/plus/s/EvYVdlUNx72ioaI/download\" />\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard background=\"#FF38C3\">\n" +
                        "    <picture url=\"https://cloudstor.aarnet.edu.au/plus/s/a2IioOedKQgQwvQ/download\" />\n" +
                        "</billboard>",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<billboard background=\"#8996FF\">\n" +
                        "    <picture url=\"https://cloudstor.aarnet.edu.au/plus/s/5fhToroJL0nMKvB/download\" />\n" +
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
            billboardContents = new BillboardContents(billboards[9]);
        }
    }
}
