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

    private static final String EMAIL_ICON = "@ ";
    private static final String PHONE_ICON = "☎ ";
    private static final String ADDRESS_ICON = "\uD83C\uDFE0 ";
    private static final String ROLE_ICON = "👤 ";
    private static final String SALARY_ICON = "\uD83D\uDCB2 ";
    private static final String HOURSWORKED_ICON = "\uD83D\uDD51 ";
    private static final String OVERTIME_ICON = " ↷  ";
    private static final String LEAVES_ICON = "\uD83C\uDF42 ";
    private static final String DATES_ICON = "\uD83D\uDDD3 ";

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
        p.addListener((x, y, z) -> {
            updateInfoPanel(z);
            System.out.println(y);
        });
    }

    /**
     * Fills in the data of the person into the Info Panel fields.
     * @param person Person to get the update info from
     */
    public void updateInfoPanel(Person person) {
        name.setText(person.getName().fullName);
        phone.setText(PHONE_ICON + person.getPhone().value);
        address.setText(ADDRESS_ICON + person.getAddress().value);
        email.setText(EMAIL_ICON + person.getEmail().value);
        role.setText(ROLE_ICON + person.getRole().value);
        leave.setText(String.format(LEAVES_ICON + "Leaves Remaining: %s", person.getLeaves().toString()));
        leaveDates.setText(DATES_ICON + person.getLeavesTaken().toDisplayString());
        salary.setText(String.format(SALARY_ICON + "Hourly salary: $%s" + " per hour", person.getSalary().toString()));
        hoursWorked.setText(String.format(HOURSWORKED_ICON + "Hours Worked: %s", person.getHoursWorked().toString()));
        overtime.setText(String.format(OVERTIME_ICON + "Overtime Hours Worked: %s", person.getOvertime().toString()));

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

        System.out.println("InfoPanel to display:\n" + person.toString());
    }

}
