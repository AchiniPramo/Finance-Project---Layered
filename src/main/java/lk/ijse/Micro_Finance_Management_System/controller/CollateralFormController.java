package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.Micro_Finance_Management_System.bo.BOFactory;
import lk.ijse.Micro_Finance_Management_System.bo.custom.CollateralBO;
import lk.ijse.Micro_Finance_Management_System.dto.CollateralDTO;
import lk.ijse.Micro_Finance_Management_System.util.Navigation;
import lk.ijse.Micro_Finance_Management_System.view.tm.CollateralTm;

import java.sql.SQLException;
import java.util.ArrayList;

public class CollateralFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCollateralId;

    @FXML
    private TableColumn<?, ?> colMobileNo;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<CollateralTm> tblCollateral;

    private static CollateralFormController controller;
    public CollateralFormController(){
        controller = this;
    }
    public static CollateralFormController getInstance(){
        return controller;
    }

    private CollateralBO collateralBO = (CollateralBO) BOFactory.getBOFactory().getBOType(BOFactory.BOTypes.COLLATERAL);

    @FXML
    void btnCollateralManageOnAction(ActionEvent event) {
        Navigation.navigateToManageCollateral();
    }

    public void initialize() throws ClassNotFoundException {
        setCellValueFactory();
        loadAllCollateral();
    }

    private void loadAllCollateral() throws ClassNotFoundException {
        tblCollateral.getItems().clear();

        try {
            ArrayList<CollateralDTO> allCollateral = collateralBO.getAllCollateral();

            for (CollateralDTO collateralDTO : allCollateral) {
                tblCollateral.getItems().add(new CollateralTm(
                        collateralDTO.getCollateralId(),
                        collateralDTO.getName(),
                        collateralDTO.getAddress(),
                        collateralDTO.getPhoneNumber()

                ));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colCollateralId.setCellValueFactory(new PropertyValueFactory<>("collateralId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMobileNo.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }
}
