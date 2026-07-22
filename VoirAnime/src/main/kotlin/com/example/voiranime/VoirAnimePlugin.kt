package com.example.voiranime

import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.Plugin
import android.content.Context

@CloudstreamPlugin
class VoirAnimePlugin : Plugin() {
    override fun load(context: Context) {
        // This registers your scraper so the app can see it
        registerMainAPI(VoirAnimeProvider())
    }
}

