package com.unifest.android.feature.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.unifest.android.core.common.utils.formatWithDayOfWeek
import com.unifest.android.core.common.utils.toLocalDate
import com.unifest.android.core.designsystem.ComponentPreview
import com.unifest.android.core.designsystem.R as designR
import com.unifest.android.core.designsystem.component.NetworkImage
import com.unifest.android.core.designsystem.theme.Content4
import com.unifest.android.core.designsystem.theme.Content6
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.core.model.FestivalModel

@Composable
internal fun IncomingFestivalCard(
    festival: FestivalModel,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NetworkImage(
                imgUrl = festival.thumbnail,
                contentDescription = "Festival Thumbnail",
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "${festival.beginDate.toLocalDate().formatWithDayOfWeek()} - ${festival.endDate.toLocalDate().formatWithDayOfWeek()}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = Content6,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = festival.festivalName,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = Content4,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = designR.drawable.ic_location_grey),
                        contentDescription = "Location Icon",
                        modifier = Modifier
                            .size(10.dp)
                            .align(Alignment.CenterVertically),
                        tint = Color.Unspecified,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = festival.schoolName,
                        style = Content6,
                        color = MaterialTheme.colorScheme.onSecondary,
                    )
                }
            }
        }
    }
}

@ComponentPreview
@Composable
private fun IncomingFestivalCardPreview() {
    UnifestTheme {
        IncomingFestivalCard(
            festival = FestivalModel(
                festivalId = 1,
                schoolId = 1,
                festivalName = "대동제",
                beginDate = "2024-05-21",
                endDate = "2024-05-23",
                thumbnail = "",
                schoolName = "건국대",
            ),
        )
    }
}
