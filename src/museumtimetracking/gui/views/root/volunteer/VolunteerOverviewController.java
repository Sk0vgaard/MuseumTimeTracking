/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package museumtimetracking.gui.views.root.volunteer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import museumtimetracking.be.Volunteer;
import museumtimetracking.be.enums.EFXMLName;
import museumtimetracking.gui.model.VolunteerModel;
import museumtimetracking.gui.views.ModalFactory;

/**
 * FXML Controller class
 *
 * @author gta1
 */
public class VolunteerOverviewController implements Initializable {

    @FXML
    private Button btnEdit;

    @FXML
    private ListView<Volunteer> lstVolunteer;
    @FXML
    private RadioButton radioDA;
    @FXML
    private RadioButton radioDE;
    @FXML
    private RadioButton radioENG;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private Label txtLinkMoreInfo;
    @FXML
    private TextField txtPhone;

    private final VolunteerModel volunteerModel;

    private final ModalFactory modalFactory;

    private Stage primStage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtLinkMoreInfo.setVisible(false);

        ObservableList<Volunteer> volunteers = FXCollections.observableArrayList();
        Volunteer v = new Volunteer("test", "testesen", "test", 123);
        v.setDescription("Sheit works!");
        volunteers.add(v);
        lstVolunteer.setItems(volunteers);

        lstVolunteer.setItems(volunteerModel.getCachedVolunteers());
        setVolunteerCellFactory();

        setTextVisibility(false);
    }

    public VolunteerOverviewController() {
        modalFactory = ModalFactory.getInstance();
        volunteerModel = VolunteerModel.getInstance();
    }

    private void setTextVisibility(boolean value) {
        txtFirstName.setDisable(!value);
        txtLastName.setDisable(!value);
        txtEmail.setDisable(!value);
        txtPhone.setDisable(!value);
    }

    @FXML
    private void handleVolunteerInfo() throws IOException {
        primStage = (Stage) btnEdit.getScene().getWindow();

        Stage volunteerInfoModal = modalFactory.createNewModal(primStage, EFXMLName.VOLUNTEER_INFO);

        volunteerInfoModal.show();
    }

    /**
     * For each Volunteer in the list, show only their full name
     */
    private void setVolunteerCellFactory() {
        lstVolunteer.setCellFactory(v -> new ListCell<Volunteer>() {
            @Override
            public void updateItem(Volunteer volunteer, boolean empty) {
                super.updateItem(volunteer, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(volunteer.getFullName());
                }
            }
        });
    }

    @FXML
    private void handleDeleteVolunteer() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ADVARSEL!");
        alert.setHeaderText(" Tryk 'Ja' for at slette permanent. \n Tryk 'Nej' for at fortryde.");
        ButtonType yesButton = new ButtonType("Ja", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("Nej", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton, noButton);
        alert.showAndWait().ifPresent(type -> {

            if (type == yesButton) {

                Volunteer deleteVolunteer = lstVolunteer.getSelectionModel().getSelectedItem();
//                volunteerModel.deleteVolunteer(deleteVolunteer);
            }
        });
    }

    @FXML
    private void handleEditVolunteer() {
        if (btnEdit.getText().equalsIgnoreCase("rediger")) {
            btnEdit.setText("Gem");
            setTextVisibility(true);
        } else {
            btnEdit.setText("Rediger");
            setTextVisibility(false);
        }
    }

    @FXML
    private void handleNewVolunteer() throws IOException {
        primStage = (Stage) btnEdit.getScene().getWindow();

        Stage newVolunteerModal = modalFactory.createNewModal(primStage, EFXMLName.ADD_NEW_VOLUNTEER);

        newVolunteerModal.show();
    }

    @FXML
    private void handleSelectVolunteer() {
        Volunteer selectedVolunteer = lstVolunteer.getSelectionModel().getSelectedItem();
        if (selectedVolunteer != null) {
            txtFirstName.setText(selectedVolunteer.getFirstName());
            txtLastName.setText(selectedVolunteer.getLastName());
            txtEmail.setText(selectedVolunteer.getEmail());
            txtPhone.setText("" + selectedVolunteer.getPhone());
            txtLinkMoreInfo.setVisible(true);

            selectVolunteerLanguage(selectedVolunteer);
        }
    }

    /**
     * Select the volunteer language
     *
     * @param selectedVolunteer
     */
    private void selectVolunteerLanguage(Volunteer selectedVolunteer) {
        switch (selectedVolunteer.getLanguage()) {
            case DANISH:
                radioDA.setSelected(true);
                break;
            case ENGLISH:
                radioENG.setSelected(true);
                break;
            case GERMAN:
                radioDE.setSelected(true);
                break;
            default:
        }
    }

}