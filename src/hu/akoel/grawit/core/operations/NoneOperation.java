package hu.akoel.grawit.core.operations;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.core.operation.interfaces.ElementOperationAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.exceptions.ElementInvalidOperationException;
import hu.akoel.grawit.gui.interfaces.progress.ProgressIndicatorInterface;

public class NoneOperation extends ElementOperationAdapter{
	private static final String NAME = "NONE";
	
	public static String getStaticName(){
		return NAME;
	}
	
	@Override
	public String getName() {		
		return getStaticName();
	}
	
	@Override
	public void doOperation(WebDriver driver, BaseElementDataModelAdapter baseElement, WebElement webElement, ProgressIndicatorInterface elementProgress, String tab, Set<String> definedElementSet, boolean needToPrintSource ) throws ElementInvalidOperationException {
	}
	
	@Override
	public void setXMLAttribute(Document document, Element element) {
		//No parameter, no operation				
	}
	
	@Override
	public Object clone(){
		
		NoneOperation cloned = new NoneOperation();
		
		return cloned;
		
	}
	
	@Override
	public String getOperationNameToString() {		
		return "Nop()";
	}

}
