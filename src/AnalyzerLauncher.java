/*
   Reads a text found in a website and outputs statistics about that text.

   Input - Text in website - https://www.gutenberg.org/files/1065/1065-h/1065-h.htm
           Ignore: All HTML tags
                   All text before poem's title
                   All text after end of poem

   Output - 1) The word frequencies of all words in the file.
            2) Sorted by the most frequently used word.
            3) Output = set of pairs -> word + frequency in file.
*/

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class AnalyzerLauncher {
    public static void main(String[] args) {
        try {
            ArrayList<String> checkedWords = new ArrayList<String>();
            HashMap<String, Integer> results = new HashMap<String, Integer>();

            // 1) Select all paragraphs with the class = poem to extract the poem.
            Document doc = Jsoup.connect("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm").get();       // Creates a new connection session with the URL.
            Elements htmlPoem = doc.getElementsByClass("poem");                                            // Scraps for all elements with classname "poem". Includes HTML tags.
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

            } catch(IOException e) {
            String errorMessage = "No poem found";
            e.printStackTrace();
            System.out.println(errorMessage);
        } // End of try/catch
    } // End of main
} // Enf of AnalyzerLauncher