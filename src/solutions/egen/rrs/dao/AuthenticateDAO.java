package solutions.egen.rrs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import solutions.egen.rrs.exception.ExceptionHandler;
import solutions.egen.rrs.model.OwnersDetails;
import solutions.egen.rrs.utils.DBUtils;

public class AuthenticateDAO {

	public boolean userAuthentication(OwnersDetails login) throws ExceptionHandler {

		Connection conn = DBUtils.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean flag;

		try {
			ps = conn.prepareStatement("SELECT * FROM OwnersDetails WHERE Email = ? AND Password =?");
			ps.setString(1, login.getEmail());
			ps.setString(2, login.getPassword());

			rs = ps.executeQuery();

			if (rs.next()) {

				login.setFirstName(rs.getString("FirstName"));
				login.setLastName(rs.getString("LastName"));
				login.setEmail(rs.getString("Email"));
				login.setContactNumber(rs.getString("ContactNumber"));
				login.setMessage("Login Sucessful");
				flag = true;

			} else {
				login.setMessage("Login Failed!!");
				flag = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(), e.getCause());
		} finally {

			DBUtils.closeResouce(ps, rs, conn);
		}
		return flag;
	}
}
