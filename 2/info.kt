import retrofit2.http.GET
import retrofit2.http.Path
 
interface Info {
    @GET("form/{id}")
    fun Info(@Path("id") pokemonId: Int): Observable<Data>
}