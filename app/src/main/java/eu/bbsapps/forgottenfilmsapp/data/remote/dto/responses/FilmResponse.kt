package eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses

/**
 * The class contains detailed information about the film, also includes if a user has liked the film
 * @param name Film title
 * @param imageUrls a list of image url, the first one is the main image
 * @param description Film description
 * @param categories a list of genres
 * @param likes total like count
 * @param dislikes total dislike count
 * @param isLiked provides information if the video is like by the user (expected values:
 *          -1 - disliked;
 *          0 -  not rated;
 *          1 - liked
 * )
 * @param url url to the .mp4 file
 * @param id
 */
data class FilmResponse(
    val name: String,
    val imageUrls: List<String>,
    val description: String,
    val categories: List<String>,
    val likes: Int,
    val dislikes: Int,
    val isLiked: Int = -1,
    val url: String,
    val id: String
)
