package callofproject.dev.shoppinglistapp.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.LocalDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DateTimeModule {


    @Provides
    @Singleton
    fun provideDateTime(): LocalDateTime = LocalDateTime.now()
}