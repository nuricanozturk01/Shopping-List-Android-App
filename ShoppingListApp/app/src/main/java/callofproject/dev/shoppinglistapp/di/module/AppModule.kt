package callofproject.dev.shoppinglistapp.di.module

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import callofproject.dev.shoppinglistapp.data.preferences.DefaultPreferences
import callofproject.dev.shoppinglistapp.domain.preferences.IPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("shared_pref", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences): IPreferences {
        return DefaultPreferences(sharedPreferences)
    }
}