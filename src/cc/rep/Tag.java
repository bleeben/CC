package cc.rep;

public class Tag {
	private String text;
	
	public Tag(String text){
		this.text = text;
	}
	
	public String getText(){
		return text;
	}

	public boolean matchesTag(Tag tag){
		return text.startsWith(tag.getText());
	}
}
