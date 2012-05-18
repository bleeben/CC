package data.hash;

public class TestMain {
public static void main(String[] arg){
	HashTestSuite suite1=new HashTestSuite(new LinearHash<String,Integer>());
	HashTestSuite suite2=new HashTestSuite(new CachedLinearHash<String,Integer>());
	HashTestSuite suite3=new HashTestSuite(new ChainingHash<String,Integer>());
	HashTestSuite suite4=new HashTestSuite(new CuckooHash<String,Integer>());
	HashTestSuite suite5=new HashTestSuite(new JavaHashWrapper<String,Integer>());
	HashTestSuite suite6=new HashTestSuite(new FKSPerfectHash<String,Integer>());
	suite1.start();
	suite2.start();
	suite3.start();
	suite4.start();
	suite5.start();
	suite6.start();
	extractData();
}
public static void extractData(){
	
}
}
