package doro.android.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import doro.android.data.dto.FcmPushRepositoryImpl
import doro.android.data.dto.UsageHistoryResponse
import doro.android.data.repository.*
import doro.android.domain.repository.*


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ) : AuthRepository

    @Binds
    abstract fun bindGameRepository(
        gameRepositoryImpl: GameRepositoryImpl
    ): GameRepository

    @Binds
    abstract fun bindMachineRepository(
        machineRepositoryImpl: MachineRepositoryImpl
    ): MachineRepository

    @Binds
    abstract fun bindDeviceRepository(
        deviceRepositoryImpl: DeviceRepositoryImpl
    ): DeviceRepository

    @Binds
    abstract fun bindNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl
    ): NotificationRepository

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun bindPointTransactionRepositoryImpl(
        pointTransactionRepositoryImpl: PointTransactionRepositoryImpl
    ): PointTransactionRepository

    @Binds
    abstract fun bindAgentRepository(
        bindAgentRepositoryImpl: AgentRepositoryImpl
    ): AgentRepository

    @Binds
    abstract fun bindFcmPushRepository(
        fcmPushRepositoryImpl: FcmPushRepositoryImpl
    ): FcmPushRepository

    @Binds
    abstract fun bindLogRepository(
        logRepositoryImpl: LogRepositoryImpl
    ): LogRepository

    @Binds
    abstract fun bindJumbotronRepository(
        jumbotronRepositoryImpl: JumbotronRepositoryImpl
    ): JumbotronRepository

    @Binds
    abstract fun bindCommissionRepository(
        commissionRepositoryImpl: CommissionRepositoryImpl
    ): CommissionRepository

    @Binds
    abstract fun bindUsageHistoryRepository(
        usageHistoryRepositoryImpl: UsageHistoryRepositoryImpl
    ): UsageHistoryRepository

    @Binds
    abstract fun bindUserPointRequestRepository(
        userPointRequestRepositoryImpl: UserPointRequestRepositoryImpl
    ): UserPointRequestRepository

    @Binds
    abstract fun bindAppRepository(
        appRepositoryImpl: AppRepositoryImpl
    ): AppRepository

    @Binds
    abstract fun bindFaqRepository(
        faqRepositoryImpl: FaqRepositoryImpl
    ): FaqRepository
}