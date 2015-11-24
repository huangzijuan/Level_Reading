package com.example.level_reading.data;

import java.io.Serializable;

public class Chapter implements Serializable{
	private String unit;
	private String lesson;
	private String e_title;
	private String c_title;
	private String content;
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getLesson() {
		return lesson;
	}
	public void setLesson(String lesson) {
		this.lesson = lesson;
	}
	public String getE_title() {
		return e_title;
	}
	public void setE_title(String e_title) {
		this.e_title = e_title;
	}
	public String getC_title() {
		return c_title;
	}
	public void setC_title(String c_title) {
		this.c_title = c_title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Chapter [unit=" + unit + ",lesson=" + lesson + ", e_title=" + e_title
				+ ", c_title=" + c_title + ", content=" + content + "]";
	}

}
