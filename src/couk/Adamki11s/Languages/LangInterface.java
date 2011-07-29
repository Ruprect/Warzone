package couk.Adamki11s.Languages;

public class LangInterface{
	
	public Langs lang = Langs.ENGLISH;
	
	private LangInterface_EN langGB = new LangInterface_EN();
	private LangInterface_FR langFR = new LangInterface_FR();
	private LangInterface_DE langDE = new LangInterface_DE();
	
	public Langs getLang(){
		return lang;
	}
	
	public LangInterface(Langs l){
		lang = l;
	}
	
	public String getObj(String msg){
		switch(lang){
			case ENGLISH: return langGB.getString(msg);
			case FRENCH: return langFR.getString(msg);
			case GERMAN: return langDE.getString(msg);
			default: return langGB.getString(msg);
		}
	}
	
	public enum Langs{
		ENGLISH,
		FRENCH,
		GERMAN;
		
		@Override
		public String toString(){
			return super.toString();
		}
	}



}
