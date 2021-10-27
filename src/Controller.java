import javafx.scene.control.Button;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class Controller {
    static Document doc;

    public void countWords() {
        ArrayList<String> checkedWords = new ArrayList<String>();
        HashMap<String, Integer> results = new HashMap<String, Integer>();
        Elements htmlPoem = null;

        try {
            doc = Jsoup.connect("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm").get();       // Creates a new connection session with the URL.
        } catch(IOException e) {
            String errorMessage = "Connection to URL failed";
            System.out.println(errorMessage);
            e.printStackTrace();
        } // End of connection try/catch

        // 1) Select all paragraphs with the class = poem to extract the poem.
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

        String noTagsPoem = htmlPoem.text();                                                                    // Removes the HTML tags from the text.
        String lwrCaseNoTagsPoem = noTagsPoem.toLowerCase();

        String noPunctuationPoem = lwrCaseNoTagsPoem.replaceAll("[\\p{Punct}\\“\\”\\—]", "");   // Removes punctuation signs.
        String[] arrWords = noPunctuationPoem.split("[\\s+]");                                            // Splits the string into words.

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
        sortedResults.forEach(System.out::println);
    } // End of countWords
} // End of controller


