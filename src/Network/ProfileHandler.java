package Network;

import java.rmi.RemoteException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Common.ProfileService;
import javafx.scene.control.Label;

public class ProfileHandler {

	// xem profile:
	public void receiveProfile(ProfileService profileService, Label nicknameProfile, Label usernameProfile,
			Label numPosts, Label numFollower, Label numFollowing) throws RemoteException, ParseException {
		String profileJSONString = profileService.detailProfile(LoginAction.userID.intValue());
		System.out.println("personal: " + LoginAction.userID.intValue());

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(profileJSONString);
		System.out.println(" wall: " + jsonObject);
		int numfollowing = ((Long) jsonObject.get("numFollowing")).intValue();
		int numfollower = ((Long) jsonObject.get("numFollower")).intValue();
		int numPost = ((Long) jsonObject.get("numPost")).intValue();
		JSONArray posts = (JSONArray) jsonObject.get("posts");
		JSONObject profileUser = (JSONObject) jsonObject.get("profileUser");

		// gans gia tri cho cac component: so luong post, so nguoi follow...
		assignValueIntoComponent(nicknameProfile, usernameProfile, numPosts, numFollower, numFollowing, numfollowing,
				numfollower, numPost, profileUser);

		// load image posts:

	}

	// gans gia tri cho cac component: so luong post, so nguoi follow...
	public void assignValueIntoComponent(Label nicknameProfile, Label usernameProfile, Label numPost, Label numFollower,
			Label numFollowing, int following, int follower, int countPost, JSONObject profileUser) {
		nicknameProfile.setText((String) profileUser.get("name"));
		usernameProfile.setText((String) profileUser.get("username"));
		numPost.setText("" + countPost);
		numFollower.setText("" + follower);
		numFollowing.setText("" + following);
	}

	// chỉnh sửa profile:

}
