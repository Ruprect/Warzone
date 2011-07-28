package couk.Adamki11s.Languages;

public class LangInterface_FR {
	
	public String getString(String s){
		return parseString(s);
	}
	
	private String parseString(String s){
		if(s.equals("Hello")){
			return "Bonjour";
		}
		return "";
	}

}
