package com.example.android.hilt.di

import com.example.android.hilt.data.LoggerDataSource
import com.example.android.hilt.data.LoggerInMemoryDataSource
import com.example.android.hilt.data.LoggerLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton


// 프로젝트에서 두 구현체를 모두 제공해야 하는 경우에는?
// 우선 컨테이너를 다른 범위로 각각 설정 후 실행
// 힐트는 같은 유형에 두 가지 결합이 있어 어느 구현을 사용해야 하는지 모른다.
// Qualifier(한정자)를 사용하여 해결한다.

// 주의할 점으로, InMemoryLogger 의 컨테이너는 액티비티 범위를 지니고 있고,
// DatabaseLogger 는 애플리케이션 범위를 지니고 있음.
// 고로, InMemoryLogger 는 LogApplication 클래스에 삽입될 수 없음.

//@Qualifier
//annotation class InMemoryLogger
//
//@Qualifier
//annotation class DatabaseLogger
//
//@InstallIn(SingletonComponent::class)
//@Module
//abstract class LoggingDatabaseModule {
//
//    @DatabaseLogger
//    @Singleton
//    @Binds
//    abstract fun bindDatabaseLogger (impl: LoggerLocalDataSource): LoggerDataSource
//}
//
//@InstallIn(ActivityComponent::class)
//@Module
//abstract class LoggingInMemoryModule {
//
//    @InMemoryLogger
//    @ActivityScoped
//    @Binds
//    abstract fun bindInMemoryLogger(impl: LoggerInMemoryDataSource): LoggerDataSource
//}

@Qualifier
annotation class InMemoryLogger

@Qualifier
annotation class DatabaseLogger

@InstallIn(SingletonComponent::class)
@Module
abstract class LoggingDatabaseModule {

    @DatabaseLogger
    @Singleton
    @Binds
    abstract fun bindDatabaseLogger(impl: LoggerLocalDataSource): LoggerDataSource
}

@InstallIn(ActivityComponent::class)
@Module
abstract class LoggingInMemoryModule {

    @InMemoryLogger
    @ActivityScoped
    @Binds
    abstract fun bindInMemoryLogger(impl: LoggerInMemoryDataSource): LoggerDataSource
}