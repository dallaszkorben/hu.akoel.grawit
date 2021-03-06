package hu.akoel.grawit.core.treenodedatamodel.base;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BaseRootDataModel extends BaseNodeDataModelAdapter{

	private static final long serialVersionUID = 5361088361756620748L;

	private static final Tag TAG = Tag.BASEROOT;
	
	public static final String ATTR_NAME = "";
	
	public BaseRootDataModel(){
		super( "", "" );
	}
	
	public BaseRootDataModel( Document doc ) throws XMLPharseException{
		super("","");
		
		NodeList nList = doc.getElementsByTagName( TAG.getName() );
		
		//Ha pontosan 1 db root van definialva
		if( nList.getLength() == 1 ){
		
			Node basePageNode = nList.item(0);
			if (basePageNode.getNodeType() == Node.ELEMENT_NODE) {
			
				NodeList nodeList = basePageNode.getChildNodes();
				for( int i = 0; i < nodeList.getLength(); i++ ){
			
					Node baseNode = nodeList.item( i );
				
					if (baseNode.getNodeType() == Node.ELEMENT_NODE) {
						Element baseElement = (Element)baseNode;
					
						//Ha ujabb BASEFOLDER van alatta
						if( baseElement.getTagName().equals( Tag.BASEFOLDER.getName() ) ){
							this.add(new BaseFolderDataModel(baseElement));
						}
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
		//return "/";
		//return "Base Root";
		return CommonOperations.getTranslation( "tree.nodetype.base.root.name");
	}
	
	@Override
	public String getNodeTypeToShow(){
		return CommonOperations.getTranslation( "tree.nodetype.base.root");
	}
	
	@Override
	public Element getXMLElement(Document document) {
		
		//BaseElement
		Element baseElement = document.createElement( TAG.getName() );

		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof BaseDataModelAdapter ){
				
				Element element = ((BaseDataModelAdapter)object).getXMLElement( document );
				baseElement.appendChild( element );		    		
		    	
			}
		}

		return baseElement;		
	}
	
}
