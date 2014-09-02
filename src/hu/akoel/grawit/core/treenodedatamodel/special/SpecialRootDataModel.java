package hu.akoel.grawit.core.treenodedatamodel.special;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.SpecialDataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseNodeDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLExtraRootTagPharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SpecialRootDataModel extends SpecialNodeDataModel{

	private static final long serialVersionUID = 5361088361756620748L;

	private static final Tag TAG = Tag.SPECIALROOT;
	
	public static final String ATTR_NAME = "";
	
	public SpecialRootDataModel(){
		super( "", "" );
	}
	
	public SpecialRootDataModel( Document doc ) throws XMLPharseException{
		super("","");
		
		NodeList nList = doc.getElementsByTagName( TAG.getName() );
		
		//Ha nem pontosan 1 db basepage tag van, akkor az gaz
		if( nList.getLength() != 1 ){
			
			throw new XMLExtraRootTagPharseException( TAG );
		}
		
		Node specialNode = nList.item(0);
		if (specialNode.getNodeType() == Node.ELEMENT_NODE) {
			
			NodeList nodeList = specialNode.getChildNodes();
			for( int i = 0; i < nodeList.getLength(); i++ ){
			
				Node baseNode = nodeList.item( i );
				
				if (baseNode.getNodeType() == Node.ELEMENT_NODE) {
					Element baseElement = (Element)baseNode;
					
					//Ha ujabb SPECIALNODE van alatta
					if( baseElement.getTagName().equals( Tag.SPECIALNODE.getName() ) ){
						this.add(new SpecialNodeDataModel(baseElement));
					}
				}
			}
		}		
	}
	
	public static Tag getTagStatic(){
		return TAG;
	}

	@Override
	public Tag getTag() {
		return getTagStatic();
	}

	@Override
	public String getName(){
		return "Special Root";
	}
	
	@Override
	public String getModelNameToShow(){
		return CommonOperations.getTranslation( "tree.nodetype.root");
	}
	
	@Override
	public Element getXMLElement(Document document) {
		
		//SpecialElement
		Element specialElement = document.createElement( TAG.getName() );

		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof SpecialDataModelInterface ){
				
				Element element = ((SpecialDataModelInterface)object).getXMLElement( document );
				specialElement.appendChild( element );		    		
		    	
			}
		}

		return specialElement;		
	}
	
}