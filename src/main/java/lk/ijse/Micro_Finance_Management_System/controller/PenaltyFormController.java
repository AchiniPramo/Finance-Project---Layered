package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.Micro_Finance_Management_System.bo.BOFactory;
import lk.ijse.Micro_Finance_Management_System.bo.custom.PenaltyBO;
import lk.ijse.Micro_Finance_Management_System.dto.PenaltyDTO;
import lk.ijse.Micro_Finance_Management_System.view.tm.PenaltyTm;

import java.sql.SQLException;
import java.util.ArrayList;

public class PenaltyFormController {

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDateApplied;

    @FXML
    private TableColumn<?, ?> colLoanId;

    @FXML
    private TableColumn<?, ?> colPenaltyId;

    @FXML
    private TableColumn<?, ?> colReason;

    @FXML
    private TableView<PenaltyTm> tblPenalty;
    private PenaltyBO penaltyBO = (PenaltyBO) BOFactory.getBOFactory().getBOType(BOFactory.BOTypes.PENALTY);

    public void initialize() throws ClassNotFoundException {
        loadAllPenalty();
        setCellValueFactory();
    }

    private void loadAllPenalty() throws ClassNotFoundException {
        tblPenalty.getItems().clear();

        try {
            ArrayList<PenaltyDTO> allPenalty = penaltyBO.getAllPenalty();

            for (PenaltyDTO penaltyDTO : allPenalty) {
                tblPenalty.getItems().add(new PenaltyTm(
                        penaltyDTO.getPenaltyId(),
                        penaltyDTO.getReason(),
                        penaltyDTO.getLoanId(),
                        penaltyDTO.getAmount(),
                        penaltyDTO.getDateApplied()
                ));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colPenaltyId.setCellValueFactory(new PropertyValueFactory<>("penaltyId"));
        colReason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        colLoanId.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDateApplied.setCellValueFactory(new PropertyValueFactory<>("dateApplied"));
    }
}
