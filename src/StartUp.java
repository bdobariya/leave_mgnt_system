
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.leave.process.Monitor;

public class StartUp extends javax.servlet.http.HttpServlet
		implements ServletContextListener, HttpSessionAttributeListener, HttpSessionListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		Monitor thread = new Monitor("Continuously Monitor Leaves");
		thread.start();
	}
	
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {

	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent arg0) {

	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent arg0) {

	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent arg0) {

	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

}
