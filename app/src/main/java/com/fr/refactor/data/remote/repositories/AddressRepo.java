package com.fr.refactor.data.remote.repositories;
import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import com.fr.refactor.R;
import com.fr.refactor.data.preferances.prefs.PreferencesHelper;
import com.fr.refactor.data.remote.dao.AddressDao;
import com.fr.refactor.model.api.Address;
import com.fr.refactor.model.api.Geojson;
import com.fr.refactor.model.api.LatLng;
import com.fr.refactor.utils.AppConstants;
import com.fr.refactor.utils.network.NetworkErrorHandler;
import com.fr.refactor.utils.network.Resource;
import com.google.gson.JsonArray;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import retrofit2.Response;
import timber.log.Timber;

@Singleton
public class AddressRepo {
    private static final String TAG = AddressRepo.class.getSimpleName();

    private AddressDao addressDao;

    private Context context;

    private PreferencesHelper preferencesHelper;

     @Inject
    public AddressRepo(final Context context,final AddressDao addressDao,PreferencesHelper preferencesHelper) {
       this.context = context;
       this.addressDao = addressDao;
       this.preferencesHelper = preferencesHelper;
    }

    public void sendAddressListRequest(final MediatorLiveData<Resource<List<Address>>> addressListData, String address, String language) {

        final LiveData<Resource<List<Address>>> source = LiveDataReactiveStreams
                .fromPublisher(addressDao.getAddressesList(address, (language != null) ? new String[]{language} : null)
                        .doOnSubscribe(subscription -> addressListData.setValue(Resource.loading(null)))
                        .onErrorReturn(throwable -> ((Response<List<Address>>) NetworkErrorHandler
                                .handleThrowable(throwable, context, context.getResources().getString(R.string.error_occure))))
                        .map(this::parseAddressListResponse));

        addressListData.addSource(source, authResource -> {
            addressListData.setValue(authResource);
            addressListData.removeSource(source);
        });
    }

    private Resource<List<Address>> parseAddressListResponse(Response<List<Address>> response) {
        try {
            int statusCode = response.code();
            if (statusCode == 200 && response.body() != null) {
                List<Address> body = response.body();
                List<LatLng> latLngList = readyLatLong(body);
                Timber.tag(TAG).i("LatLng List %s", latLngList);
                return Resource.success(response.body());
            }
            if (statusCode == 400) {
                return Resource.error(context.getResources().getString(R.string.error_occure), null);
            }
            if (statusCode == AppConstants.ERROR_CODE) {
                return Resource.error(response.errorBody().string(), null);
            }
            NetworkErrorHandler.logUnsuccessfulResponse(response);
        } catch (Throwable th) {
            Timber.e(th);
        }
        return Resource.error(context.getResources().getString(R.string.error_occure), null);
    }


    private List<LatLng> readyLatLong(List<Address> body) {
        if (body == null || body.isEmpty()) {
            return new ArrayList<>();
        }

        Address address = body.get(0);
        Geojson geojson = address.getGeojson();
        if (geojson == null) {
            return new ArrayList<>();
        }

        Timber.tag(TAG).i("GeojsonType %s", geojson.getType());

        ArrayList<ArrayList<JsonArray>> coordinates = geojson.getCoordinates();
        if (coordinates == null || coordinates.isEmpty()) {
            return new ArrayList<>();
        }
        List<LatLng> latLngList = new ArrayList<>();

        for (ArrayList<JsonArray> latLng : coordinates) {

            for (JsonArray latLngg : latLng) {
                // System.out.println(latLngg.get(0));
                Double aLng = latLngg.get(0).getAsDouble();
                Double aLat = latLngg.get(1).getAsDouble();
                latLngList.add(new LatLng(aLat, aLng));
            }

        }

        return latLngList;
    }

}
