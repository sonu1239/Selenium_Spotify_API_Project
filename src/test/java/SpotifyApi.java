
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class SpotifyApi {

	public String token;
	String userId;
	String playListId;
	String trackId; 

	@BeforeTest
	public void getToken() {
		token = "Bearer BQBpESgqUw18KYG-8muwQ5SpBrW5XZKGKdzejQTcLPYlrmIySJsLceKvu-f6-tZYW_xh6EoFi8Uu2Qwso5bWMdptOC_Ad2f5ZlrmurOTvbjQTNruzrRbpR8WGjf4m5024QkYc9P2apScYmE1BZL8eux4TksYzLulAS42AfAQDhRn4ARv2Uq3EbLrV20AZ45kXXHOiykKlGgVECpsydByQRfInEcKuFh5KNFqQFu1_GlIiMPRSDI5njy4hpkTP7AukSpQhnSglq0YzdAMtn2Yz_PhYWzGtdy45FI";
		playListId = "29vluldnfoM66d3AoTwBpn";
		trackId ="4ws4fIFJDtQAjNQ53KYVl2";
	}

	@Test(priority = 0)
	public void test_GetCurrentUsersProfile() {
		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", token).when().get("https://api.spotify.com/v1/me");
		response.prettyPrint();
		userId = response.path("id");
		System.out.println("User Id is : " + userId);
		int statusCode = response.getStatusCode();
		System.out.println("Status code: " + statusCode);
		Assert.assertEquals(statusCode, 200);
		Assert.assertEquals(userId, "313df25p6epz6mn6pfrvmqaut7rm");
	}

	// Get User's Profile
	@Test(priority = 1)
	public void test_GetUsersProfile() {
		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", token).when().get("https://api.spotify.com/v1/users/" + userId + "");
		response.prettyPrint();
//		int statusCode = response.getStatusCode();
//		System.out.println("Status code: " + statusCode);
//		Assert.assertEquals(statusCode, 200);
		response.then().assertThat().statusCode(200);
	}

	// Create Playlist

	@Test(priority = 2)
	public void testCreatePlaylistShouldStatusCode() {
		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", token)
				.body("{\r\n" + "  \"name\": \"Automation Playlist1\",\r\n"
						+ "  \"description\": \"New playlist description\",\r\n" + "  \"public\": false\r\n" + "}")
				.when().post("https://api.spotify.com/v1/users/" + userId + "/playlists");
		response.prettyPrint();
		playListId = response.path("id");
		System.out.println("Playlist Id is: " + playListId);
		response.then().assertThat().statusCode(201);

	}

	// Get Playlist
	@Test(priority = 3)
	public void testGetPlaylist() {
		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", token).when()
				.get("	https://api.spotify.com/v1/playlists/" + playListId + "");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);

	}

	// Get Current User's Playlists
	@Test(priority = 4)
	public void testGetCurrentUserPlaylists() {
		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", token).when().get("https://api.spotify.com/v1/me/playlists");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);

	}

	// Get Playlist Cover Image
	@Test(priority = 5)
	public void test_GetPlaylistCoverImage() {
		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", token).when()
				.get("	https://api.spotify.com/v1/playlists/" + playListId + "/images");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);

	}

	// Get Playlist Items
	@Test(priority = 6)
	public void testGetPlaylistItems() {
		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", token).when()
				.get("https://api.spotify.com/v1/playlists/" + playListId + "/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);

	}

	// Get User's Playlists
	@Test(priority = 7)
	public void testGetUsersPlaylists() {
		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", token).when().get("https://api.spotify.com/v1/users/" + userId + "/playlists");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);

	}

	// Search for Item
	@Test(priority = 8)
	public void testSearchForItem() {
		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", token).queryParam("q", "Arijit Singh").queryParam("type", "track").when()
				.get("https://api.spotify.com/v1/search");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);

	}

	// Add Items to Playlist
	@Test(priority = 9)
	public void testAddItemstoPlaylist() {
		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", token)
				.queryParams("uris",
						"spotify:track:4ws4fIFJDtQAjNQ53KYVl2,spotify:track:5AHwSsMTEGwZoKew1JMXI0,spotify:track:5pjSpt2mstf5JTf46FbT48,spotify:track:6zyBA0diqQ5DsWUZdJWWfa")
				.when().post("https://api.spotify.com/v1/playlists/" + playListId + "/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(201);

	}

	// Update Playlist Items
	@Test(priority = 10)
	public void testUpdatePlaylistItems() {
		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", token).body("{\r\n" + "\"range_start\": 1,\r\n" + "\"insert_before\": 4,\r\n"
						+ "\"range_length\": 1\r\n" + "}")
				.when().put("https://api.spotify.com/v1/playlists/" + playListId + "/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);

	}

	// Change Playlist Details
	@Test(priority = 11)
	public void testChangePlaylistDetails() {
		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", token)
				.body("{\r\n" + "  \"name\": \"Automation Playlist\",\r\n"
						+ "  \"description\": \"Updated playlist description\",\r\n" + "  \"public\": false\r\n" + "}")
				.when().put("https://api.spotify.com/v1/playlists/" + playListId + "");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);

	}

	// Remove Playlist Items
	@Test(priority = 12)
	public void test_RemovePlaylistItems() {
		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", token)
				.body("{ \"tracks\": [{ \"uri\": \"spotify:track:4ws4fIFJDtQAjNQ53KYVl2\" }] }")
				.when().delete("https://api.spotify.com/v1/playlists/" + playListId + "/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}

	

	// Get Track's Audio Analysis
	@Test(priority = 13)
	public void test_GetTracksAudioAnalysis() {
		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", token).when().get("https://api.spotify.com/v1/audio-analysis/" + trackId + "");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);

	}

	// Get Tracks' Audio Features
	@Test(priority = 14)
	public void test_GetTracksAudioFeatures() {
		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", token).when().get("https://api.spotify.com/v1/audio-features");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);

	}

	// Get Track's Audio Features
	@Test(priority = 15)
	public void test_GetTracksAudioFeatures_ShouldtatusCode() {
		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", token).when().get("https://api.spotify.com/v1/audio-features/" + trackId + "");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);

	}

	// Get Several Tracks
	@Test(priority = 16)
	public void test_GetSeveralTracks() {
		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", token).queryParam("ids", "0DqXA397QkcVjWq5Wa7DMt").when()
				.get("https://api.spotify.com/v1/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);

	}

	// Get Track
	@Test(priority = 17)
	public void test_GetTrack() {
		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", token).when().get("https://api.spotify.com/v1/tracks/" + trackId + "");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);

	}

}
