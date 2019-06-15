package com.example.kacperopyrchal.petinder

object DependencyInjector {

    private var applicationComponent: ApplicationComponent? = null

    fun initialize() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule())
                .build()
    }

    fun applicationComponent(): ApplicationComponent? {
        return applicationComponent
    }
}