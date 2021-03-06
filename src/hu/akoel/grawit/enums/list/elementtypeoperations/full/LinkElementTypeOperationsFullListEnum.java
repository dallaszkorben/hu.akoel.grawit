package hu.akoel.grawit.enums.list.elementtypeoperations.full;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.list.elementtypeoperations.ElementTypeOperationsListEnumInterface;

public enum LinkElementTypeOperationsFullListEnum implements ElementTypeOperationsListEnumInterface{		
	CLICK( 0, CommonOperations.getTranslation( "editor.label.step.elementtype.link.click") ),
	MOVE_TO_ELEMENT( 1, CommonOperations.getTranslation( "editor.label.step.elementtype.link.movetoelement") ),
	COMPARETEXT_TO_CONSTANT( 2, CommonOperations.getTranslation( "editor.label.step.elementtype.link.comparetexttoconstant") ),
	COMPARETEXT_TO_STORED( 3, CommonOperations.getTranslation( "editor.label.step.elementtype.link.comparetexttostored") ),
	COMPARETEXT_TO_STRING( 4, CommonOperations.getTranslation( "editor.label.step.elementtype.link.comparetexttostring") ),
	GAINTEXT_TO_ELEMENT( 5, CommonOperations.getTranslation( "editor.label.step.elementtype.link.gaintexttoelement") ),
	OUTPUTSTORED( 6, CommonOperations.getTranslation( "editor.label.step.elementtype.link.outputstored") ),	
	;
	
	private String translatedName;
	private int index;
	
	private LinkElementTypeOperationsFullListEnum( int index, String translatedName ){
	
		this.index = index;
		this.translatedName = translatedName;
	}
	
	@Override
	public String getTranslatedName(){
		return translatedName;
	}	
	
	public static int getSize(){
		return LinkElementTypeOperationsFullListEnum.values().length;
	}
	
	@Override
	public int getIndex(){
		return index;
	}
	
	public static LinkElementTypeOperationsFullListEnum getElementFieldOperationByIndex( int index ){
		switch (index){
		case 0:	return CLICK;
		case 1:	return MOVE_TO_ELEMENT;
		case 2: return COMPARETEXT_TO_CONSTANT;
		case 3: return COMPARETEXT_TO_STORED;
		case 4: return COMPARETEXT_TO_STRING;
		case 5: return GAINTEXT_TO_ELEMENT;
		case 6:	return OUTPUTSTORED;
		default: return MOVE_TO_ELEMENT;
		}
	}
	
}
