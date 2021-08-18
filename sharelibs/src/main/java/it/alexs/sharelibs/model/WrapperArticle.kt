package it.alexs.sharelibs.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class WrapperArticle(
    @Expose
    @SerializedName("articles")
    var articles: List<Article>? = null,
    @Expose
    @SerializedName("status")
    var status: String? = null,
    @Expose
    @SerializedName("totalResults")
    var totalResults: Int? = null
) : Parcelable