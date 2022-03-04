package com.leandrodev.contasdolar.android

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
internal fun AuthScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Image(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.money_bag),
                contentDescription = stringResource(id = R.string.ic_launcher_description)
            )
            Text(
                text = stringResource(id = R.string.auth_screen_title),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(top = 32.dp).align(Alignment.CenterHorizontally)
            )
        }
    }
}