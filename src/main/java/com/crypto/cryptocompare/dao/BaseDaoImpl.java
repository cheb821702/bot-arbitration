package com.crypto.cryptocompare.dao;

import com.crypto.cryptocompare.exception.CryptoCompareDaoException;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class BaseDaoImpl<RQ,RS> {

    protected abstract String getBaseUrl();

    protected abstract String convertToHttpParameters(RQ request);

    protected abstract RS parseResponse(InputStreamReader reader);

    public RS send(RQ request) {
        return parseResponse(getResponseReader(request));
    }

    private InputStreamReader getResponseReader(RQ request)  {
        HttpsURLConnection con = getConnection(assembleUrl(request));
        try {
            return new InputStreamReader(con.getInputStream());
        } catch (IOException e) {
            throw new CryptoCompareDaoException(e);
        }
    }

    private HttpsURLConnection getConnection(String https_url) {
        try {
            URL url = new URL(https_url);
            return (HttpsURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new CryptoCompareDaoException(e);
        } catch (IOException e) {
            throw new CryptoCompareDaoException(e);
        }
    }

    private String assembleUrl(RQ request) {
        String params = convertToHttpParameters(request);
        return (params == null || params.isEmpty()) ? getBaseUrl() : getBaseUrl() + "?" + params;
    }

}
