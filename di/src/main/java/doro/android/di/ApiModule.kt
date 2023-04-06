package doro.android.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import doro.android.data.service.*
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Singleton
    @Provides
    fun provideGameService(retrofit: Retrofit): GameService =
        retrofit.create(GameService::class.java)

    @Singleton
    @Provides
    fun provideMachineService(retrofit: Retrofit): MachineService =
        retrofit.create(MachineService::class.java)

    @Singleton
    @Provides
    fun provideDeviceService(retrofit: Retrofit): DeviceService =
        retrofit.create(DeviceService::class.java)

    @Singleton
    @Provides
    fun provideNotificationService(retrofit: Retrofit): NotificationService =
        retrofit.create(NotificationService::class.java)

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Singleton
    @Provides
    fun providePointTransactionService(retrofit: Retrofit): PointTransactionService =
        retrofit.create(PointTransactionService::class.java)

    @Singleton
    @Provides
    fun provideAgentService(retrofit: Retrofit): AgentService =
        retrofit.create(AgentService::class.java)

    @Singleton
    @Provides
    fun provideFcmPushService(retrofit: Retrofit): FcmPushService =
        retrofit.create(FcmPushService::class.java)

    @Singleton
    @Provides
    fun provideLogService(@Named(LOG_NAMED) retrofit: Retrofit): LogService =
        retrofit.create(LogService::class.java)

    @Singleton
    @Provides
    fun provideJumbotronService(retrofit: Retrofit): JumbotronService =
        retrofit.create(JumbotronService::class.java)

    @Singleton
    @Provides
    fun provideCommissionService(retrofit: Retrofit): CommissionService =
        retrofit.create(CommissionService::class.java)

    @Singleton
    @Provides
    fun provideUsageHistoryService(retrofit: Retrofit): UsageHistoryService =
        retrofit.create(UsageHistoryService::class.java)

    @Singleton
    @Provides
    fun providePointRequestService(retrofit: Retrofit): UserPointRequestService =
        retrofit.create(UserPointRequestService::class.java)

    @Singleton
    @Provides
    fun provideAppService(retrofit: Retrofit): AppService =
        retrofit.create(AppService::class.java)

}