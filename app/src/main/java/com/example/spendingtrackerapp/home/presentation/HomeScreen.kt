package com.example.spendingtrackerapp.home.presentation

import android.os.Build
import com.example.spendingtrackerapp.R
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastCbrt
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.spendingtrackerapp.core.domain.model_ui.SpendingUi
import com.example.spendingtrackerapp.home.di.homeModule
import com.example.spendingtrackerapp.utils.BackGround
import org.koin.compose.viewmodel.koinViewModel
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onBalanceClick: () -> Unit,
    onUpdateSpending: (Int) -> Unit,
    onAddSpending: () -> Unit
) {
    LaunchedEffect(true) {
        viewModel.onAction(HomeAction.LoadingHomeViewBalance)
    }
    HomeScreenInner(
        state = viewModel.state,
        onBalanceClick = onBalanceClick,
        onAction = viewModel::onAction,
        onUpdateSpending = onUpdateSpending,
        onDeleteSpending = {
            viewModel.onAction(HomeAction.OnDeleteSpending(it))
        },
        onAddSpending = onAddSpending,
    )

}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenInner(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    onBalanceClick: () -> Unit,
    onUpdateSpending: (Int) -> Unit,
    onDeleteSpending: (Int) -> Unit,
    onAddSpending: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    )
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onAddSpending()
            }, containerColor = Color.Green, modifier = Modifier.clip(RoundedCornerShape(50.dp))) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        },
        topBar = {
            Column {
                HomeTopBar(
                    modifier = Modifier.fillMaxWidth(),
                    onBalanceClick = {
                        onBalanceClick()
                    },
                    balance = state.balance,
                    scrollBehavior = scrollBehavior
                )
                Spacer(Modifier.height(8.dp))
                DatePickerDropDownMenu(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 10.dp),
                    onItemClick = { index ->
                        onAction(HomeAction.OnDateChanged(index))
                    },
                    homeState = state
                )
            }
        }
    ) {
        BackGround()
        Column(
            modifier = Modifier.padding(it)
        ) {
            SpendingList(
                onDeleteSpending = onDeleteSpending,
                onUpdateSpending = onUpdateSpending,
                state = state
            )
        }
    }

}

@Composable
fun SpendingList(
    modifier: Modifier = Modifier,
    onDeleteSpending: (Int) -> Unit,
    onUpdateSpending: (Int) -> Unit,
    state: HomeState
) {
    LazyColumn(
        contentPadding = PaddingValues(
             bottom = 80.dp
        )
    ) {
        itemsIndexed(state.spending) { index, item ->
            SpendingItem(
                spending = item,
                onDeleteSpending = { onDeleteSpending(item.spendingId ?: 0) },
                onUpdateSpending = { onUpdateSpending(item.spendingId ?:-1) }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SpendingItem(
    modifier: Modifier = Modifier,
    spending: SpendingUi,
    onDeleteSpending: () -> Unit,
    onUpdateSpending: () -> Unit
) {
    var isDeleteShowing by rememberSaveable { mutableStateOf(false) }

    Box {
        ElevatedCard(
            elevation = CardDefaults.elevatedCardElevation(4.dp),
            shape = RoundedCornerShape(22.dp),
            modifier = Modifier
                .padding(15.dp)
                .height(160.dp)
                .combinedClickable(
                    onClick = {
                        onUpdateSpending()
                    },
                    onLongClick = {
                        isDeleteShowing = !isDeleteShowing
                    }
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color(spending.color))
                    .padding(horizontal = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        spending.name,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 23.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(Modifier.height(2.dp))
                    SpendingInfo(
                        name = "Price",
                        value = "$${spending.price}"
                    )
                    SpendingInfo(
                        name = "Kilograms",
                        value = "${spending.kilograms}"
                    )
                    SpendingInfo(
                        name = "Quantity",
                        value = "${spending.quantity}"
                    )
                }

                AsyncImage(
                    model = spending.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(10.dp)).padding(10.dp),
                    placeholder = painterResource(id = R.drawable.bg)
                )

            }
        }
    DropdownMenu(
        expanded = isDeleteShowing,
        onDismissRequest = {
            isDeleteShowing = false
        },
        modifier = Modifier,
        offset = DpOffset(30.dp,0.dp)
    ) {
        DropdownMenuItem(
            text = {
                Text("Delete Spending ?")
            },
            onClick = {
                onDeleteSpending()
                isDeleteShowing = false
            },
        )
    }
    }

}

@Composable
fun SpendingInfo(
    name: String,
    value: String,
) {
    Row {
        Text(
            text = "$name :",
            color = MaterialTheme.colorScheme.onSurface.copy(.8f),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerDropDownMenu(
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit,
    homeState: HomeState
) {

    var isExpanded by rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = modifier.shadow(
            elevation = 1.dp,
            shape = RoundedCornerShape(16.dp)
        )
    ) {
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            },
            modifier = Modifier.heightIn(max = 400.dp),
            offset = DpOffset(10.dp, 0.dp),
        ) {
            homeState.dateList.forEachIndexed { index, zonedDateTime ->
                if (index == 0) {
                    HorizontalDivider()
                }
                Text(
                    text = zonedDateTime.toFormat(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            onItemClick(index)
                            isExpanded = false
                        }
                )
                HorizontalDivider()
            }
        }
        Row(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(16.dp)
                )
                .background(MaterialTheme.colorScheme.primaryContainer)
                .clickable {
                    isExpanded = true
                }
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = homeState.pickDate.toFormat(),
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color.White)
        }
    }

}

//format date zoneDateTime to string
@RequiresApi(Build.VERSION_CODES.O)
fun ZonedDateTime.toFormat(): String {
    return "$dayOfMonth-$month-$year"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    onBalanceClick: () -> Unit,
    balance: Double,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = {
            Text(
                text = "$$balance", fontSize = 35.sp,
                maxLines = 1,
                fontFamily = FontFamily.SansSerif,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.primary,
            )
        },
        modifier = Modifier.padding(start = 12.dp, end = 16.dp),
        actions = {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary.copy(.6f),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(.3f))
                    .clickable {
                        onBalanceClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("$", fontSize = 26.sp, fontWeight = FontWeight.SemiBold)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        ),
        scrollBehavior = scrollBehavior
    )

}