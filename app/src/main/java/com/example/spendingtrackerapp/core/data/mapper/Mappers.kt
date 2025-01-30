package com.example.spendingtrackerapp.core.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.spendingtrackerapp.core.data.local.db.SpendingEntity
import com.example.spendingtrackerapp.core.domain.model_ui.SpendingUi
import java.time.Instant
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
fun SpendingEntity.toSpendingUi():SpendingUi{
    return SpendingUi(
        spendingId = spendingId?:0,
        name = name,
        price = price,
        kilograms = kilograms,
        quantity = quantity,
        dateTime = Instant.parse(dateTimeUtc).atZone(ZoneId.of("UTC")),
        imageUrl
    )


}

@RequiresApi(Build.VERSION_CODES.O)
fun SpendingUi.toNewSpendingEntity() : SpendingEntity{
    return SpendingEntity(
        name = name,
        price = price,
        kilograms = kilograms,
        quantity = quantity,
        dateTimeUtc = dateTime.toInstant().toString(),
        imageUrl = imageUrl
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun SpendingUi.toEditSpendingEntity():SpendingEntity{
    return SpendingEntity(
        spendingId = spendingId,
        name = name,
        price = price,
        kilograms = kilograms,
        quantity = quantity,
        dateTimeUtc = dateTime.toInstant().toString(),
        imageUrl = imageUrl
    )
}