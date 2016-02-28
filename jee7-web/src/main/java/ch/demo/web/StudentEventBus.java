/**
 * 
 */
package ch.demo.web;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @author hostettler
 *
 */
@ServerEndpoint(value = "/studentBus")
@ApplicationScoped
public class StudentEventBus {

	private static final Logger LOGGER = Logger.getLogger(StudentEventBus.class
			.getName());

	private static final Set<Session> sessions = Collections
			.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void onOpen(Session session) {
		LOGGER.log(Level.INFO, "New connection with client: {0}",
				session.getId());
		sessions.add(session);
	}

	public void alertOtherPeers(Principal principal, StudentDTO st) {
		try {
			for (Session s : sessions) {
				System.out.println("Send message to user principal : "
						+ s.getUserPrincipal() + " with session id "
						+ s.getId());

				s.getBasicRemote().sendText("User : " + principal.getName() + " has updated Student " + st.getLastName());

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@OnMessage
	public String onMessage(String message, Session session) throws IOException {
		LOGGER.log(Level.INFO, "New message from Client [{0}]: {1}",
				new Object[] { session.getId(), message });
		return "Server received [" + message + "]";
	}

	@OnClose
	public void onClose(Session session) {
		LOGGER.log(Level.INFO, "Close connection for client: {0}",
				session.getId());
		sessions.remove(session);
	}

	@OnError
	public void onError(Throwable exception, Session session) {
		LOGGER.log(Level.INFO, "Error for client: {0}", session.getId());
	}

}
