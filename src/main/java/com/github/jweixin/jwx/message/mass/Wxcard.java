package com.github.jweixin.jwx.message.mass;

/**
 * 卡券
 * @author Administrator
 *
 */
public class Wxcard {
	/**
	 * 卡券id
	 */
	private String cardId;
	
	public Wxcard() {
	}
	
	public Wxcard(String cardId) {
		this.cardId = cardId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

}
