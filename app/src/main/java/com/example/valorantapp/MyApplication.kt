package com.example.valorantapp

import android.app.Application
import com.example.valorantapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

// essa classe representa o app inteiro e roda antes de qualquer tela
// usei ela só pra ligar o Koin (injeção de dependência)
// importante: ela precisa estar no AndroidManifest em android:name=".MyApplication"
class MyApplication : Application() {

    // roda uma vez só, quando o app abre
    override fun onCreate() {
        super.onCreate()  // deixa o Android fazer a parte dele primeiro

        // liga o Koin, ele que cria os objetos e entrega pra quem precisa
        // (tipo o repository pro ViewModel)
        startKoin {
            // dá o contexto do Android pro Koin
            androidContext(this@MyApplication)

            // aqui tá a lista de coisas que o Koin sabe criar (fica na pasta di)
            modules(appModule)
        }
    }
}