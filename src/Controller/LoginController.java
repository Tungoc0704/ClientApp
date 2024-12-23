package Controller;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.json.simple.JSONObject;

import Network.LoginAction;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {
	private Redirect redirect = new Redirect();

	public static String username, password;

	@FXML
	private Text forget_password;

	@FXML
	private Button login_btn;

	@FXML
	private Pane login_fields;

	@FXML
	private Text login_with_facebook_btn;

	@FXML
	private PasswordField password_field;

	@FXML
	private Button signup_btn;

	@FXML
	private TextField username_field;

	@FXML
	public void initialize() {
		// Set the action event
		login_btn.setOnMouseClicked(arg0 -> {
			submitLogin(arg0);

		});

		signup_btn.setOnMouseClicked(e -> {
			try {

				// show giao dien cua signup-view.fxml:
				redirect.redirectPage("/View/Signup-view.fxml", signup_btn);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		});
	}

	public void submitLogin(javafx.scene.input.MouseEvent event) {
		try {
			username = username_field.getText();
			password = password_field.getText();

			if (!username.equals("") & !password.equals("")) {
				Network.LoginAction loginAction = new LoginAction();
				loginAction.requestLogin(username, password);
				String notify = loginAction.notify;
				System.out.println("notify: " + notify);
				if (notify != null) {
					if (loginAction.notify.equals("APPROPRIATE ACCOUNT")) {
						// neu account correct (dang nhap thanh cong) thi Newfeed-view isVisible:
						redirect.redirectPage("/View/Newfeeds-view.fxml", login_btn);

					} else if (loginAction.notify.equals("INCORRECT PASSWORD")) {
						showOptionPane("Thông báo", "Đăng nhập không thành công", "Sai mật khẩu !");
					} else if (loginAction.notify.equals("INCORRECT USERNAME")) {
						showOptionPane("Thông báo", "Đăng nhập không thành công", "Tên đăng nhập sai !");
					}
				} else {
					System.err.println("notify is null !");
				}
			} else {
				// neu khong dien du thong tin login => tbao error yeu cau nhap day du thong
				// tin:
				showOptionPane("Thông báo", "Chú ý !", "Vui lòng nhập thông tin đầy đủ !");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void showOptionPane(String title, String headerText, String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);

		// Hiển thị Alert
		alert.showAndWait();
	}

}
