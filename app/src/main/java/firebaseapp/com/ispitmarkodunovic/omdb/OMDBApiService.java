package firebaseapp.com.ispitmarkodunovic.omdb;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OMDBApiService {
    /**
     * Prvo je potrebno da definisemo retrofit instancu preko koje ce komunikacija ici
     * */
    public static Retrofit getRetrofitInstance(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Contact.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    /**
     * Definisemo konkretnu instancu servisa na intnerntu sa kojim
     * vrsimo komunikaciju
     * */
    public static OMDBApiEndpoint apiInterface(){
        OMDBApiEndpoint apiService = getRetrofitInstance().create(OMDBApiEndpoint.class);
        return apiService;
    }
}
