package com.example.spendingtrackerapp.my_balance.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import com.example.spendingtrackerapp.common.ActionIcon
import com.example.spendingtrackerapp.utils.BackGround
import org.koin.compose.viewmodel.koinViewModel

@Preview
@Composable
private fun MyBalanceScreenPreview() {
    MyBalanceScreen(
        onBackHome = {},
        onSave  ={}
    )
}

@Composable
fun MyBalanceScreen(
    modifier: Modifier = Modifier,
    onBackHome: () -> Unit,
    onSave:()->Unit,
    viewModel: MyBalanceViewModel= koinViewModel()
) {
    MyBalanceScreenInner(
        state = viewModel.state,
        action = viewModel::onAction,
        onSave = {
            viewModel.onAction(MyBalanceAction.OnSaveBalance)
            onSave()
        },
        onBackHome = {
            onBackHome()
        }
    )
}

@Composable
fun MyBalanceScreenInner(
    state: MyBalanceState,
    action: (MyBalanceAction)->Unit,
    onSave: () -> Unit,
    onBackHome: () -> Unit
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Update Balance",
                onBackHome = onBackHome,
            )
        }
    ) {innerPadding->
        BackGround()
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 15.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(15.dp))

            Text(
                text = "$${state.balance}",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 34.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(15.dp))

            OutlinedTextField(
                onValueChange = {
                    action(MyBalanceAction.OnBalanceChanged(it.toDoubleOrNull() ?: 0.0))
                },
                value = state.balance.toString(),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                label = {
                    Text(
                        text = "Enter Balance"
                    )
                },
                textStyle = TextStyle(
                    fontSize = 18.sp
                ),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                )
            )
            Spacer(Modifier.height(15.dp))

            OutlinedButton(onClick = {
                onSave()

            }) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 3.dp)
                )
                Text(
                    text = "Save "
                )
            }

        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    onBackHome: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        ),
        navigationIcon = {
            IconButton(onClick = {
                onBackHome()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
            }
        },
        title = {
            Text(
                text = title,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleLarge
            )
        },
        )

}