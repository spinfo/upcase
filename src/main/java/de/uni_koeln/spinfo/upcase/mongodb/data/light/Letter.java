package de.uni_koeln.spinfo.upcase.mongodb.data.light;

public class Letter {

	/**
	 * The word id
	 */
	private String wordId;
	
	/**
	 * The words index
	 */
	private int wordIndex;
	
	/**
	 * The current word character
	 */
	private char character;
	
	/**
	 * The position of the current character within the page Range
	 */
	private int charPosInText;
 
	/**
	 * The position of the current character within the word
	 */
	private int charPosInWord;

	public Letter(String wordId, int wordIndex, char character, int charPosInWord, int charPosInText) {
		this.wordId = wordId;
		this.wordIndex = wordIndex;
		this.character = character;
		this.charPosInWord = charPosInWord;
		this.charPosInText = charPosInText;
	}

	public String getWordId() {
		return wordId;
	}

	public void setWordId(String wordId) {
		this.wordId = wordId;
	}

	public char getCharacter() {
		return character;
	}

	public void setCharacter(char character) {
		this.character = character;
	}

	public int getWordIndex() {
		return wordIndex;
	}

	public void setWordIndex(int wordIndex) {
		this.wordIndex = wordIndex;
	}

	public int getCharPosInText() {
		return charPosInText;
	}

	public void setCharPosInText(int charPosInText) {
		this.charPosInText = charPosInText;
	}

	public int getCharPosInWord() {
		return charPosInWord;
	}

	public void setCharPosInWord(int charPosInWord) {
		this.charPosInWord = charPosInWord;
	}

}
