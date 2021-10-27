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

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AnalyzerLauncher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
            Scene scene = new Scene(root,280,204);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }

    } // End of start

    public static void main(String[] args) {
        launch(args);
    } // End of main
} // Enf of AnalyzerLauncher













