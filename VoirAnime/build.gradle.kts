// Set the version outside the cloudstream block
version = 1

cloudstream {
    // These properties are valid for the Cloudstream extension
    description = "My custom Voir Anime provider built from scratch"
    authors = listOf("YourName")
    
    // 1 = Ok, 0 = Down, 3 = Beta
    status = 1 
    
    // Since this is for Voir Anime, we set the type and language accordingly
    tvTypes = listOf("Anime")
    language = "fr"
}
