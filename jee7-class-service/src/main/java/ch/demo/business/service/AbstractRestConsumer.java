package ch.demo.business.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import com.google.gson.Gson;

public abstract class AbstractRestConsumer {

	private Client client;
	private String serviceRoot;

	public AbstractRestConsumer(String serviceRoot) {
		client = ClientBuilder.newClient();
		this.serviceRoot = serviceRoot;
	}

	/**
	 * invoke a rest service with the GET method and with a path param
	 * @param returnType
	 * @param path parameter
	 * @return the result of the ws invokation
	 */
	public <T extends Object> T invokeGetWithPathParam(String serviceURL, Class<T> returnType, Object param) {
		String sParams = new Gson().toJson(param);
		WebTarget target = client.target(serviceRoot + serviceURL + sParams);
		return target.request().build("GET").invoke(returnType);
	}
}
