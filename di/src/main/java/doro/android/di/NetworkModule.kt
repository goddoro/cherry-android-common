package doro.android.di


import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import doro.android.core.util.UserHolder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
const val LOG_NAMED = "LOG_NAMED"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val baseUrl = "http://15.165.196.152:3000/"

    @Singleton
    @Provides
    fun provideOkHttpClient(
        userHolder: UserHolder,
        @ApplicationContext context: Context
    ): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(CherryInterceptor(userHolder, context))
            .build()
    }

    @Singleton
    @Provides
    @Named("LOG_OK_HTTP_CLIENT")
    fun provideLogOkHttpClient(
        userHolder: UserHolder,
        @ApplicationContext context: Context
    ): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(LogInterceptor(userHolder, context))
            .build()
    }

    @Singleton
    @Provides
    fun provideCherryRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://15.165.196.152:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    @Named(LOG_NAMED)
    fun provideLogRetrofit(@Named("LOG_OK_HTTP_CLIENT") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://13.124.181.184:3000/")
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

class LogInterceptor @Inject constructor(
    val userHolder: UserHolder,
    val context: Context,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("x-cherry-log-client", "android")
            .header("x-cherry-log-device-name", android.os.Build.MODEL)
            .header(
                "x-cherry-log-version",
                context.packageManager.getPackageInfo(context.packageName, 0).versionName
            )
            .header("x-cherry-log-env", "debug")
            .build()
        return chain.proceed(request)
    }
}