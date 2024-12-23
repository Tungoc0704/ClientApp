package Network;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Model.Post;
import Model.User;

public class PostHandler {

	// request load posts khi login thanh cong:
	public List<Post> requestLoadPost() {
		List<Post> listPost = null;
		try {
			URL url = new URL("http://localhost:8081/api/posts");
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setDoOutput(true);

			// sedn request:
			String action = "REQUEST_LOAD_POST";
			JSONObject json = new JSONObject();
			json.put("action", action);
			try (OutputStream os = httpConnection.getOutputStream()) {
				os.write(json.toJSONString().getBytes("utf-8"));
			}

			// receive list post:
			int resposeCode = httpConnection.getResponseCode();
			if (resposeCode == 200) { // Thành công
				try (BufferedReader reader = new BufferedReader(
						new InputStreamReader(httpConnection.getInputStream(), "utf-8"))) {
					StringBuilder responseBuilder = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						responseBuilder.append(line);
					}
					System.out.println("list post: " + responseBuilder);

					listPost = handlePostArr(responseBuilder.toString());

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listPost;
	}

	// Phương thức gửi video / hình ảnh đến server:
	public JSONObject sendImage_Video(String filePath, String caption) {
		JSONObject jsonObject = new JSONObject();
		try {
			URL url = new URL("http://localhost:8081/api/posts");
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setDoOutput(true);

			// Gửi userId dưới dạng JSON (hoặc chuỗi thuần)
			JSONObject json = new JSONObject();
			json.put("action", "UPLOAD_POST");
			json.put("uploadPerson", LoginAction.userID.intValue());
			json.put("caption", caption);
			json.put("image_url", filePath);
			json.put("created_at", getTimePost());
			try (OutputStream os = httpConnection.getOutputStream()) {
				os.write(json.toJSONString().getBytes("utf-8"));
			}

			// nhan tu server:
			jsonObject = receiveFromServer(httpConnection);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	// Lấy phần mở rộng của file:
	public String getFileExtension(String fileName) {
		String extension = fileName.substring(fileName.lastIndexOf("."));
		System.out.println("extension: " + extension);
		return extension;
	}

	// time post
	public String getTimePost() {
		// Lấy thời gian hiện tại
		LocalDateTime currentDateTime = LocalDateTime.now();

		// Định dạng thời gian theo kiểu "dd:mm:yyyy H:M:S"
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
		String formattedTime = currentDateTime.format(formatter);
		return formattedTime;
	}

	public JSONObject receiveFromServer(HttpURLConnection connection) throws IOException, ParseException {
		System.out.println("da nhan post...");
		JSONObject receiveJSON = null;
		int resposeCode = connection.getResponseCode();
		if (resposeCode == 200) { // Thành công
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), "utf-8"))) {
				StringBuilder responseBuilder = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					responseBuilder.append(line);
				}

				receiveJSON = (JSONObject) (new JSONParser().parse(responseBuilder.toString()));
			}
		}
		return receiveJSON;
	}

	// xu li jsonArr listPost
	public List<Post> handlePostArr(String postArrString) {
		List listPost = new ArrayList<>();
		try {
			JSONParser parser = new JSONParser();
			JSONArray arr = (JSONArray) parser.parse(postArrString);
			for (int i = 0; i < arr.size(); i++) {
				JSONObject responseJSON = (JSONObject) arr.get(i);
				JSONObject userJSON = (JSONObject) responseJSON.get("user");
				JSONObject postJSON = (JSONObject) responseJSON.get("post");

				User userUpload = new User(((Long) userJSON.get("userID")).intValue(),
						(String) userJSON.get("username"), null, null, null, (String) userJSON.get("avatar"), null,
						(String) userJSON.get("nickname"));
				Post post = new Post(userUpload, (String) postJSON.get("created_at"), (String) postJSON.get("imageUrl"),
						(String) postJSON.get("caption"));
				listPost.add(post);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listPost;
	}
}
