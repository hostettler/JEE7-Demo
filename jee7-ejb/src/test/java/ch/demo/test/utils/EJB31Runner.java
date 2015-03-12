/**
 * 
 */
package ch.demo.test.utils;

import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

/**
 * @author hostettler
 *
 */
public class EJB31Runner extends BlockJUnit4ClassRunner {

	public EJB31Runner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	private static EJBContainer ejbContainer;

	@SuppressWarnings("deprecation")
	@Override
	protected Statement withBefores(FrameworkMethod method, Object target,
			Statement statement) {
		try {
			ejbContainer.getContext().bind("inject", target);	
		} catch (NamingException e) {
			throw new IllegalStateException(e);
		}
		
		return super.withBefores(method, target, statement);
	}

	
	public static Context login(String username, String password) throws NamingException {
		Properties pContext = new Properties();
		pContext.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.openejb.core.LocalInitialContextFactory");
		pContext.put(Context.SECURITY_PRINCIPAL, username);
		pContext.put(Context.SECURITY_CREDENTIALS, password);
		pContext.setProperty("openejb.authentication.realmName",
				"PropertiesLogin");
		return new InitialContext(pContext);
	}

	@Override
	protected Statement withBeforeClasses(Statement statement) {
		Properties p = new Properties();

		// set the initial context factory
		p.put("java.naming.factory.initial ",
				"org.apache.openejb.client.LocalInitialContextFactory");
		// create some resources
		p.put("jdbc/StudentsDS", "new://Resource?type=DataSource");
		p.put("jdbc/StudentsDS.JdbcDriver", "org.apache.derby.jdbc.EmbeddedDriver");
		p.put("jdbc/StudentsDS.JdbcUrl", "jdbc:derby:memory:studentDB;create=true");

		// set some openejb flags
		p.put("openejb.jndiname.format", "{ejbName}/{interfaceClass}");
		p.put("openejb.descriptors.output", "true");
		p.put("openejb.validation.output.level", "verbose");
		
		p.put("JEE6DemoPersistence.eclipselink.target-database", "DERBY");
		p.put("JEE6DemoPersistence.eclipselink.logging.level", "INFO");
		p.put("JEE6DemoPersistence.eclipselink.ddl-generation", "create-tables");
		
		ejbContainer = EJBContainer.createEJBContainer(p);
		return super.withBeforeClasses(statement);
	}


}
