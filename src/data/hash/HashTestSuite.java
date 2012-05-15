package data.hash;

public class HashTestSuite<T> {
	private T hashSubject;
	public HashTestSuite(T subject){
		setHashSubject(subject);
	}
	
	public T getHashSubject() {
		return hashSubject;
	}
	public void setHashSubject(T hashSubject) {
		this.hashSubject = hashSubject;
	}
}
