package it.alexs.sharelibs.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Article(
    @Expose
    @SerializedName("author")
    var author: String? = null,
    @Expose
    @SerializedName("content")
    var content: String? = null,
    @Expose
    @SerializedName("description")
    var description: String? = null,
    @Expose
    @SerializedName("publishedAt")
    var publishedAt: String? = null,
    @Expose
    @SerializedName("source")
    var source: Source? = null,
    @Expose
    @SerializedName("title")
    var title: String? = null,
    @Expose
    @SerializedName("url")
    var url: String? = null,
    @Expose
    @SerializedName("urlToImage")
    var urlToImage: String? = null
) : Parcelable