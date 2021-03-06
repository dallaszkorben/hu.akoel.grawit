package hu.akoel.grawit.enums.list.elementtypeoperations.full;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

public enum ScriptElementTypeOperationsFullListEnum implements ElementTypeOperationsListEnumInterface{	
	ADD_CONSTANT_TO_PARAMETERS( 0, CommonOperations.getTranslation( "editor.label.step.elementtype.script.addconstanttoparameters") ),
	ADD_STORED_TO_PARAMETERS( 1, CommonOperations.getTranslation( "editor.label.step.elementtype.script.addstoredtoparameters") ),
	ADD_STRING_TO_PARAMETERS( 2, CommonOperations.getTranslation( "editor.label.step.elementtype.script.addstringtoparameters") ),
	CLEAR_PARAMETERS( 3, CommonOperations.getTranslation( "editor.label.step.elementtype.script.clearparameters") ),
	EXECUTE_SCRIPT( 4, CommonOperations.getTranslation( "editor.label.step.elementtype.script.executescript") ),
	;
	
	private String translatedName;
	private int index;
	
	private ScriptElementTypeOperationsFullListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return ScriptElementTypeOperationsFullListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static ScriptElementTypeOperationsFullListEnum getOperationByIndex( int index ){
		switch (index){		
		case 0:	return ADD_CONSTANT_TO_PARAMETERS;
		case 1:	return ADD_STORED_TO_PARAMETERS;
		case 2:	return ADD_STRING_TO_PARAMETERS;
		case 3:	return CLEAR_PARAMETERS;
		case 4:	return EXECUTE_SCRIPT;
		default: return CLEAR_PARAMETERS;
		}
	}
	
}
