package com.odl.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class Topology defined mean to determine operation of router nodes to mount ,
 * to dismount , to get its status.
 *
 */
public class Topology {

	private URL url;
	private URLConnection rulConnection;
	private HttpURLConnection httpConn;

	public Topology() {
		url = null;
		rulConnection = null;
		httpConn = null;
	}

	public Topology(String strurl) {
		try {
			url = new URL(strurl);
			rulConnection = url.openConnection();
			httpConn = (HttpURLConnection) rulConnection;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setURL(String strurl) {
		try {
			url = new URL(strurl);
			rulConnection = url.openConnection();
			httpConn = (HttpURLConnection) rulConnection;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setAuthorization(String username, String password) {
		String userCredentials = username + ":" + password;
		String basicAuth = "Basic "
				+ new String(new Base64().encode(userCredentials.getBytes()));
		httpConn.addRequestProperty("Authorization", basicAuth);
	}

	public String odl_http_post(String method, String url_suffix,
			String contentType, String content, String accept,
			String exected_status_code) {
		String request = null;

		this.setURL(url_suffix);
		String username = "admin";
		String password = "admin";
		this.setAuthorization(username, password);
		try {
			httpConn.setRequestMethod(method);
			httpConn.addRequestProperty("Accept", accept);
			httpConn.addRequestProperty("Content-Type", contentType);
		} catch (ProtocolException e) {
			e.printStackTrace();
		}

		JSONObject json_content = null;

		try {
			json_content = new JSONObject(content);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		httpConn.addRequestProperty("Content-Length",
				Integer.toString(json_content.length()));
		httpConn.setDoOutput(true);
		try {
			httpConn.getOutputStream()
					.write(json_content.toString().getBytes());
			httpConn.getOutputStream().flush();
			httpConn.getOutputStream().close();
			httpConn.connect();
			int status = httpConn.getResponseCode();
			if (204 == status) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						httpConn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();
				request = sb.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return request;
	}

	public String odl_http_get(String method, String url_suffix,
			String contentType, String content, String accept,
			String exected_status_code) {
		String request = null;
		this.setURL(url_suffix);
		String username = "admin";
		String password = "admin";
		this.setAuthorization(username, password);
		try {
			httpConn.setRequestMethod(method.toUpperCase());
			httpConn.addRequestProperty("Accept", accept);
			httpConn.addRequestProperty("Content-Type", contentType);
			httpConn.addRequestProperty("Content-Length", "0");
			httpConn.connect();

			int status = httpConn.getResponseCode();
			if (200 == status) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						httpConn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();
				request = sb.toString();
			}
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return request;
	}

	public String odl_http_delete(String request_url, String accept,
			String expected_status_code, String contentType, String content) {
		String request = null;
		this.setURL(request_url);
		String username = "admin";
		String password = "admin";
		this.setAuthorization(username, password);

		try {
			httpConn.setRequestMethod("DELETE");
			httpConn.addRequestProperty("Accept", accept);
			httpConn.addRequestProperty("Content-Type", contentType);

			int status = httpConn.getResponseCode();
			httpConn.connect();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					httpConn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();

			request = sb.toString();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return request;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public URLConnection getRulConnection() {
		return rulConnection;
	}

	public void setRulConnection(URLConnection rulConnection) {
		this.rulConnection = rulConnection;
	}

	public HttpURLConnection getHttpConn() {
		return httpConn;
	}

	public void setHttpConn(HttpURLConnection httpConn) {
		this.httpConn = httpConn;
	}
}
