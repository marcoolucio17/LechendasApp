package com.example.lechendasapp.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Devices.PHONE
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes

@Preview(
    name = "Galaxy Tab A9 Portrait",
    device = "id:Samsung Tablet Galaxy Tab A9",
    showSystemUi = true,
    showBackground = true,
)
@Preview(
    name = "Phone",
    device = PHONE,
    showSystemUi = true,
    showBackground = true,
)
@Preview(
    name = "Galaxy Tab A9 Landscape",
    device = "id:Samsung Tablet Galaxy Tab A9",
    widthDp = 1340,
    heightDp = 800,
    showBackground = true,
    //cannot showUi it's bugged
)
/*@Preview(
    name = "Phone - Landscape",
    device = "spec:width = 411dp, height = 891dp, orientation = landscape, dpi = 420",
    showSystemUi = true,
    showBackground = true,
)*/

annotation class ScreenPreviews