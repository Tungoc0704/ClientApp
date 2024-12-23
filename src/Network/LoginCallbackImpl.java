package Network;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Common.Callback;

public class LoginCallbackImpl extends UnicastRemoteObject implements Callback {

	protected LoginCallbackImpl() throws RemoteException {
		super();
	}

	@Override
	public void notifyClient(String notifyJSONString) throws RemoteException {
		System.out.println("notify login: " + notifyJSONString); // retrun là chuỗi json
		JSONParser parser = new JSONParser();
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(notifyJSONString);
			String notify = (String) jsonObject.get("notify");
			if (notify.equals("APPROPRIATE ACCOUNT")) {
				LoginAction.userID = (Long) jsonObject.get("user_id");
				LoginAction.notify = notify;
			}
			else {
				LoginAction.notify = notify;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}	
	}

}
