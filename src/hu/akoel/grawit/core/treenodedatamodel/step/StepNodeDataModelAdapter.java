package hu.akoel.grawit.core.treenodedatamodel.step;

import java.util.Vector;

import javax.swing.tree.MutableTreeNode;

import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantRootDataModel;
import hu.akoel.grawit.enums.Tag;
import hu.akoel.grawit.exceptions.XMLMissingAttributePharseException;
import hu.akoel.grawit.exceptions.XMLPharseException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class StepNodeDataModelAdapter extends StepDataModelAdapter{

	private static final long serialVersionUID = -2466202302741284519L;
	
	private static final String ATTR_DETAILS = "details";
		
	private String name;
	private String details;
	
	public StepNodeDataModelAdapter( String name, String details ){
		super( );
		this.name = name;
		this.details = details;
	}
	
	public StepNodeDataModelAdapter( Element element, BaseRootDataModel baseRootDataModel, ConstantRootDataModel constantRootDataModel ) throws XMLPharseException{
		
		//========
		//
		// Name
		//
		//========
		if( !element.hasAttribute( ATTR_NAME ) ){
			throw new XMLMissingAttributePharseException( StepNodeDataModelAdapter.getRootTag(), Tag.STEPFOLDER, ATTR_NAME );			
		}
		String nameString = element.getAttribute( ATTR_NAME );
		this.name = nameString;

		//========
		//
		// Details
		//
		//========
		String detailsString;
		if( !element.hasAttribute( ATTR_DETAILS ) ){
		//	throw new XMLMissingAttributePharseException( ParamNodeDataModel.getRootTag(), Tag.PARAMNODE, ATTR_NAME, getName(), ATTR_DETAILS );
			detailsString = "";
		}else{		
			detailsString = element.getAttribute( ATTR_DETAILS );
		}
		this.details = detailsString;

/*		
	    //========
		//
		// Gyermekei
		//
	    //========
		NodeList nodelist = element.getChildNodes();
		for( int i = 0; i < nodelist.getLength(); i++ ){
			Node node = nodelist.item( i );
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element pageElement = (Element)node;
				
				//Ha PARAMPAGE van alatta
				if( pageElement.getTagName().equals( Tag.PARAMNORMALELEMENTCOLLECTOR.getName() )){					
					this.add(new ParamNormalCollectorDataModel(pageElement, baseRootDataModel, variableRootDataModel ) );

				//Ha PARAMLOOP van alatta
				}else if( pageElement.getTagName().equals( Tag.PARAMLOOPELEMENTCOLLECTOR.getName() )){					
					this.add(new ParamLoopCollectorDataModel(pageElement, variableRootDataModel, baseRootDataModel ) );
						
				//Ha ujabb PARAMNODE van alatta
				}else if( pageElement.getTagName().equals( Tag.PARAMNODE.getName() )){					
					this.add(new ParamNodeDataModelAdapter(pageElement, baseRootDataModel, variableRootDataModel ) );
				}
			}
		}
*/		
	}

/*	public static Tag getTagStatic(){
		return TAG;
	}
*/	
/*	@Override
	public Tag getTag(){
		return TAG;
		//return getTagStatic();
	}
*/	
	@Override
	public void add(StepDataModelAdapter node) {
		super.add( (MutableTreeNode)node );
	}
/*	
	public static String  getModelNameToShowStatic(){
		return CommonOperations.getTranslation( "tree.nodetype.step.node");
	}
	
	@Override
	public String getNodeTypeToShow(){
		return getModelNameToShowStatic();
	}
*/	
	@Override
	public String getName(){
		return name;
	}
	
	public String getDetails(){
		return details;
	}
	
	public void setDetails( String details ){
		this.details = details;
	}
	
	public void setName( String name ){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}

	@Override
	public Element getXMLElement(Document document) {
		Attr attr;
		
		//Node element
		Element nodeElement = document.createElement( StepNodeDataModelAdapter.this.getTag().getName() );
				
		//========
		//
		// Name
		//
		//========
		attr = document.createAttribute( ATTR_NAME );
		attr.setValue( getName() );
		nodeElement.setAttributeNode(attr);	
		
		//========
		//
		// Details
		//
		//========
		attr = document.createAttribute( ATTR_DETAILS );
		attr = document.createAttribute("details");
		attr.setValue( getDetails() );
		nodeElement.setAttributeNode(attr);	
	
		//========
		//
		// Gyermekei
		//
		//========	
		int childrens = this.getChildCount();
		for( int i = 0; i < childrens; i++ ){
			
			Object object = this.getChildAt( i );
			
			if( !object.equals(this) && object instanceof StepDataModelAdapter ){
				
				Element element = ((StepDataModelAdapter)object).getXMLElement( document );
				nodeElement.appendChild( element );		    		
		    	
			}
		}
			
		return nodeElement;		
	}

	@Override
	public Object clone(){
		
		//Leklonozza a NODE-ot
		StepNodeDataModelAdapter cloned = (StepNodeDataModelAdapter)super.clone();
	
		//Ha vannak gyerekei (NODE vagy PAGE)
		if( null != this.children ){
					
			//Akkor azokat is leklonozza
			cloned.children = new Vector<>();
					
			for( Object o : this.children ){
						
				if( o instanceof StepDataModelAdapter ){
					
					StepDataModelAdapter child = (StepDataModelAdapter) ((StepDataModelAdapter)o).clone();
					
					//Szulo megadasa, mert hogy nem lett hozzaadva direkt modon a Tree-hez
					child.setParent( cloned );					
					
					cloned.children.add(child);
					
				}
			}
		}
				
		//Es a valtozokat is leklonozza
		cloned.name = new String( this.name );
		cloned.details = new String( this.details );		
		
		//Le kell masolni a felmenoit is, egyebkent azok automatikusan null-ok
		cloned.setParent( (MutableTreeNode) this.getParent() );
		
		return cloned;
		
	}

}
