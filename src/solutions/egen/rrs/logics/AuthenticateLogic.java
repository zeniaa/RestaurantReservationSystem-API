package solutions.egen.rrs.logics;

import java.util.HashMap;

import solutions.egen.rrs.model.OwnersDetails;

public class AuthenticateLogic {

	public static HashMap<String, String> eventToken = new HashMap<String, String>();

	public void maintainingSession(OwnersDetails login) {

		login.setPassword(null);
		login.setToken(login.getFirstName() + login.getLastName() + "123Token");
		eventToken.put(login.getEmail(), login.getToken());
	}

	public static boolean ValidateRequest(String email, String token) {

		boolean flag = false;
		if (token.equals(eventToken.get(email))) {
			flag = true;
		}
		return flag;
	}

}
