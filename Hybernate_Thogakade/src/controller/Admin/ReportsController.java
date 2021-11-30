package controller.Admin;

import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;

public class ReportsController {
    public AnchorPane AdminReportsContext;

    public void AnnualIncome_Report(ActionEvent actionEvent) {
    }

    public void MostMovableItem_Report(ActionEvent actionEvent) throws JRException, SQLException, ClassNotFoundException {
        JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/Reports/MostMovableItem.jrxml"));
        JasperReport compileReport = JasperCompileManager.compileReport(design);

        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }

    public void LeastMovableItem_Report(ActionEvent actionEvent) throws JRException, SQLException, ClassNotFoundException {
        JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/Reports/LeastMovableItem.jrxml"));
        JasperReport compileReport = JasperCompileManager.compileReport(design);

        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }

    public void CustomerViseIncome_Report(ActionEvent actionEvent) {

    }
}
