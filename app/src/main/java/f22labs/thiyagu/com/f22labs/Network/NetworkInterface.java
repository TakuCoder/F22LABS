package f22labs.thiyagu.com.f22labs.Network;



import java.util.List;

import f22labs.thiyagu.com.f22labs.Data.Food;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface NetworkInterface {

    @GET("/data.json")
    Observable<List<Food>> getFood();

}
