package entity;

import android.graphics.Bitmap;

public class BaoXueYouXi {
	private String digest;
	private String lmodify;
	private String title;
	private byte[] image;
	private Bitmap bitmap;
	private String imgsrc;//!!
	
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public String getImodify() {
		return lmodify;
	}
	public void setImodify(String imodify) {
		this.lmodify = imodify;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public String getImgsrc() {
		return imgsrc;
	}
	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	
}
