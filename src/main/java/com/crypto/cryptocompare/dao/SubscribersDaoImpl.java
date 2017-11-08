package com.crypto.cryptocompare.dao;

import com.crypto.cryptocompare.model.SubsRequest;
import com.crypto.cryptocompare.model.SubsResponse;

import java.io.InputStreamReader;

public class SubscribersDaoImpl extends BaseDaoImpl<SubsRequest,SubsResponse> implements SubscribersDao {
    @Override
    protected String getBaseUrl() {
        return "https://min-api.cryptocompare.com/data/subs";
    }

    @Override
    protected String convertToHttpParameters(SubsRequest request) {
        return null;
    }

    @Override
    protected SubsResponse parseResponse(InputStreamReader reader) {
        return null;
    }
}
