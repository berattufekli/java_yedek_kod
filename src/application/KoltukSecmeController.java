package application;

import java.io.InterruptedIOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class KoltukSecmeController {
	
	

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_pay;

    @FXML
    private Button btn_reset;

    @FXML
    private Button close_btn;

    @FXML
    private HBox harflerHBox;

    @FXML
    private VBox DonusKoltuklarVBox;

    @FXML
    private VBox GidisKoltuklarVBox;

    @FXML
    private Button minimize_btn;
    
    @FXML
    private VBox GidisSayilarVBox;

    @FXML
    private VBox DonusSayilarVBox;
    
    @FXML
    private Tab tabPage1;

    @FXML
    private Tab tabPage2;
    
    @FXML
    private Label lbl1_ucus;

    @FXML
    private Label lbl2_ucus;
    

    @FXML
    private Label lbl_koltuklar1;

    @FXML
    private Label lbl_koltuklar2;
    
    @FXML
    private AnchorPane anchor_pane;
    
    
    public static List<String> GidisSecilenKoltukList = new ArrayList<>();
    public static List<String> DonusSecilenKoltukList = new ArrayList<>();
    
    List<String> selectedGidis = MainController.selectedGidis;
	List<String> selectedDonus = MainController.selectedDonus;
	List<ToggleButton> gidisButtons = new ArrayList<>();
	List<ToggleButton> donusButtons = new ArrayList<>();
	
	private double x;
	private double y;
    
	@FXML
    void btn_pay_click(ActionEvent event) {
    	
    	if(selectedGidis.get(6).equals("Tek Yön")) {
    		if(GidisSecilenKoltukList.size() == Integer.valueOf(selectedGidis.get(5))) {
    			MessageBox();	
    		}else {
				MessageBox(AlertType.ERROR, "Hata", "Seçilen yolcu sayýsý kadar bilet seçilmesi gereklidir.",
						"Lütfen tekrar deneyiniz");
			}
    	}else if(selectedGidis.get(6).equals("Gidiþ Dönüþ")) {
    		if(GidisSecilenKoltukList.size() == Integer.valueOf(selectedGidis.get(5)) &&
    		   DonusSecilenKoltukList.size() == Integer.valueOf(selectedDonus.get(5))) {
    			MessageBox();	
    		}else {
    			if(GidisSecilenKoltukList.size() < Integer.valueOf(selectedDonus.get(5))) {
    				MessageBox(AlertType.ERROR, "Hata", "Gidiþ için seçilen yolcu sayýsý kadar bilet seçilmesi gereklidir.",
    						"Lütfen tekrar deneyiniz");
    			}else if(DonusSecilenKoltukList.size() == Integer.valueOf(selectedDonus.get(5))) {
    				MessageBox(AlertType.ERROR, "Hata", "Dönüþ için seçilen yolcu sayýsý kadar bilet seçilmesi gereklidir.",
    						"Lütfen tekrar deneyiniz");
    			}
				
			}
    	}
    }
    
    private void MessageBox(AlertType alerttype, String title, String header, String content) {
    	Alert alert = new Alert(alerttype);
    	alert.setTitle(title);
    	alert.setHeaderText(header);
    	alert.setContentText(content);
    	alert.showAndWait();
    }
    
    private void MessageBox() {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Uçuþ Bilgileri");
    	alert.setHeaderText("Ödeme sayfasýna geçiliyor.");
    	alert.setContentText("Koltuk bilgilerinizi onaylýyor musunuz?");

    	ButtonType btn1=new ButtonType("Evet");
    	ButtonType btn2=new ButtonType("Hayýr");
    	alert.getButtonTypes().setAll(btn1, btn2);
    	alert.getButtonTypes().setAll(btn1, btn2);
    	
    	Optional<ButtonType> sonuc= alert.showAndWait();
    	if(sonuc.get()==btn1) {
    		try {
    			AnchorPane pane= (AnchorPane) FXMLLoader.load(getClass().getResource("OdemeYap.fxml"));
            	anchor_pane.getChildren().setAll(pane);
			} catch (Exception e) {
				// TODO: handle exception
			}
    		
    		System.out.println("Ödeme Sayfasýna Geçiliyor");
    	}
    	else if(sonuc.get()==btn2) {
    		System.out.println("Ýptal Edildi.");
    	}	
    }

    @FXML
    void close_btnClick(ActionEvent event) {
    	Stage obj = (Stage)close_btn.getScene().getWindow();
    	obj.close();
    }

    @FXML
    void minimize_btnClick(ActionEvent event) {
    	Stage obj = (Stage) minimize_btn.getScene().getWindow();
    	obj.setIconified(true);
    }
    
    @FXML
    void btn_reset_click(ActionEvent event) {
    	GidisSecilenKoltukList.clear();
    	GidisSecilenKoltukList.clear();
    	koltuklariAktifEt(donusButtons);
    	koltuklariAktifEt(gidisButtons);
    	
    	if(selectedGidis.get(5).toString().equals("Gidiþ Dönüþ")) {
    		for(int i=0; i<gidisButtons.size();i++) {
        		gidisButtons.get(i).setSelected(false);
        		donusButtons.get(i).setSelected(false);
        	}
    	}else {
    		for(int i=0; i<gidisButtons.size();i++) {
        		gidisButtons.get(i).setSelected(false);
        	}
    	}
    	
    	
    	lbl_koltuklar1.setText("Seçilen Koltuklar: ");
    	lbl_koltuklar2.setText("Seçilen Koltuklar: ");
    }

    @FXML
    void initialize() {
        if(selectedGidis.get(6).toString().equals("Tek Yön")) {
        	tabPage2.setDisable(true);
        	lbl1_ucus.setText(selectedGidis.get(0) + "-" + selectedGidis.get(1) + " " + selectedGidis.get(2)
			+ " - " + selectedGidis.get(3));
        	for(int i=1; i<=15; i++) {
            	createLabel(GidisSayilarVBox,i);
            	createHBox(GidisKoltuklarVBox, new Insets(5,10,5,10),String.valueOf(i), gidisButtons);
            }
        	
        }else if(selectedDonus.get(6).toString().equals("Gidiþ Dönüþ")) {
        	tabPage2.setDisable(false);
        	lbl1_ucus.setText(selectedGidis.get(0) + "-" + selectedGidis.get(1) + " " + selectedGidis.get(2)
			+ " - " + selectedGidis.get(3));
        	lbl2_ucus.setText(selectedDonus.get(0) + "-" + selectedDonus.get(1) + " " + selectedDonus.get(2)
			+ " - " + selectedDonus.get(3));
        	
        	for(int i=1; i<=15; i++) {
            	createLabel(GidisSayilarVBox,i);
            	createHBox(GidisKoltuklarVBox, new Insets(5,10,5,10),String.valueOf(i), gidisButtons);
            	createLabel(DonusSayilarVBox,i);
            	createHBox(DonusKoltuklarVBox, new Insets(5,10,5,10),String.valueOf(i), donusButtons);
            }
        }
    }
    
    private void createLabel(VBox box, int index) {
    	Label lbl = new Label(String.valueOf(index));
    	lbl.setPrefSize(70, 70);
    	lbl.setAlignment(Pos.CENTER);
    	lbl.setStyle("-fx-font-family: 'Noto Sans';"
    			   + "-fx-font-size: 16px;"
    			   + "-fx-font-weight:Bold;");
    	box.getChildren().add(lbl);
    }
    
    private void createHBox(VBox vbox, Insets margin, String siraSayisi, List<ToggleButton> buttonlist) {
    	HBox box = new HBox();
    	String toggleString;
    	String[] harfler = {"A", "B", "C", "D"};
    	for(int i=0;i<4;i++) {
    		toggleString = harfler[i] + siraSayisi;
    		ToggleButton toggle = new ToggleButton();
        	toggle.setPrefSize(50, 50);
        	if(i == 2) {
        		HBox.setMargin(toggle, new Insets(5,10,5,80));
        	}else {
        		HBox.setMargin(toggle, margin);
        	}
        	toggle.getStyleClass().add("ucus-toggle");
        	
        	buttonlist.add(toggle);
        	toggle.setId(toggleString);
        	box.getChildren().add(toggle);
        	System.out.println("Button Adý: " + toggle.getId().toString());
        	toggle.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
					if(selectedGidis.get(6).equals("Tek Yön")) {
						if(toggle.isSelected()) {
							GidisSecilenKoltukList.add(toggle.getId().toString());
							lbl_koltuklar1.setText(lbl_koltuklar1.getText() + " - " + toggle.getId().toString());
						}else {
							if(GidisSecilenKoltukList.size() == Integer.valueOf(selectedGidis.get(5))) {
								koltuklariAktifEt(buttonlist);
							}
							lbl_koltuklar1.setText(lbl_koltuklar1.getText().replace(" - "+toggle.getId().toString(), "").trim());
							GidisSecilenKoltukList.remove(toggle.getId().toString());
						}
						yolcuSayisiKontrol(buttonlist, GidisSecilenKoltukList);
					}
					
					else if(selectedGidis.get(6).equals("Gidiþ Dönüþ")) {
						if(tabPage1.isSelected()) {
							if(toggle.isSelected()) {
								GidisSecilenKoltukList.add(toggle.getId().toString());
								lbl_koltuklar1.setText(lbl_koltuklar1.getText() + " - " + toggle.getId().toString());
							}else {
								if(GidisSecilenKoltukList.size() == Integer.valueOf(selectedGidis.get(5))) {
									koltuklariAktifEt(buttonlist);
								}
								lbl_koltuklar1.setText(lbl_koltuklar1.getText().replace(" - "+toggle.getId().toString(), "").trim());
								GidisSecilenKoltukList.remove(toggle.getId().toString());
							}
							yolcuSayisiKontrol(buttonlist, GidisSecilenKoltukList);
						}
						else if(tabPage2.isSelected()) {
							if(toggle.isSelected()) {
								DonusSecilenKoltukList.add(toggle.getId().toString());
								lbl_koltuklar2.setText(lbl_koltuklar2.getText() + " - " + toggle.getId().toString());
							}else {
								if(DonusSecilenKoltukList.size() == Integer.valueOf(selectedDonus.get(5))) {
									koltuklariAktifEt(buttonlist);
								}
								lbl_koltuklar2.setText(lbl_koltuklar2.getText().replace(" - "+toggle.getId().toString(), "").trim());
								DonusSecilenKoltukList.remove(toggle.getId().toString());
							}
							yolcuSayisiKontrol(buttonlist, DonusSecilenKoltukList);
						}
					}
					
				}
			});
    	}
    	vbox.getChildren().add(box);
    	
    }
    
    private void yolcuSayisiKontrol(List<ToggleButton> btn,List<String> secilenKoltukList) {
		if(secilenKoltukList.size() == Integer.valueOf(selectedGidis.get(5))) {
			for(int i=0; i<btn.size(); i++) {
				if(!btn.get(i).isSelected()) {
					btn.get(i).setDisable(true);
				}
			}
		}
	}
    
    private void koltuklariAktifEt(List<ToggleButton> btn) {
    	for(int i=0; i<btn.size(); i++) {
			if(btn.get(i).isDisable()) {
				btn.get(i).setDisable(false);
			}
		}
    }
    
    

}
