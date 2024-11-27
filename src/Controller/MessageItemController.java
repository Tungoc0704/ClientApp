package Controller;

import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import Model.Partner;
import Model.DetailMessage;
import Network.ChatAction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MessageItemController {
	private MessageController parentController;
	@FXML
	private AnchorPane chatter_item;

	@FXML
	private ImageView avtPartner;

	@FXML
	private Label contentMessage;

	@FXML
	private Label namePartner;

	@FXML
	private Label timeChat;

	@SuppressWarnings("exports")
	public static VBox detail_message_vbox = new VBox();

	private Partner partner;

	public static int choosed_partnerID;

	public void setParentController(MessageController parentController) {
		this.parentController = parentController;
	}

	public void showPartners(Partner p) {
		this.partner = p;
//		avtPartner.setImage(new Image(p.getProfile_picture()));
		contentMessage.setText("The newest message content...");
		namePartner.setText(p.getUsername());
		timeChat.setText("04/11/2024");
	}

	public int getPartnerID() {
		return this.partner.getUserID();
	}

	@FXML
	public void initialize() {
		// Set the action event
		chatter_item.setOnMouseClicked(arg0 -> {
			try {
				detailConversation(arg0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public void detailConversation(MouseEvent event) throws IOException {
		try {
			// khi click chon partner de nhan tin -> gui tin hieu den server:
			choosed_partnerID = this.getPartnerID();
			System.out.println("id partner: " + choosed_partnerID);
			new ChatAction().requestPartner(choosed_partnerID);

			// show detail messages:
			load_detail_message(choosed_partnerID);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void load_detail_message(int choosed_partner_id) throws IOException {

		showComponent();

		List<DetailMessage> MessageList = new ChatAction().detailMessageList;
		for (DetailMessage dm : MessageList) {
			if (dm.getSender() == choosed_partner_id) {

				URL resource1 = getClass().getResource("/View/respondent-detail_message.fxml");
				if (resource1 == null) {
					System.err.println("FXML file not found!");
				}
				FXMLLoader fxmlLoader1 = new FXMLLoader(resource1);
				fxmlLoader1.setLocation(resource1);
				AnchorPane responsePane = fxmlLoader1.load();

				RespondentMessageController respondentMessageController = fxmlLoader1.getController();
				respondentMessageController.setRespondentMessage(dm);

				if (parentController != null) {
					javafx.scene.control.ScrollPane conversationPane = parentController.getConversationPane();
					if (conversationPane != null) {
						detail_message_vbox.setMinWidth(996.0);
						detail_message_vbox.getChildren().add(responsePane);
						conversationPane.setContent(detail_message_vbox);
					} else {
						System.err.println("conversation pane is null !");
					}
				} else {
					System.err.println("parentController is null");
				}
			}

			else {
				URL resource = getClass().getResource("/View/sender-detail-message.fxml");
				if (resource == null) {
					System.err.println("FXML file not found!");
				}
				FXMLLoader fxmlLoader = new FXMLLoader(resource);
				fxmlLoader.setLocation(resource);
				AnchorPane senderPane = fxmlLoader.load();

				SenderMessageController senderMessageController = fxmlLoader.getController();
				senderMessageController.setSenderMessage(dm);

				if (parentController != null) {
					javafx.scene.control.ScrollPane conversationPane = parentController.getConversationPane();
					if (conversationPane != null) {
						detail_message_vbox.setMinWidth(996.0);
						detail_message_vbox.getChildren().add(senderPane);
						conversationPane.setContent(detail_message_vbox);
					} else {
						System.err.println("conversation pane is null !");
					}
				} else {
					System.err.println("parentController is null");
				}

			}

		}

	}

	// khi detail_message is showed => textfield nhập tin nhắn + send_btn +
	// filechooser_btn isVisible
	// = "true":

	public void showComponent() {
		try {

			parentController.getSendBtn().setVisible(true);
			parentController.getTextArea().setVisible(true);
			parentController.getFileIcon().setVisible(true);
			parentController.getFileChooserBtn().setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
