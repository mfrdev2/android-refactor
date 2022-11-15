package com.fr.refactor.data.remote.dao;
import com.fr.refactor.data.remote.network.AddressApiService;
import com.fr.refactor.model.api.Address;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import retrofit2.Response;

@Singleton
public class AddressDao {
    private static final String[] COUNTRY_CODE ={"bd"};
    private static final String[] ACCEPT_LANGUAGE ={"en"};
    private static final String FORMAT = "json";
    private static final int ADDRESS_DETAILS = 0;
    private AddressApiService api;

    @Inject
    public AddressDao(AddressApiService api) {
        this.api = api;
    }

    /**
     *
     * @param address
     * @param acceptLanguage
     * @link https://nominatim.org/release-docs/develop/api/Search/
     * @api_route https://nominatim.openstreetmap.org/search?q=dhaka&format=json&polygon_geojson=1&addressdetails=0&countrycodes=ban,ind&accept-language=en
     * @return
     */
    public Flowable<Response<List<Address>>> getAddressesList(String address, String[] acceptLanguage) {
        if(acceptLanguage == null){
            return api.getAddressesList(address, ADDRESS_DETAILS, FORMAT, COUNTRY_CODE, AddressDao.ACCEPT_LANGUAGE);
        }else {
            return api.getAddressesList(address, ADDRESS_DETAILS, FORMAT, COUNTRY_CODE,acceptLanguage);
        }
    }
}
