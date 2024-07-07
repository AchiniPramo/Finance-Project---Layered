package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.Micro_Finance_Management_System.bo.BOFactory;
import lk.ijse.Micro_Finance_Management_System.bo.custom.LoanBO;
import lk.ijse.Micro_Finance_Management_System.dto.LoanDTO;
import lk.ijse.Micro_Finance_Management_System.util.Navigation;
import lk.ijse.Micro_Finance_Management_System.view.tm.LoanTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoanFormController {
    @FXML
    private Pane bodyPane;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colInterestRate;

    @FXML
    private TableColumn<?, ?> colLoanId;

    @FXML
    private TableView<LoanTm> tblLoan;

    private static LoanFormController controller;
    public LoanFormController(){
        controller=this;
    }
    public static LoanFormController getInstance(){
        return controller;
    }

    private LoanBO loanBO = (LoanBO) BOFactory.getBOFactory().getBOType(BOFactory.BOTypes.LOAN);

    @FXML
    void btnPlaceLoanFormOnAction(ActionEvent event) throws SQLException {
        Navigation.navigateToPlaceLoanPage();
    }

    public void btnViewPenaltyOnAction(ActionEvent event) {
        bodyPane.getChildren().clear();
        try {
            bodyPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/penalty_form.fxml")));
        } catch (IOException e) {
            showErrorAlert();
        }
    }

    public void initialize() throws ClassNotFoundException {
        setCellValueFactory();
        loadAllLoan();
    }

    private void loadAllLoan() throws ClassNotFoundException {
        tblLoan.getItems().clear();

        try {
           ArrayList<LoanDTO> allLoan = loanBO.getAllLoan();

            for (LoanDTO loanDTO : allLoan) {
                tblLoan.getItems().add(new LoanTm(
                        loanDTO.getLoanId(),
                        loanDTO.getDescription(),
                        loanDTO.getAmount(),
                        loanDTO.getDuration(),
                        loanDTO.getInterestRate()

                ));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colLoanId.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colInterestRate.setCellValueFactory(new PropertyValueFactory<>("interestRate"));
    }

    private void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Navigation Error");
        alert.showAndWait();
    }
}
