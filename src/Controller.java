import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class Controller {
    String url = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";

    // Creates a new connection session with the URL.
    public Document getConnection(String website) {
        Document doc = null;
        try {
            doc = Jsoup.connect(website).get();
        } catch(IOException e) {
            String errorMessage = "Connection to URL failed";
            System.out.println(errorMessage);
            e.printStackTrace();
        } // End of connection try/catch
        return doc;
    } // End of getConnection method

    public Elements scrapPoem(Document doc) {
        Elements htmlPoem = null;
        try {
            htmlPoem = doc.getElementsByClass("chapter");                                          // Scraps for all elements with classname "poem". Includes HTML tags.
            if(htmlPoem.isEmpty()) {
                throw new NullPointerException();
            }
        } catch(NullPointerException e) {
            String error = "The div's class name that contains the poem in the website might have changed. Elements htmlPoem variable is empty";
            System.out.println(error);
            System.exit(-1);
        } // End of scrapper try/catch
        return htmlPoem;
    } // End of scrapPoem method

    public String removeTagsAndPunctuation(Elements scrappedPoem) {
        String noTagsPoem = scrappedPoem.text();                                                                // Removes the HTML tags from the text.
        String lwrCaseNoTagsPoem = noTagsPoem.toLowerCase();
        String noPunctuationPoem = lwrCaseNoTagsPoem.replaceAll("[\\p{Punct}\\“\\”\\—]", "");   // Removes punctuation signs.
        return noPunctuationPoem;
    } // End of removeTagsAndPunctuation method

    public List countWords(String text) {
        ArrayList<String> checkedWords = new ArrayList<String>();
        HashMap<String, Integer> results = new HashMap<String, Integer>();

        String[] arrWords = text.split("[\\s+]");

        //  2) Check each word against all the others to know how many times it appears.
        for(int i = 0; i < arrWords.length; i++) {                                                             // i = first word to be compared
            int frequency = 1;

            for(int j = i + 1; j < arrWords.length; j++) {                                                     // j = second word to be compared
                if(i > 0) {
                    if(checkedWords.contains(arrWords[i])) {
                        break;
                    } else {
                        if(arrWords[i].equals(arrWords[j])) {
                            frequency++;
                        }
                    }
                } else {
                    if(arrWords[i].equals(arrWords[j])) {
                        frequency++;
                    }
                }
            } // End of j for-loop
            while(!checkedWords.contains(arrWords[i])) {
                checkedWords.add(arrWords[i]);
                results.put(arrWords[i], frequency);
            }
        } // End if i for-loop

        // 3) Sort all words by the most frequent.
        List<Map.Entry<String, Integer>> sortedResults = new ArrayList<>(results.entrySet());
        sortedResults.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
//        sortedResults.forEach(System.out::println);
        return sortedResults;
    } // End of countWords method

    @FXML
    private void sendInfo() throws IOException {
        // Executes all the logic of scrapping and counting the words
        Document doc = getConnection(url);
        Elements poem = scrapPoem(doc);
        String cleanText = removeTagsAndPunctuation(poem);
        List finalResults = countWords(cleanText);

        // Loads second window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resultsWindow.fxml"));
        Parent root = loader.load();

        // Get controller instance
        ResultsController secondController = loader.getController();

        // Pass data to controller
        secondController.setResultsArea(finalResults);

        // Show second scene in new stage
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Results");
        stage.show();
    }







} // End of controller


