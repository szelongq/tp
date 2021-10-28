package seedu.address.ui;

import java.util.Comparator;

import javafx.beans.value.ObservableObjectValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
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
    private Label leaveBalance;
    @FXML
    private Label leaveDates;
    @FXML
    private Label salary;
    @FXML
    private Label hoursWorked;
    @FXML
    private Label overtime;
    @FXML
    private Label salaryOwed;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code InfoPanel} with the given {@code Person}.
     * @param p
     */
    public InfoPanel(ObservableObjectValue<Person> p) {
        super(FXML);
        updateInfoPanel(p.get());
        p.addListener((x, y, z) -> updateInfoPanel(z));
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
        leaveBalance.setText(String.format("Leaves Remaining: %s", person.getLeaveBalance().toString()));
        leaveDates.setText(person.getLeavesTaken().toDisplayString());
        salary.setText(String.format("Hourly salary: $%s" + " per hour", person.getSalary().toString()));
        hoursWorked.setText(String.format("Hours Worked: %s", person.getHoursWorked().toString()));
        overtime.setText(String.format("Overtime Hours Worked: %s", person.getOvertime().toString()));

        String salaryDue = person.getCalculatedPay().toString(); // To be replaced by calculated salary
        if (!salaryDue.equals("0.00")) {
            salaryOwed.setText(String.format("%s left unpaid!!", salaryDue));
        } else {
            salaryOwed.setText("");
        }

        tags.getChildren().clear();
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

}
