package doro.android.di


import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import doro.android.core.BuildConfig
import doro.android.core.util.UserHolder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val baseUrl = "http://15.165.196.152:3000/"

    @Singleton
    @Provides
    fun provideOkHttpClient(userHolder: UserHolder, @ApplicationContext context: Context) =
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
        } else {
            OkHttpClient.Builder()
        }
            .addInterceptor(CherryInterceptor(userHolder, context))
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}

class CherryInterceptor @Inject constructor(
    val userHolder: UserHolder,
    val context: Context,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("Authorization", "Bearer ${userHolder.getToken().orEmpty()}")
            .header("x-cherry-device-name", android.os.Build.MODEL)
            .header("x-cherry-client", "android")
            .header(
                "x-cherry-version",
                context.packageManager.getPackageInfo(context.packageName, 0).versionName
            )
            .build()
        return chain.proceed(request)
    }
}