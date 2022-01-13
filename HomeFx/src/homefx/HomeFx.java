/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homefx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class HomeFx extends Application {

    private Connection con = null;
    private PreparedStatement prs = null;
    private ResultSet rs = null;

    @Override
    public void start(Stage primaryStage) {

        Label lbl = new Label("SID :");
        Label lbl1 = new Label("STUDID :");
        Label lbl2 = new Label("FIRST NAME :");
        Label lbl3 = new Label("LAST NAME :");
        Label lbl4 = new Label("SECTION :");
        Label lbl5 = new Label("DEPARTMENT :");
        Label lbl6 = new Label("STUDENT REGISTRATION");
        lbl6.setTextFill(Color.INDIGO);
        lbl6.setFont(Font.font("Castellar", FontWeight.BOLD, 28));

        TextField txt = new TextField();
        TextField txt1 = new TextField();
        TextField txt2 = new TextField();
        TextField txt3 = new TextField();
        TextField txt4 = new TextField();
        TextField txt5 = new TextField();

        Button INSERT = new Button();
        Button UPDATE = new Button();
        Button VIEW = new Button();
        Button DISTINCT = new Button();
        Button SELECT = new Button();

        INSERT.setText("INSERT");
        UPDATE.setText("UPDATE");
        VIEW.setText("VIEW ALL");
        DISTINCT.setText("DISTINCT");
        SELECT.setText("SELECT");

        TableView table = new TableView<Table1>();
      
        DBConnectionC db = new DBConnectionC();

        INSERT.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String lbl = txt.getText();
                String lbl1 = txt1.getText();
                String lbl2 = txt2.getText();
                String lbl3 = txt3.getText();
                String lbl4 = txt4.getText();
                String lbl5 = txt5.getText();
                String sql = "Insert into DEPT_TBL (SID, STUDID, FIRSTNAME, LASTNAME,SECTION, DEPARTMENT ) Values (?,?,?,?,?,?)";

                if (lbl.equals("") || lbl1.equals("") || lbl2.equals("") || lbl3.equals("") || lbl4.equals("") || lbl5.equals("")) {

                    txt.setText("");
                    txt1.setText("");
                    txt2.setText("");
                    txt3.setText("");
                    txt4.setText("");
                    txt5.setText("");

                    JOptionPane.showMessageDialog(null, "Please Inesrt The Data");

                } else {

                    try {
                        con = db.connMethod();
                        prs = con.prepareStatement(sql);
                        prs.setString(1, lbl);
                        prs.setString(2, lbl1);
                        prs.setString(3, lbl2);
                        prs.setString(4, lbl3);
                        prs.setString(5, lbl4);
                        prs.setString(6, lbl5);

                        prs.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Data Inserted successfully!");

                    } catch (SQLException ex) {

                        Logger.getLogger(HomeFx.class.getName()).log(Level.SEVERE, null, ex);

                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(HomeFx.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            prs.close();
                        } catch (SQLException ex) {

                        }
                    }
                }
            }
        });

        UPDATE.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                DBConnectionC db = new DBConnectionC();
                Connection con = null;

                try {

                    con = db.connMethod();
                    String lbl2 = "Aman";
                    String txx = "Muaz";
                    String sql = "UPDATE DEPT_TBL SET FIRSTNAME='" + txx + "' WHERE FIRSTNAME='" + lbl2 + "'";

                    PreparedStatement statement = con.prepareStatement(sql);

                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "The Data Is Updated successfully!");
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        VIEW.setOnAction(new EventHandler<ActionEvent>() {
            private ObservableList<ObservableList> data;

            //private TableView tbl;
            @Override
            public void handle(ActionEvent event) {

                DBConnectionC db = new DBConnectionC();
                Connection c;
                ResultSet rs;
                data = FXCollections.observableArrayList();
                try {

                    // table.setStyle("-fx-background-color:red; -fx-font-color:yellow ");
                    c = db.connMethod();
                    String SQL = "SELECT * from DEPT_TBL";
                    rs = c.createStatement().executeQuery(SQL);
                    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                        final int j = i;
                        TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                        col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));
                        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                        col.setMinWidth(100);
                        table.getColumns().addAll(col);
                        System.out.println("Column [" + i + "] ");

                    }

                    while (rs.next()) {
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            row.add(rs.getString(i));
                        }
                        System.out.println("Row[1]added " + row);
                        data.add(row);

                    }

                    table.setItems(data);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error ");
                }

            }
        });

        DISTINCT.setOnAction(new EventHandler<ActionEvent>() {
            private ObservableList<ObservableList> data;

            //private TableView tbl;
            @Override
            public void handle(ActionEvent event) {

                DBConnectionC db = new DBConnectionC();
                Connection c;
                ResultSet rs;
                data = FXCollections.observableArrayList();
                try {

                    // table.setStyle("-fx-background-color:red; -fx-font-color:yellow ");
                    c = db.connMethod();
                    String SQL = "SELECT distinct SECTION from DEPT_TBL";
                    rs = c.createStatement().executeQuery(SQL);
                    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                        final int j = i;
                        TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                        col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));
                        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                        col.setMinWidth(100);
                        table.getColumns().addAll(col);
                        // System.out.println("Column [" + i + "] ");

                    }

                    while (rs.next()) {
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            row.add(rs.getString(i));
                        }
                        // System.out.println("Row[1]added " + row);
                        data.add(row);

                    }

                    table.setItems(data);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error ");
                }

            }
        });

        SELECT.setOnAction(new EventHandler<ActionEvent>() {
            private ObservableList<ObservableList> data;

            //private TableView tbl;
            @Override
            public void handle(ActionEvent event) {

                DBConnectionC db = new DBConnectionC();
                Connection c;
                ResultSet rs;
                data = FXCollections.observableArrayList();
                try {

                    // table.setStyle("-fx-background-color:red; -fx-font-color:yellow ");
                    c = db.connMethod();
                    String SQL = "SELECT DEPARTMENT from DEPT_TBL where FIRSTNAME = 'Elias' and SECTION = 'secD'";
                    rs = c.createStatement().executeQuery(SQL);
                    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                        final int j = i;
                        TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                        col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));
                        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                        col.setMinWidth(100);
                        table.getColumns().addAll(col);
                        // System.out.println("Column [" + i + "] ");

                    }

                    while (rs.next()) {
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            row.add(rs.getString(i));
                        }
                        // System.out.println("Row[1]added " + row);
                        data.add(row);

                    }

                    table.setItems(data);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error ");
                }

            }
        });

        GridPane root = new GridPane();
        root.addColumn(1, lbl6);
        root.addRow(1, lbl, txt);
        root.addRow(2, lbl1, txt1);
        root.addRow(3, lbl2, txt2);
        root.addRow(4, lbl3, txt3);
        root.addRow(5, lbl4, txt4);
        root.addRow(6, lbl5, txt5);
        root.addRow(7, INSERT, UPDATE);
        root.addRow(8, VIEW, DISTINCT, SELECT);
        root.addColumn(3, table);

        root.setHgap(10);
        root.setVgap(10);

        Scene scene = new Scene(root, 1450, 750);

        primaryStage.setTitle("Data");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
