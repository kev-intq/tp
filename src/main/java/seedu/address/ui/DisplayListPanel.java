package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.DisplayType;
import seedu.address.model.Displayable;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.assessment.StudentResult;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;

public class DisplayListPanel extends UiPart<Region> {
    private static final String FXML = "DisplayListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DisplayListPanel.class);
    private int attendanceWeek;

    @FXML
    private ListView<Displayable> displayListView;

    /**
     * Creates a {@code DisplayListPanel} with the given {@code ObservableList}.
     */
    public DisplayListPanel(ObservableList<? extends Displayable> displayList) {
        super(FXML);
        ObservableList<Displayable> inputList = FXCollections.observableArrayList();
        inputList.setAll(displayList);
        displayListView.setItems(inputList);
        displayListView.setCellFactory(listView -> new DisplayListPanel.DisplayListViewCell());
    }

    /**
     * Sets the week of attendance in query for display.
     */
    public void setAttendanceWeek(int week) {
        attendanceWeek = week;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class DisplayListViewCell extends ListCell<Displayable> {
        @Override
        protected void updateItem(Displayable item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else if (item.getDisplayType() == DisplayType.PERSON) {
                setGraphic(new PersonCard((Person) item, getIndex() + 1).getRoot());
            } else if (item.getDisplayType() == DisplayType.CLASS) {
                setGraphic(new TutorialCard((Tutorial) item, getIndex() + 1).getRoot());
            } else if (item.getDisplayType() == DisplayType.ASSESSMENT) {
                setGraphic(new AssessmentCard((Assessment) item, getIndex() + 1).getRoot());
            } else if (item.getDisplayType() == DisplayType.SCORE) {
                setGraphic(new StudentResultCard((StudentResult) item, getIndex() + 1).getRoot());
            } else if (item.getDisplayType() == DisplayType.ATTENDANCE && attendanceWeek == 0) {
                setGraphic(new AttendanceByStudentCard((Attendance) item, getIndex() + 1).getRoot());
            } else if (item.getDisplayType() == DisplayType.ATTENDANCE) {
                setGraphic(new AttendanceCard((Attendance) item, getIndex() + 1, attendanceWeek).getRoot());
            } else {
                setGraphic(new PersonCard((Person) item, getIndex() + 1).getRoot());
            }
        }
    }

}
