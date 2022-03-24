package com.leandrodev.contasdolar.android.di

import com.leandrodev.bills.billsDiModule
import com.leandrodev.contasdolar.android.auth.signin.authModule
import com.leandrodev.contasdolar.android.auth.state.authStateModule
import com.leandrodev.contasdolar.android.wallet.WalletListViewModel
import com.leandrodev.contasdolar.android.wallet.WalletListViewModelImpl
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val androidAppDi = DI.Module("AndroidApplication") {
    import(commonDi)
    import(authModule)
    import(authStateModule)
}
val commonDi = DI.Module("Common") {
    import(billsDiModule)

    // TODO Migrate WalletList to bills module
    bindProvider<WalletListViewModel> {
        WalletListViewModelImpl(instance())
    }
}
