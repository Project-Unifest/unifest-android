package com.unifest.android.feature.map.model

import android.os.Parcelable
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.clustering.ClusteringKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class BoothMapModel(
    val id: Long = 0L,
    val name: String = "",
    val category: String = "",
    val description: String = "",
    val thumbnail: String = "",
    val location: String = "",
    val latitude: Double = 0.toDouble(),
    val longitude: Double = 0.toDouble(),
    val isSelected: Boolean = false,
) : Parcelable, ClusteringKey {
    override fun getPosition(): LatLng {
        return LatLng(latitude, longitude)
    }
}

// @Parcelize
// data class BoothMapModel(
//     val id: Long = 0L,
//     val name: String = "",
//     val category: String = "",
//     val description: String = "",
//     val thumbnail: String = "",
//     val location: String = "",
//     val latitude: Double = 0.toDouble(),
//     val longitude: Double = 0.toDouble(),
//     val isSelected: Boolean = false,
// ) : Parcelable, TedClusterItem {
//     override fun getTedLatLng(): TedLatLng {
//         return TedLatLng(latitude, longitude)
//     }
// }
