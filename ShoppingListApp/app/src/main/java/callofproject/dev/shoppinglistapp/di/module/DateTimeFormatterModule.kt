package callofproject.dev.shoppinglistapp.di.module

import android.content.Context
import callofproject.dev.shoppinglistapp.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.time.format.DateTimeFormatter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DateTimeFormatterModule {

    @Provides
    @Singleton
    fun provideDateTimeFormatter(@ApplicationContext context: Context): DateTimeFormatter {
        return DateTimeFormatter.ofPattern(context.getString(R.string.date_time_formatter))
    }
}