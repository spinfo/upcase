package de.uni_koeln.spinfo.upcase.service;

import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

//@Service
class SSHConnection {
	
	private Session session;

	public void createConnection() throws JSchException {
		JSch jsch = new JSch();
		
		// TODO: Externalize credentials into properties file
		session = jsch.getSession("USER", "HOST");
		session.setPassword("PWD");
		
		Properties prop = new Properties();
		prop.put("StrictHostKeyChecking", "no");
		session.setConfig(prop);
		session.setTimeout(3000);
		session.connect();
	}
	
	public void closeConnection() throws JSchException {
		if(session != null && session.isConnected())
			session.disconnect();
	}


}
