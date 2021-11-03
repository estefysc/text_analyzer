import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    @Test
    void getConnection() {
        Controller controller = new Controller();
        Document connectionTest = controller.getConnection("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm");

        assertNotEquals(null, connectionTest);
    }

    @Test
    void scrapPoem() {
        Controller controller = new Controller();
        Document completeWebsiteText = controller.getConnection("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm");
        Elements scrappedPoem = completeWebsiteText.getElementsByClass("chapter");

        assertNotEquals(completeWebsiteText, scrappedPoem);
    }

} // End of ControllerTest