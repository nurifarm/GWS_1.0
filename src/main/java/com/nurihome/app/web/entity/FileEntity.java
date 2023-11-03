package com.nurihome.app.web.entity;


import java.io.Serializable;

/**
 * Helper class for storing upload file contents.
 */
@SuppressWarnings("serial")
public class FileEntity implements Serializable {

	/**
	 * 파일명
	 */
	private String name;
	
	/**
	 * 파일 크기 (바이트)
	 */
	private int size;
	
	/**
	 * 파일 콘텐츠
	 */
	private byte[] content;

	/**
	 * default constructor
	 */
	public FileEntity() {}

	/**
	 * <p>parameterized constructor with arguments</p>
	 */
	public FileEntity(String name, byte[] content) {
		this.name = name;
		this.content = content;
		
		if (content != null) {
			this.size = content.length;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(" name=");
		sb.append(this.name);
		sb.append(", size=");
		sb.append(this.size);

		return sb.toString();
	}

}