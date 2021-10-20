package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import seedu.address.model.person.Address;
import seedu.address.model.person.CalculatedPay;
import seedu.address.model.person.Email;
import seedu.address.model.person.HourlySalary;
import seedu.address.model.person.HoursWorked;
import seedu.address.model.person.Leave;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

import java.util.Comparator;
import java.util.HashSet;

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

    public InfoPanel() {
        super(FXML);

        HashSet<Tag> testTags = new HashSet<>();
        testTags.add(new Tag("Cringe"));
        Person person = new Person(new Name("Jeff Bezos"), new Phone("62353535"), new Email("awlk@gmail.com"), new Address("Dover Road, School of Computing"),
                new Role("CEO of Amazon"), new Leave("69"), new HourlySalary("20"), new HoursWorked("200"),
                new CalculatedPay("200"), testTags);

        updateInfoPanel(person);
    }

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

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

}
