package couk.Adamki11s.Languages;

public class LangInterface_FR {
	
	public String getString(String s){
		return parseString(s);
	}
	
	private String parseString(String s){
		if(s.equalsIgnoreCase("Hello")){
            return "Hallo";
    }
    if(s.equalsIgnoreCase("[Warzone] By Adamki11s of the MineDev team.")){
            return "[Warzone] Von Adamki11s aus dem MineDev Team.";
    }
    if(s.equalsIgnoreCase("version")){
            return "Version";
    }
    if(s.equalsIgnoreCase("enabled successfully")){
            return "erfolgreich aktiviert";
    }
    if(s.equalsIgnoreCase("Warzone disabled successfully")){
            return "Warzone erfolgreich deaktiviert";
    }
    if(s.equalsIgnoreCase("Statistics reset for player : ")){
            return "Statistiken wurden zurückgesetzt für Spieler : ";
    }
    if(s.equalsIgnoreCase("This player does not exist in the warzone database!")){
            return "Dieser Spieler existiert in der Warzone Datenbank nicht!";
    }
    if(s.equalsIgnoreCase("You must provide the block id!")){
            return "Sie müssen die Block ID angeben!";
    }
    if(s.equalsIgnoreCase("You do not have permission to disable Warzone!")){
            return "Sie haben nicht die Erlaubnis Warzone zu deaktivieren!";
    }
    if(s.equalsIgnoreCase("Search cancelled successfully.")){
            return "Suche erfolgreich abgebrochen.";
    }
    if(s.equalsIgnoreCase("You are not searching for a game!")){
            return "Sie suchen derzeit nicht nach einem Spiel!";
    }
    if(s.equalsIgnoreCase("Returned to previous location.")){
            return "Zu vorherigem Standort zurückgekehrt.";
    }
    if(s.equalsIgnoreCase("No return location exists!")){
            return "Es ist kein Rückkehrort gespeichert!";
    }
    if(s.equalsIgnoreCase("has no statistics to display.")){
            return "hat keine Statistiken zum Anzeigen.";
    }
    if(s.equalsIgnoreCase("You have no statistics to display!")){
            return "Sie haben keine Statistiken zum Anzeigen!";
    }
    if(s.equalsIgnoreCase("You do not have permissions to do this!")){
            return "Sie haben keine Erlaubnis dies zu tun!";
    }
    if(s.equalsIgnoreCase("Invalid Sword Type")){
            return "Ungültiger Schwert Typ";
    }
    if(s.equalsIgnoreCase("You must be level")){
            return "Sie müssen Stufe";
    }
    if(s.equalsIgnoreCase("or above to do this")){
            return "oder höher sein um dies zu tun";
    }
    if(s.equalsIgnoreCase("Sword preference changed to")){
            return "Bevorzugtes Schwertmaterial geändert zu";
    }
    if(s.equalsIgnoreCase("Invalid Material Type!")){
            return "Ungültiges Material!";
    }
    if(s.equalsIgnoreCase("Armour type changed to")){
            return "Art der Rüstung geändert zu";
    }
    if(s.equalsIgnoreCase("sucessfully!")){
            return "erfolgreich!";
    }
    if(s.equalsIgnoreCase("to wield this armour!")){
            return "diese Rüstung zu tragen!";
    }
    if(s.equalsIgnoreCase("Error : Armour type can only be")){
            return "Fehler : Rüstungstyp kann nur sein";
    }
    if(s.equalsIgnoreCase("Chill in the lobby while we find a match for you.")){
            return "Warten Sie in der Lobby während wir ein Spiel suchen.";
    }
    if(s.equalsIgnoreCase("Searching for players...")){
            return "Suche nach Spielern...";
    }
    if(s.equalsIgnoreCase("You are already searching!")){
            return "Sie suchen bereits!";
    }
    if(s.equalsIgnoreCase("Do &e/warzone stop search &redto cancel")){
            return "Benutze &e/warzone stop search &redzum Abbrechen";
    }
    if(s.equalsIgnoreCase("No players found, request timed out.")){
            return "Keine Spieler gefunden, Zeitüberschreitung der Anfrage.";
    }
    if(s.equalsIgnoreCase("Timeout in")){
            return "Zeitüberschreitung in";
    }
    if(s.equalsIgnoreCase("match found with")){
            return "Spiel gefunden mit";
    }
    if(s.equalsIgnoreCase("Selecting random map...")){
            return "Wähle zufällige Karte...";
    }
    if(s.equalsIgnoreCase("All maps are occupied! Please wait.")){
            return "Alle Karten sind besetzt! Bitte warten.";
    }
    if(s.equalsIgnoreCase("Map Chosen")){
            return "Karte gewählt";
    }
    if(s.equalsIgnoreCase("Loading map...")){
            return "Lade Karte...";
    }
    if(s.equalsIgnoreCase("Map Loaded")){
            return "Karte geladen";
    }
    if(s.equalsIgnoreCase("Let battle commence!")){
            return "Lasst die Schlacht beginnen!";
    }
    if(s.equalsIgnoreCase("The warzone world is not editable!")){
            return "Die Warzone Welt ist nicht veränderbar!";
    }
    if(s.equalsIgnoreCase("You quit unexpectedly!")){
            return "Sie haben unerwarteterweise das Spiel verlassen!";
    }
    if(s.equalsIgnoreCase("Inventory was restored successfully.")){
            return "Inventar erfolgreich wiederhergestellt.";
    }
    if(s.equalsIgnoreCase("You quit during a game! Returned to initial location.")){
            return "Sie sind vor Ende des Spiels gegangen! Zur vorherigen Position zurückgekehrt.";
    }
    if(s.equalsIgnoreCase("Time Left")){//Shows on sign. make sure message is not too long
            return "Übrige Zeit";
    }
    if(s.equalsIgnoreCase("You struckdown")){
            return "Sie erschlugen";
    }
    if(s.equalsIgnoreCase("You got struckdown by")){
            return "Sie wurden erschlagen von";
    }
    if(s.equalsIgnoreCase("You shot")){
            return "Sie erschossen";
    }
    if(s.equalsIgnoreCase("You got shot by")){
            return "Sie wurden erschossen von";
    }
    if(s.equalsIgnoreCase("Quit the game!")){
            return "Verlasse das Spiel!";
    }
    if(s.equalsIgnoreCase("remaining.")){
            return "übrig.";
    }
    if(s.equalsIgnoreCase("The match was a draw!")){
            return "Das Spiel war unentschieden!";
    }
    if(s.equalsIgnoreCase("You gained")){
            return "Sie erlangten";
    }
    if(s.equalsIgnoreCase("Game over!")){
            return "Game over!";
    }
    if(s.equalsIgnoreCase("was victorious!")){
            return "war der Sieger!";
    }
    if(s.equalsIgnoreCase("won with")){
            return "gewann mit";
    }
    if(s.equalsIgnoreCase("kills.")){
            return "Punkten."; // tell me if you find a better word ^^
    }
    return "";
	}

}
