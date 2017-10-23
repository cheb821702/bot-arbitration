package com.crypto;

import com.crypto.cryptocompare.dao.ApiSpecificationDao;
import com.crypto.cryptocompare.dao.ApiSpecificationDaoImpl;

public class ApplicationMain {

    public static void main(String [] args)
    {
        ApiSpecificationDao dao = new ApiSpecificationDaoImpl();
        dao.send(null);
    }
}
