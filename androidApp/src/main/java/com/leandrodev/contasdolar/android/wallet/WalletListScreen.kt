package com.leandrodev.contasdolar.android.wallet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leandrodev.contasdolar.android.ui.theme.HomeTheme
import com.leandrodev.contasdolar.android.utils.MutableViewState
import com.leandrodev.contasdolar.android.utils.createViewAction
import com.leandrodev.data.model.Wallet
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun WalletListScreen(
    modifier: Modifier = Modifier,
    viewModel: WalletListViewModel = getViewModel()
) {
    val scope = rememberCoroutineScope()
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
        Text(text = "Authenticated.", modifier = Modifier.padding(top = 32.dp))
        Button(onClick = {
            scope.launch {
                Firebase.auth.signOut()
            }
        }) {
            Text(text = "Sign out")
        }
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