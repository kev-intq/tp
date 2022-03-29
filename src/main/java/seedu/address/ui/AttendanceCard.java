package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.attendance.Attendance;

/**
 * An UI component that displays information of a {@code Attendance}.
 */
public class AttendanceCard extends UiPart<Region> {

    private static final String FXML = "AttendanceListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Attendance attendance;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label studentId;
    @FXML
    private Label status;
    @FXML
    private Label comment;

    /**
     * Creates a {@code AttendanceCode} with the given {@code Attendance} and index to display.
     */
    public AttendanceCard(Attendance attendance, int displayedIndex, int week) {
        super(FXML);
        this.attendance = attendance;
        id.setText(displayedIndex + ". ");
        studentId.setText(attendance.getStudentId().toString());
        status.setText(attendance.getAttendanceStatusByWeek(week));
        comment.setText(attendance.getComment().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TutorialCard)) {
            return false;
        }

        // state check
        AttendanceCard card = (AttendanceCard) other;
        return id.getText().equals(card.id.getText())
                && attendance.equals(card.attendance);
    }
}
