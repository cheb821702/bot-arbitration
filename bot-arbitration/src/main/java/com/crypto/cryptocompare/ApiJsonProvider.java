package com.crypto.cryptocompare;

import static com.crypto.cryptocompare.ClientProperties.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class ApiJsonProvider {

	public InputStreamReader getSpecificationReader() {
		return getSpecificationReader(API_SPECIFICATION_URL);
	}

	public InputStreamReader getSpecificationReader(String apiUrl) {
		HttpsURLConnection con = getConnection(apiUrl);
		try {
			return new InputStreamReader(con.getInputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private HttpsURLConnection getConnection(String https_url) {
		try {
			URL url = new URL(https_url);
			return (HttpsURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
