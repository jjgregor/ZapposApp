package com.example.zappossearchandtext;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class Items {
	@SerializedName("productId")
	public String _productId;
	@SerializedName("brandName")
	public String _brandName;
	@SerializedName("productName")
	public String _productName;
	@SerializedName("thumbnailImageUrl")
	public String _thumbnailImageUrl;
	@SerializedName("originalPrice")
	public String _originalPrice;
	@SerializedName("price")
	public String _price;
	@SerializedName("percentOff")
	public String _percentOff;
	@SerializedName("productUrl")
	public String _productUrl;
	@SerializedName("results")
	public Items _results;
	public String _email;
	public boolean _sent;
	
	public Items(String productName, String percentOff, String productId, String brandName, String thumbnailImageUrl, String price, String originalPrice, String productUrl) {
		_productName = productName;
		_percentOff = percentOff;
		_productId = productId;
		_brandName = brandName;
		_thumbnailImageUrl = thumbnailImageUrl;
		_price = price;
		_originalPrice = originalPrice;
		_productUrl = productUrl;
	}

	public void setResults(Items results) {
	    _results = results;
	}
	public Items getResults() {
	    return _results;
	}

	public String getProductName() {
		return _productName;
	}

	public void setProductName(String productName) {
		_productName = productName;
	}

	// Percent Off
	public String getPercentOff() {
		return _percentOff;
	}

	public void setPercentOff(String percentOff) {
		_percentOff = percentOff;
	}


	// hasBeenNotified
	public boolean getHasBeenNotified() {
		return _sent;
	}

	public void setHasBeenNotified(boolean sent) {
		_sent = sent;
	}

	public String getThumbnailImageUrl() {
		return _thumbnailImageUrl;
	}

	public void setThumbnailImageUrl(String thumbnailImageUrl) {
		_thumbnailImageUrl = thumbnailImageUrl;
	}

	public String getProductId() {
		return _productId;
	}

	public void setProductId(String productId) {
		_productId = productId;
	}

	public String getBrandName() {
		return _brandName;
	}

	public void setBrandName(String brandName) {
		_brandName = brandName;
	}

	public String getPrice() {
		return _price;
	}

	public void setPrice(String price) {
		_price = price;
	}

	public String getOriginalPrice() {
		return _originalPrice;
	}

	public void setOriginalPrice(String originalPrice) {
		_originalPrice = originalPrice;
	}

	// Product URL
	public String getProductUrl() {
		return _productUrl;
	}

	public void setProductUrl(String productUrl) {
		_productUrl = productUrl;
	}
}
