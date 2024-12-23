package Common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoginService extends Remote {
	void login(String username, String password, Callback callback) throws RemoteException;
//	void updateProfile(String username, String newName, Cal)

}
