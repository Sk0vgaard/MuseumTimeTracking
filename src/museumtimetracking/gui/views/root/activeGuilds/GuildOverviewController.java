/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package museumtimetracking.gui.views.root.activeGuilds;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import museumtimetracking.be.Guild;
import museumtimetracking.be.GuildManager;
import museumtimetracking.be.enums.EFXMLName;
import museumtimetracking.exception.AlertFactory;
import museumtimetracking.exception.DALException;
import museumtimetracking.exception.ExceptionDisplayer;
import museumtimetracking.gui.model.GuildModel;
import museumtimetracking.gui.views.ModalFactory;
import museumtimetracking.gui.views.root.activeGuilds.editGuild.EditGuildViewController;

/**
 * FXML Controller class
 *
 * @author gta1
 */
public class GuildOverviewController implements Initializable {

    @FXML
    private TableView<Guild> tableGuild;

    @FXML
    private TableColumn<Guild, String> clmGuildName;
    @FXML
    private TableColumn<Guild, String> clmGuildDescription;
    @FXML
    private JFXComboBox<GuildManager> cmbGuildManager;
    @FXML
    private JFXTextArea txtDescription;
    @FXML
    private JFXTextField txtGuildName;
    @FXML
    private VBox vBoxGuildOptions;

    private GuildModel guildModel;

    private ModalFactory modalFactory;

    private Guild selectedGuild;

    public GuildOverviewController() {
        modalFactory = ModalFactory.getInstance();
        try {
            this.guildModel = GuildModel.getInstance();
        } catch (IOException | DALException ex) {
            ExceptionDisplayer.display(ex);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setGuildOptionsVisibility(false);

        tableGuild.setItems(guildModel.getCachedGuilds());

        clmGuildName.setCellValueFactory(g -> g.getValue().getNameProperty());
        clmGuildDescription.setCellValueFactory(g -> g.getValue().getDescriptionProperty());

    }

    @FXML
    private void handleSelectGuild(MouseEvent event) {
        setGuildOptionsVisibility(true);

        selectedGuild = tableGuild.getSelectionModel().getSelectedItem();

        //Enter edit mode
        if (event.getClickCount() == 2) {

            Stage primStage = (Stage) tableGuild.getScene().getWindow();

            Stage editGuildModal = modalFactory.createNewModal(primStage, EFXMLName.EDIT_GUILD);

            EditGuildViewController controller = modalFactory.getLoader().getController();

            controller.setCurrentGuild(selectedGuild);

            editGuildModal.show();
        }
    }

    @FXML
    private void handleSelectGuildManager(ActionEvent event) {
    }

    /**
     * Set the visibility of guild options
     *
     * @param shown
     */
    public void setGuildOptionsVisibility(boolean shown) {
        vBoxGuildOptions.setDisable(!shown);
        vBoxGuildOptions.setVisible(shown);
    }

    @FXML
    private void handleAddGuild() {
        String name = txtGuildName.getText();
        String description = txtDescription.getText();
        //TODO ALH: Add GM!
        if (name != null && description != null) {
            Guild newGuild = new Guild(name, description, false);
            try {
                guildModel.addGuild(newGuild);
            } catch (DALException ex) {
                ExceptionDisplayer.display(ex);
            }
        }
        txtGuildName.setText("");
        txtDescription.setText("");
    }

    @FXML
    private void handleArchiveGuid() {
        try {
            guildModel.archiveGuild(selectedGuild);
        } catch (DALException ex) {
            ExceptionDisplayer.display(ex);
        }
    }

    @FXML
    private void handleDeleteGuid() {
        Alert alert = AlertFactory.createDeleteAlert();
        alert.showAndWait().ifPresent(type -> {
            //If the first button ("YES") is clicked
            if (type == alert.getButtonTypes().get(0)) {
                try {
                    guildModel.deleteGuild(selectedGuild);
                } catch (DALException ex) {
                    ExceptionDisplayer.display(ex);
                }
            }
        });

    }

}
