package com.example.spendingtrackerapp.inser_spending.presentation

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsProperties.TestTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.example.spendingtrackerapp.utils.BackGround
import org.jetbrains.annotations.Async
import org.koin.compose.viewmodel.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InsertSpendingScreen(
    onSaveSpending: () -> Unit,
    id: Int?,
    viewModel: InsertSpendingViewModel = koinViewModel()
) {
    LaunchedEffect(id) {
        if (id != null) {
            viewModel.loadSpendingDetails(id = id)
        } else {
            viewModel.resetField()
        }
    }
    InsertSpendingInnerScreen(
        onSave = onSaveSpending,
        id = id,
        state = viewModel.state,
        action = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertSpendingInnerScreen(
    onSave: () -> Unit,
    id: Int?,
    state: InsertSpendingState,
    action: (InsertAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (id == null) "Insert Spending" else "Edit Spending",
                        fontSize = 35.sp,
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
                                color = MaterialTheme.colorScheme.onBackground.copy(.6f),
                                shape = RoundedCornerShape(15.dp)
                            )
                            .background(MaterialTheme.colorScheme.primaryContainer.copy(.3f))
                            .clickable {
                                if (id != null) {
                                    action(InsertAction.OnSaveSpending(id))
                                } else {
                                    action(InsertAction.OnSaveSpending(null))
                                }
                                onSave()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = null)
                    }
                },
            )
        }

    ) { innerPadding ->
        BackGround()
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Spacer(Modifier.height(30.dp))
            AsyncImage(
                model = state.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .fillMaxHeight(.3f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
                    .clickable {
                        action(InsertAction.UpdateImageDialogVisibility)
                    }
                        ,
                contentScale = ContentScale.Crop
            )
            OutlinedTextField(onValueChange = {
                action(InsertAction.OnUpdateName(it))
            }, value = state.name,
                maxLines = 1,

                label = { Text("Enter Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp))
            Spacer(Modifier.height(30.dp))

            OutlinedTextField(onValueChange = {
                action(InsertAction.OnUpdatePrice(
                    it.toDoubleOrNull()?:0.0
                ))
            }, value = state.price.toString(),
                label = { Text("Enter Price") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                )
            )
            Spacer(Modifier.height(30.dp))

            Row (

            ){
                OutlinedTextField(onValueChange = {
                    action(InsertAction.OnUpdateKilograms(it.toDoubleOrNull()?:0.0))
                }, value = state.kilograms.toString(),
                    label = { Text("Enter kilograms") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 20.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ))
                OutlinedTextField(onValueChange = {
                    action(InsertAction.OnUpdateQuantity(it.toDoubleOrNull()?:0.0))
                }, value =  state.quantity.toString(),
                    label = { Text("Enter Quantity") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 20.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ))
            }
            Spacer(Modifier.height(30.dp))
        }
        if (state.isImageDialogShowing){
            Dialog(
                onDismissRequest = {
                    action(InsertAction.UpdateImageDialogVisibility)
                }
            ) {
                ImageDialogContent(
                    state = state,
                    onSearchQuery ={
                        action(InsertAction.UpdateSearchImageQuery(it))
                    },
                    onImageClick = {imgUrl->
                        action(InsertAction.PickImage(imgUrl))
                    }
                )
            }
        }

    }
}

@Composable
fun ImageDialogContent(
    state: InsertSpendingState,
    onSearchQuery:(String)->Unit,
    onImageClick:(String)->Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.6f)
            .clip(RoundedCornerShape(26.dp))
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(onValueChange = {
            onSearchQuery(it)
            Log.d(TAG, "query Change: $it")
        }, value = state.imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                ,
            label = {
                Text("Search Image")
            }
        )
        Spacer(Modifier.height(8.dp))
        if (state.isImageLoading){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator()
                Log.d(TAG, "ImageDialogContent: ${state.imageUrl}")
            }
        }else{
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Adaptive(120.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                itemsIndexed(state.imageList){index, item ->
                    AsyncImage(
                        model = item,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .clickable {
                                onImageClick(item)
                                Log.d(TAG, "ImageDialogContent: $item")
                            },
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }

}

@Preview
@Composable
private fun InsertSpendingInnerScreenPreview() {
    InsertSpendingInnerScreen(
        onSave = {},
        id = 1,
        state = InsertSpendingState(),
        action = { }
    )
}
