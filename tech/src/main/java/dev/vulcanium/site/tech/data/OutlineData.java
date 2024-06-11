package dev.vulcanium.site.tech.data;

public class OutlineData{
private final String label;
private final int value;

public OutlineData(String label, int value){
	this.label = label;
	this.value = value;
}

public String getLabel(){
	return label;
}

public int getValue(){
	return value;
}
}