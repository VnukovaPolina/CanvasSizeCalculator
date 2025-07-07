package xstitchcatwalk.canvassize.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    //@Named("AidaCounts")
    fun provideAidaCounts(): List<Int> = listOf(14, 16, 18)

/*    @Provides
    @Named("LinenCounts")
    fun provideLinenCounts(): List<Int> = listOf(25, 28, 32, 40)*/
}