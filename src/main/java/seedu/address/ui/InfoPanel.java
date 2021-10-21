package seedu.address.ui;

import java.util.Comparator;

import javafx.beans.value.ObservableObjectValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import seedu.address.model.person.Person;

/**
 * Display panel which shows the information and details of a Person.
 */
public class InfoPanel extends UiPart<Region> {
    private static final String FXML = "InfoPanel.fxml";

    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label role;
    @FXML
    private Label leave;
    @FXML
    private Label hoursWorked;
    @FXML
    private Label overtime;
    @FXML
    private VBox calculatedSalary;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code InfoPanel} with the given {@code Person}.
     * @param p
     */
    public InfoPanel(ObservableObjectValue<Person> p) {
        super(FXML);
        updateInfoPanel(p.get());
    }

    /**
     * Fills in the data of the person into the Info Panel fields.
     * @param person Person to get the update info from
     */
    public void updateInfoPanel(Person person) {
        name.setText(person.getName().fullName);
        phone.setText("Phone Number: " + person.getPhone().value);
        address.setText("Address: " + person.getAddress().value);
        email.setText("Email: " + person.getEmail().value);
        role.setText(person.getRole().value);
        leave.setText(String.format("Leaves Remaining: %s", person.getLeaves().toString()));
        hoursWorked.setText(String.format("Hours Worked: %s", person.getHoursWorked().toString()));
        overtime.setText(String.format("Overtime Hours Worked: %s", person.getOvertime().toString()));

        String salaryDue = person.getCalculatedPay().toString(); // To be replaced by calculated salary
        if (!salaryDue.equals("0.00")) {
            Text overDueText = new Text(String.format("NOT PAID [%s]", salaryDue));
            overDueText.setFill(Color.WHITE);
            overDueText.setFont(Font.font("Open Sans Regular", 20));
            calculatedSalary.getChildren().add(overDueText);
            calculatedSalary.setStyle("-fx-background-color: #C41E3A;");
        }

        tags.getChildren().clear();
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

}
