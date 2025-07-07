package xstitchcatwalk.canvassize.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideAidaCounts(): List<Int> = listOf(14, 16, 18)
}