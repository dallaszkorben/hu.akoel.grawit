package hu.akoel.grawit.core.datamodel.elements;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.IdentificationType;
import hu.akoel.grawit.VariableSample;
import hu.akoel.grawit.core.datamodel.BaseDataModelInterface;

public class BaseElementDataModel extends BaseDataModelInterface{
	private static final long serialVersionUID = -8916078747948054716L;

	private String name;
	private VariableSample variableSample;
	private String frame;
	private String identifier;
	private IdentificationType identificationType;


	public BaseElementDataModel(String name, String identifier, IdentificationType identificationType, VariableSample variableSample, String frame){
		common( name, identifier, identificationType, variableSample, frame );	
	}

	public BaseElementDataModel( String name, String identifier, IdentificationType identificationType, VariableSample variableSample ){
		common( name, identifier, identificationType, variableSample, null );
	}

	public BaseElementDataModel( BaseElementDataModel element ){
		this.name = element.getName();
		this.identifier = getIdentifier();
		this.identificationType = getIdentificationType();
		this.variableSample = element.getVariableSample();
	}

	private void common( String name, String identifier, IdentificationType identificationType, VariableSample variableSample, String frame ){		
		this.name = name;
		this.identifier = identifier;
		this.identificationType = identificationType;
		this.variableSample = variableSample;
		this.frame = frame;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public IdentificationType getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(IdentificationType identificationType) {
		this.identificationType = identificationType;
	}

	public VariableSample getVariableSample() {
		return variableSample;
	}

	public void setVariableSample(VariableSample variableSample) {
		this.variableSample = variableSample;
	}

	public String getFrame(){
		return frame;
	}
	
	public void setFrame( String frame ){
		this.frame = frame;
	}
	
	@Override
	public void add(BaseDataModelInterface node) {
		super.add( (MutableTreeNode)node );
	}
	
	public String getNameToString(){
		return getName();
	}
	
	public String getTypeToString(){
		return CommonOperations.getTranslation( "tree.nodetype.baseelement");
	}
	
	@Override
	public String getPathToString() {		
		StringBuffer out = new StringBuffer();
		for( TreeNode node: this.getPath() ){
			out.append( ((BaseDataModelInterface)node).getNameToString() );
			out.append("/");
		}		
		return out.toString();
	}	
	

	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
//System.err.println("   Elem");		
		//Node element
		Element elementElement = document.createElement("element");
		attr = document.createAttribute("name");
		attr.setValue( getName() );
		elementElement.setAttributeNode(attr);	
		
		attr = document.createAttribute("frame");
		attr.setValue( getFrame() );
		elementElement.setAttributeNode(attr);	

		attr = document.createAttribute("variablesemple");
		attr.setValue( getVariableSample().name() );
		elementElement.setAttributeNode(attr);
		
		attr = document.createAttribute("identifier");
		attr.setValue( getIdentifier() );
		elementElement.setAttributeNode(attr);	

		attr = document.createAttribute("identificationType");
		attr.setValue( getIdentificationType().name() );
		elementElement.setAttributeNode(attr);	

		return elementElement;	
	}
}