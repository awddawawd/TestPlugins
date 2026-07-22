package com.example.voiranime

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.AppUtils.parseJson
import com.lagradost.cloudstream3.app

class VoirAnimeProvider : MainAPI() {
    override var mainUrl = "https://voir-anime.to"
    override var name = "Voir Anime"
    override val supportedTypes = setOf(TvType.Anime)

    // 1. The Blueprint
    override val mainPage = mainPageOf(
        Pair("$mainUrl/?filter=subbed", "En cours")
    )

    // 2. The Engine
    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        val url = if (page == 1) {
            request.data
        } else {
            request.data.replace("?", "page/$page/?")
        }

        val document = app.get(url).document

        val home = document.select("div.page-item-detail").mapNotNull { element ->
            val titleElement = element.selectFirst("div.post-title h3 a")
            val title = titleElement?.text() ?: return@mapNotNull null
            val link = fixUrlNull(titleElement.attr("href")) ?: return@mapNotNull null
            
            val imgElement = element.selectFirst("div.item-thumb img")
            var poster = imgElement?.attr("src")
            if (poster.isNullOrBlank() || poster.startsWith("data:image")) {
                poster = imgElement?.attr("data-src")
            }

            val epText = element.selectFirst("div.chapter-item span.chapter a")?.text()
            val epNum = Regex("""\d+""").find(epText ?: "")?.value?.toIntOrNull()

            newAnimeSearchResponse(title, link) {
                this.posterUrl = fixUrlNull(poster)
                addDubStatus(isDub = false, episodes = epNum) 
            }
        }

        return newHomePageResponse(request.name, home)
    }

    // Required by Cloudstream (left blank for now since we are only testing the homepage)
    override suspend fun search(query: String): List<SearchResponse> {
        return emptyList()
    }

    override suspend fun load(url: String): LoadResponse {
        throw ErrorLoadingException("Page loading not implemented yet!")
    }
}

