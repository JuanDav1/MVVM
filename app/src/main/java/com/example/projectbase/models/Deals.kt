package com.example.projectbase.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "deals")
data class Deals(

    @SerializedName("internalName")
    var internalName: String,
    var title: String,
    var metacriticLink: String?,
    @SerializedName("dealID")
    @PrimaryKey var dealID: String,
    @SerializedName("storeID")
    var storeID: String,
    @SerializedName("gameID")
    var gameID: String,
    @SerializedName("salePrice")
    var salePrice: String,
    @SerializedName("normalPrice")
    var normalPrice: String,
    @SerializedName("isOnSale")
    var isOnSae: String,
    @SerializedName("savings")
    var savings: String,
    @SerializedName("metacriticScore")
    var metacriticScore: String,
    @SerializedName("steamRatingText")
    var steamRatingText: String?,
    @SerializedName("steamRatingPercent")
    var steamRatingPercent: String,
    @SerializedName("steamRatingCount")
    var steamRatingCount: String,
    @SerializedName("steamAppID")
    var steamAppID: String?,
    @SerializedName("releaseData")
    var releaseData: Int,
    @SerializedName("lastChange")
    var lastChange: Int,
    @SerializedName("dealRating")
    var dealRating: String,
    @SerializedName("thumb")
    var thumb: String
):Serializable
