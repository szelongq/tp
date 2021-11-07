package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;
import seedu.address.model.person.ObservablePerson;
import seedu.address.model.person.Person;

/**
 * Display panel which shows the information and details of a Person.
 */
public class InfoPanel extends UiPart<Region> implements UiObserver {
    private static final String FXML = "InfoPanel.fxml";

    // Icons
    private static final String EMAIL_ICON = "@ ";
    private static final String PHONE_ICON = "☎ ";
    private static final String ADDRESS_ICON = "\uD83C\uDFE0 ";
    private static final String ROLE_ICON = "👤 ";
    private static final String SALARY_ICON = "\uD83D\uDCB2 ";
    private static final String HOURSWORKED_ICON = "\uD83D\uDD51 ";
    private static final String OVERTIME_ICON = "↷  ";
    private static final String LEAVES_ICON = "\uD83C\uDF42 ";
    private static final String DATES_ICON = "\uD83D\uDDD3 ";

    private static final String LIST_EMPTY_MSG = "No employee information to show! \n";

    @FXML
    private ScrollPane main;
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
    @FXML
    private Label textOverlay;

    /**
     * Creates an {@code InfoPanel} that displays information based on the given {@code ObservablePerson}.
     * @param p
     */
    public InfoPanel(ObservablePerson p) {
        super(FXML);

        textOverlay.setText(LIST_EMPTY_MSG);
        textOverlay.setTextAlignment(TextAlignment.CENTER);

        updateInfoPanel(p.getPerson());

        // Add InfoPanel to the ObserverList of the ObservablePerson
        p.addUiObserver(this);
    }

    /**
     * Removes the overlay on top of the InfoPanel.
     * Overlay prevents user from viewing the contents of the InfoPanel,
     * and displays {@code LIST_EMPTY_MSG} on top of it to notify user to start importing or adding employees.
     */
    private void unlock() {
        main.setVisible(true);
        main.setManaged(true);
    }

    /**
     * Applies the overlay on top of the InfoPanel.
     * Overlay prevents user from viewing the contents of the InfoPanel,
     * and displays {@code LIST_EMPTY_MSG} on top of it to notify user to start importing or adding employees.
     */
    private void lock() {
        main.setVisible(false);
        main.setManaged(false);
    }

    /**
     * Fills in the data of the person into the Info Panel fields.
     * @param person Person to get the update info from
     */
    public void updateInfoPanel(Person person) {
        if (person != null) {
            unlock();

            name.setText(person.getName().fullName);
            phone.setText(PHONE_ICON + person.getPhone().value);
            address.setText(ADDRESS_ICON + person.getAddress().value);
            email.setText(EMAIL_ICON + person.getEmail().value);
            role.setText(ROLE_ICON + person.getRole().value);
            leaveDates.setText(DATES_ICON + person.getLeavesTaken().toDisplayString());
            leaveBalance.setText(
                    String.format(LEAVES_ICON + "Leaves Remaining: %s", person.getLeaveBalance().toString()));
            salary.setText(
                    String.format(SALARY_ICON + "Hourly salary: $%s" + " per hour", person.getSalary().toString()));
            hoursWorked.setText(
                    String.format(HOURSWORKED_ICON + "Hours Worked: %s", person.getHoursWorked().toString()));
            overtime.setText(
                    String.format(OVERTIME_ICON + "Overtime Hours Worked: %s", person.getOvertime().toString()));


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
        } else {
            lock();
        }
    }

    /**
     * Updates the InfoPanel display content with person provided.
     */
    @Override
    public void update(Person person) {
        updateInfoPanel(person);
    }
}
