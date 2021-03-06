package com.leandrodev.bills.wallet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leandrodev.auth.signout.SignOutView
import com.leandrodev.bills.wallet.list.WalletListState
import com.leandrodev.bills.wallet.list.WalletListViewModel
import com.leandrodev.bills.wallet.model.Wallet
import com.leandrodev.shared.MutableViewState
import com.leandrodev.shared.createViewAction
import com.leandrodev.shared.getViewModel
import com.leandrodev.ui.theme.HomeTheme

@Composable
fun WalletListScreen(
    modifier: Modifier = Modifier,
    viewModel: WalletListViewModel = getViewModel(),
) {
    val state: WalletListState by viewModel.state.collectAsState(initial = WalletListState.Loading)

    Column(modifier) {
        (state as? WalletListState.Loading)?.let {
            Text(text = "Authenticated. Loading bank list...")
        }
        (state as? WalletListState.Content)?.let { safeState ->
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(all = 8.dp),
            ) {
                items(
                    items = safeState.wallets,
                    key = { it.id },
                ) { wallet ->
                    WalletCard(
                        modifier = Modifier.fillMaxWidth(),
                        wallet = wallet,
                    )
                }
            }
        }
        SignOutView(
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewWalletListScreen() {
    val viewModel = object : WalletListViewModel() {
        override val state: MutableViewState<WalletListState> = createViewAction()

        init {
            state.tryEmit(
                WalletListState.Content(
                    listOf(
                        Wallet("0", "Carteira 1", 1099, true, ""),
                        Wallet("1", "Carteira 2", -5000, true, ""),
                    )
                )
            )
        }
    }
    HomeTheme {
        WalletListScreen(
            viewModel = viewModel,
        )
    }
}
