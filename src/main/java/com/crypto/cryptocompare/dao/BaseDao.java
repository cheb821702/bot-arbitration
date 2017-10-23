package com.crypto.cryptocompare.dao;

public interface BaseDao<RQ,RS> {

    RS send(RQ request);
}
