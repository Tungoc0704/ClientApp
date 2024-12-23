package Model;

import java.io.Serializable;

public class Post implements Serializable {
//	private String poster;
	private String time;
	private String contentPost;
	private String imageSrc;
	private String amountFavorite;
	private User user;

	public Post() {

	}

	public Post(User user, String time, String imageUrl, String caption) {
		this.user = user;
		this.time = time;
		this.contentPost = caption;
		this.imageSrc = imageUrl;

	}

//	public String getPoster() {
//		return poster;
//	}
//
//	public void setPoster(String poster) {
//		this.poster = poster;
//	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContentPost() {
		return contentPost;
	}

	public void setContentPost(String contentPost) {
		this.contentPost = contentPost;
	}

	public String getAmountFavorite() {
		return amountFavorite;
	}

	public void setAmountFavorite(String amountFavorite) {
		this.amountFavorite = amountFavorite;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
