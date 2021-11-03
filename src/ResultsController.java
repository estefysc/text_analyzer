import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.List;

public class ResultsController {
    @FXML
    private TextArea resultsArea;

    public void setResultsArea(List result) {
        resultsArea.setEditable(false);
        resultsArea.wrapTextProperty();
        resultsArea.appendText(result.toString());
    }

}
