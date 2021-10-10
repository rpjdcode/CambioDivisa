package dad.rubenpablo.cambiodivisa;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;


public class CambioDivisa extends Application {

	// View
	private TextField input1, input2;
	private Button cambiarButton;
	private ComboBox<Divisa> combo, combo2;
	
	// Model
	private DoubleProperty dp2 = new SimpleDoubleProperty();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		ArrayList<Divisa> lista = new ArrayList<>();
		lista.add(Divisa.Euro);
		lista.add(Divisa.Dolar);
		lista.add(Divisa.Yen);
		lista.add(Divisa.Libra);
		
		input1 = new TextField();
		input1.setMaxWidth(55);
		input2 = new TextField();
		input2.setMaxWidth(55);
		
		
		combo = new ComboBox<Divisa>();
		combo.getItems().addAll(lista);
		combo.getSelectionModel().select(0);
		
		combo2 = new ComboBox<Divisa>();
		combo2.getItems().addAll(lista);
		combo2.getSelectionModel().select(3);
		cambiarButton = new Button("Cambiar");
		cambiarButton.setAlignment(Pos.CENTER);
		
		// Desarrollo
		CheckBox linesCheck = new CheckBox("Mostrar Grid Lines");
		linesCheck.setSelected(false);
		
		
		
		GridPane gp = new GridPane();
		gp.setHgap(5);
		gp.setVgap(5);
		gp.gridLinesVisibleProperty().bind(linesCheck.selectedProperty());
		
		gp.setAlignment(Pos.CENTER);
		gp.addRow(0, input1, combo);
		gp.addRow(1, input2, combo2);
		gp.addRow(2, cambiarButton);
		GridPane.setColumnSpan(cambiarButton, 2);
		RowConstraints[] rows = {
				new RowConstraints(),
				new RowConstraints(),
				new RowConstraints()
		};
		
		ColumnConstraints[] cols = {
				new ColumnConstraints(),
				new ColumnConstraints(),
				
		};
		cols[0].setHalignment(HPos.CENTER);
		// Restricciones de fila
		rows[2].setValignment(VPos.CENTER);
		
		gp.getRowConstraints().setAll(rows);
		gp.getColumnConstraints().setAll(cols);
		
		BorderPane bp = new BorderPane();
		bp.setCenter(gp);
		bp.setBottom(linesCheck); // desarrollo
		
		Scene escena = new Scene(bp, 640, 300);
		
		primaryStage.setTitle("Cambio de divisa");
		primaryStage.setScene(escena);
		primaryStage.show();
		
		input1.textProperty().addListener((o, ov, nv) ->{
			input1.setText(input1.getText().replace(',', '.'));
		});
		
		cambiarButton.setOnAction(e -> onChangeButtonAction(e));
		
		
		// Bindeos
		input2.textProperty().bind(dp2.asString("%.2f"));
		
	}
	
	private void onChangeButtonAction(ActionEvent e) {
		Divisa origen = combo.getValue();
		Divisa destino = combo2.getValue();
		
		Double convertido;
		try {
			convertido = Divisa.fromTo(origen, destino, Double.parseDouble(input1.getText()));
			dp2.setValue(convertido);
		} catch (NumberFormatException e1) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setHeaderText("CambioDivisa - Error");
			alerta.setContentText("Sólo es posible introducir dígitos. Revise la cantidad introducida.");
			alerta.showAndWait();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
