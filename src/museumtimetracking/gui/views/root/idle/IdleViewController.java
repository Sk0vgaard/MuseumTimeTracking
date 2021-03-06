/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package museumtimetracking.gui.views.root.idle;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import museumtimetracking.MuseumTimeTracking;
import museumtimetracking.be.GM;
import museumtimetracking.be.Volunteer;
import museumtimetracking.exception.AlertFactory;
import museumtimetracking.exception.DALException;
import museumtimetracking.exception.ExceptionDisplayer;
import museumtimetracking.gui.model.GuildManagerModel;
import museumtimetracking.gui.model.ModelFacade;
import museumtimetracking.gui.model.VolunteerModel;
import museumtimetracking.gui.views.root.ISearchableController;

/**
 * FXML Controller class
 *
 * @author gta1
 */
public class IdleViewController implements Initializable, ISearchableController {

    @FXML
    private TableColumn<GM, String> clmGMDescription;
    @FXML
    private TableColumn<GM, String> clmGMName;
    @FXML
    private TableColumn<Volunteer, String> clmVolunteerDescription;
    @FXML
    private TableColumn<Volunteer, String> clmVolunteerName;
    @FXML
    private Label lblGMAmount;
    @FXML
    private Label lblVolunteerAmount;
    @FXML
    private TableView<GM> tableIdleGM;
    @FXML
    private TableView<Volunteer> tableIdleVolunteer;
    @FXML
    private HBox vBoxGMOptions;
    @FXML
    private HBox vBoxVolunteerOptions;

    private GuildManagerModel guildManagerModel;

    private VolunteerModel volunteerModel;

    private GM selectedManager;
    private Volunteer selectedVolunteer;
    private final String TABLEVIEW_PLACEHOLDER = MuseumTimeTracking.bundle.getString("EmptyTable");

    public IdleViewController() {
        guildManagerModel = ModelFacade.getInstance().getGuildManagerModel();
        volunteerModel = ModelFacade.getInstance().getVolunteerModel();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initializeTables();

        lblGMAmount.textProperty().bind(Bindings.size((guildManagerModel.getCachedIdleGuildManagers())).asString());
        lblVolunteerAmount.textProperty().bind(Bindings.size((volunteerModel.getCachedIdleVolunteers())).asString());
    }

    @FXML
    private void handleDeleteGM() {
        Alert alert = new AlertFactory().createDeleteAlert();
        alert.showAndWait().ifPresent(type -> {
            //If the first button ("YES") is clicked
            if (type == alert.getButtonTypes().get(0)) {
                try {
                    guildManagerModel.deleteGuildManager(selectedManager);
                } catch (DALException ex) {
                    ExceptionDisplayer.display(ex);
                }
            }
        });
        if (tableIdleGM.getSelectionModel().isEmpty()) {
            setIdleGMOptionsVisibility(false);
        }
    }

    @FXML
    private void handleDeleteVolunteer() {
        Alert alert = new AlertFactory().createDeleteAlert();
        alert.showAndWait().ifPresent(type -> {
            //If the first button ("YES") is clicked
            if (type == alert.getButtonTypes().get(0)) {
                try {
                    volunteerModel.deleteVolunteer(selectedVolunteer);
                } catch (DALException ex) {
                    ExceptionDisplayer.display(ex);
                }
            }
        });
        if (tableIdleVolunteer.getSelectionModel().isEmpty()) {
            setIdleVolunteerOptionsVisibility(false);
        }
    }

    @FXML
    private void handleRestoreGM() {
        try {
            guildManagerModel.updateIdleManager(selectedManager, false);
        } catch (DALException ex) {
            ExceptionDisplayer.display(ex);
        }
        if (tableIdleGM.getSelectionModel().isEmpty()) {
            setIdleGMOptionsVisibility(false);
        }
    }

    @FXML
    private void handleRestoreVolunteer() {
        try {
            volunteerModel.updateIdleVolunteer(selectedVolunteer, false);
        } catch (DALException ex) {
            ExceptionDisplayer.display(ex);
        }
        if (tableIdleVolunteer.getSelectionModel().isEmpty()) {
            setIdleVolunteerOptionsVisibility(false);
        }
    }

    @FXML
    private void handleSelectGM() {
        selectedManager = tableIdleGM.getSelectionModel().getSelectedItem();
        setIdleGMOptionsVisibility(true);
    }

    @FXML
    private void handleSelectVounteer() {
        selectedVolunteer = tableIdleVolunteer.getSelectionModel().getSelectedItem();
        setIdleVolunteerOptionsVisibility(true);
    }

    /**
     * Set the visibility of idle options
     *
     * @param shown
     */
    public void setIdleGMOptionsVisibility(boolean shown) {
        vBoxGMOptions.setDisable(!shown);
        vBoxGMOptions.setVisible(shown);
    }

    /**
     * Set the visibility of idle options
     *
     * @param shown
     */
    public void setIdleVolunteerOptionsVisibility(boolean shown) {
        vBoxVolunteerOptions.setDisable(!shown);
        vBoxVolunteerOptions.setVisible(shown);
    }

    /**
     * Fill information in the tables
     */
    private void initializeTables() {
        tableIdleGM.setItems(guildManagerModel.getCachedIdleGuildManagers());

        tableIdleGM.setPlaceholder(new Label(TABLEVIEW_PLACEHOLDER));

        clmGMName.setCellValueFactory(gm -> gm.getValue().getFullNameProperty());
        clmGMDescription.setCellValueFactory(gm -> gm.getValue().getDescriptionProperty());

        tableIdleVolunteer.setItems(volunteerModel.getCachedIdleVolunteers());
        tableIdleVolunteer.setPlaceholder(new Label(TABLEVIEW_PLACEHOLDER));

        clmVolunteerName.setCellValueFactory(v -> v.getValue().getFullNameProperty());
        clmVolunteerDescription.setCellValueFactory(v -> v.getValue().getDescriptionProperty());

        //Check if Idle GM is empty
        if (tableIdleGM.getSelectionModel().isEmpty()) {
            setIdleGMOptionsVisibility(false);
        }
        //Check if idle volunteer is empty
        if (tableIdleVolunteer.getSelectionModel().isEmpty()) {
            setIdleVolunteerOptionsVisibility(false);
        }
    }

    /**
     * Makes the model search in the cachedLists
     *
     * @param searchText
     */
    public void handleSearch(String searchText) {
        guildManagerModel.searchIdleManagers(searchText);
        volunteerModel.searchIdleVolunteers(searchText);
    }

}
