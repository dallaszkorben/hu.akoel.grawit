package hu.akoel.grawit.gui.editors.component.elementtype.full;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.ListRenderer;
import hu.akoel.grawit.core.operation.interfaces.ElementOperationAdapter;
import hu.akoel.grawit.core.operations.ClickLeftOperation;
import hu.akoel.grawit.core.operations.ScriptElementAddStoreToParametersOperation;
import hu.akoel.grawit.core.operations.ScriptElementAddStringToParametersOperation;
import hu.akoel.grawit.core.operations.ScriptElementAddConstantToParametersOperation;
import hu.akoel.grawit.core.operations.ScriptElementClearParametersOperation;
import hu.akoel.grawit.core.operations.ScriptElementExecuteOperation;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseElementDataModelAdapter;
import hu.akoel.grawit.core.treenodedatamodel.base.BaseRootDataModel;
import hu.akoel.grawit.core.treenodedatamodel.constant.ConstantRootDataModel;
import hu.akoel.grawit.enums.list.ElementTypeListEnum;
import hu.akoel.grawit.enums.list.elementtypeoperations.full.ScriptElementTypeOperationsFullListEnum;
import hu.akoel.grawit.gui.editors.component.treeselector.BaseElementTreeSelectorComponent;
import hu.akoel.grawit.gui.editors.component.treeselector.ConstantTreeSelectorComponent;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ScriptElementTypeComponentFull<E extends ScriptElementTypeOperationsFullListEnum> extends ElementTypeComponentFullInterface<E>{

	private static final long serialVersionUID = -6108131072338954554L;
	
	//Type
	private JLabel labelType;
	private JTextField fieldType;
	
	//Operation
	private JLabel labelOperations;	
	private JComboBox<E> comboOperationList;	
	
	//Constant selector - Mezo kitoltes
	private JLabel labelConstantSelector;
	private ConstantTreeSelectorComponent fieldConstantSelector;
	
	//BaseElement selector - Mezo kitoltes
	private JLabel labelBaseElementSelector;
	private BaseElementTreeSelectorComponent fieldBaseElementSelector;
	
	//String - Mezo kitoltes
	private JLabel labelString;
	private JTextField fieldString;
	
	private JLabel labelFiller;
	
	public ScriptElementTypeComponentFull( BaseElementDataModelAdapter baseElement, ElementOperationAdapter elementOperation, BaseRootDataModel baseRootDataModel, ConstantRootDataModel constantRootDataModel ){
		
		super();
		
		common( baseElement, elementOperation, baseRootDataModel, constantRootDataModel );		
		
	}
	
	@SuppressWarnings("unchecked")
	private void common( BaseElementDataModelAdapter baseElement , ElementOperationAdapter elementOperation, BaseRootDataModel baseRootDataModel, ConstantRootDataModel constantRootDataModel ){
		
		ElementTypeListEnum elementType = baseElement.getElementType();
		
		labelType = new JLabel( CommonOperations.getTranslation("editor.label.step.type") + ": ");
		labelOperations = new JLabel( CommonOperations.getTranslation("editor.label.step.operation") + ": ");
		labelString = new JLabel( CommonOperations.getTranslation("editor.label.step.string") + ": ");
		labelConstantSelector = new JLabel( CommonOperations.getTranslation("editor.label.step.constant") + ": ");
		labelBaseElementSelector = new JLabel( CommonOperations.getTranslation("editor.label.step.baseelement") + ": ");
		labelFiller = new JLabel();
		
		fieldType = new JTextField( elementType.getTranslatedName() );
		fieldType.setEditable(false);

		//OPERATION
		comboOperationList = new JComboBox<>();
		for( int i = 0; i < E.getSize(); i++ ){
			comboOperationList.addItem( (E) E.getOperationByIndex(i) );
		}		
		comboOperationList.addItemListener( new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {

				int index = comboOperationList.getSelectedIndex();				

				//Ha megvaltoztattam a tipust
				if( e.getStateChange() == java.awt.event.ItemEvent.SELECTED ){ 

					setValueContainer( comboOperationList.getItemAt(index));
					
				}				
			}
		});			
				
		//Azert kell, hogy a setEditable() hatasara ne szurkuljon el a felirat
		//comboOperationList.setRenderer(new ElementTypeComponentRenderer());
		comboOperationList.setRenderer(new ListRenderer<E>());

		this.setLayout( new GridBagLayout() );
		
		GridBagConstraints c = new GridBagConstraints();		
		c.insets = new Insets(0,0,0,0);

		//Type
		c.gridy = 0;
		c.gridx = 0;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelType, c );
		
		c.gridx = 1;
		c.weightx = 0;
		this.add( fieldType, c );
		
		//Operation
		c.gridy = 0;
		c.gridx = 2;
		c.gridwidth = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		this.add( labelOperations, c );
		
		c.gridx = 3;
		c.weightx = 0;
		this.add( comboOperationList, c );

		//Kenyszeritem, hogy a kovetkezo setSelectedItem() hatasara vegrehajtsa a az itemStateChanged() metodust
		comboOperationList.setSelectedIndex(-1);
		
		//Valtozok letrehozase
		fieldConstantSelector = new ConstantTreeSelectorComponent( constantRootDataModel );
		fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel );
		fieldString = new JTextField( "" );
		
		//Kezdo ertek beallitasa
		if( null == elementOperation ){
			
			comboOperationList.setSelectedIndex(E.EXECUTE_SCRIPT.getIndex());
			
		}else{
			
			//!!!Fontos a beallitasok sorrendje!!!
			
			//CLEAR PARAMETERS
			if( elementOperation instanceof ClickLeftOperation  ){
				
				comboOperationList.setSelectedIndex(E.CLEAR_PARAMETERS.getIndex());
							
			//ADD_CONSTANT_TO_PARAMETERS
			}else if( elementOperation instanceof ScriptElementAddConstantToParametersOperation ){
								
				fieldConstantSelector = new ConstantTreeSelectorComponent( constantRootDataModel, ((ScriptElementAddConstantToParametersOperation)elementOperation).getConstantElement() );
				comboOperationList.setSelectedIndex(E.ADD_CONSTANT_TO_PARAMETERS.getIndex());

			//ADD_STORED_TO_PARAMETERS
			}else if( elementOperation instanceof ScriptElementAddStoreToParametersOperation ){
								
				fieldBaseElementSelector = new BaseElementTreeSelectorComponent( baseRootDataModel, ((ScriptElementAddStoreToParametersOperation)elementOperation).getBaseElement() );
				comboOperationList.setSelectedIndex(E.ADD_STORED_TO_PARAMETERS.getIndex());

			//ADD_STRING_TO_PARAMETERS
			}else if( elementOperation instanceof ScriptElementAddStringToParametersOperation ){
								
				fieldString.setText( ((ScriptElementAddStringToParametersOperation)elementOperation).getStringToShow() );
				comboOperationList.setSelectedIndex(E.ADD_STRING_TO_PARAMETERS.getIndex());
				
			//EXECUTE_SCRIPT
			}else if( elementOperation instanceof ScriptElementExecuteOperation ){
				
				comboOperationList.setSelectedIndex(E.EXECUTE_SCRIPT.getIndex());
				
			//Ha megvaltozott az alapElem es kulonbozik a tipusa
			}else{
				
				comboOperationList.setSelectedIndex(E.CLEAR_PARAMETERS.getIndex());
				
			}
		}		
		
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public E getSelectedOperation(ElementTypeListEnum elementType) {
		return(E)comboOperationList.getSelectedItem();
	}
	
	@Override
	public void setEnableModify(boolean enable) {
		
		comboOperationList.setEnabled( enable );		
		fieldString.setEditable( enable );
		fieldBaseElementSelector.setEnableModify(enable);		
		fieldConstantSelector.setEnableModify( enable );

	}

	@Override
	public Component getComponent() {
		return this;
	}

	private void setValueContainer( E selectedOperation ){

		GridBagConstraints c = new GridBagConstraints();		
		c.insets = this.getInsets(); //new Insets(0,0,0,0);		
		
		this.remove( labelBaseElementSelector );
		this.remove( fieldBaseElementSelector );
		this.remove( labelString );
		this.remove( fieldString );
		this.remove( labelConstantSelector );
		this.remove( fieldConstantSelector );	
		this.remove( labelFiller );
		
		//Fill element / Compare value to element
		if( selectedOperation.equals( E.ADD_STORED_TO_PARAMETERS ) ){
			
			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.anchor = GridBagConstraints.WEST;
			this.add( labelBaseElementSelector, c );
		
			c.gridx = 5;
			c.weightx = 1;
			this.add( fieldBaseElementSelector, c );
			
		//Fill constant / Compare value to constant
		}else if( selectedOperation.equals( E.ADD_CONSTANT_TO_PARAMETERS ) ){
			
			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.anchor = GridBagConstraints.WEST;
			this.add( labelConstantSelector, c );
		
			c.gridx = 5;
			c.weightx = 1;
			this.add( fieldConstantSelector, c );
			
		//Fill string / Compare value to string
		}else if( selectedOperation.equals( E.ADD_STRING_TO_PARAMETERS ) ){
		
			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.anchor = GridBagConstraints.WEST;
			this.add( labelString, c );
		
			c.gridx = 5;
			c.weightx = 1;
			this.add( fieldString, c );
			
		//CLEAR_PARAMETERS || EXECUTE_SCRIPT
		}else if( selectedOperation.equals( E.CLEAR_PARAMETERS) || selectedOperation.equals( E.EXECUTE_SCRIPT) ){
			
			//Filler
			c.gridy = 0;
			c.gridx = 4;
			c.gridwidth = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			c.anchor = GridBagConstraints.WEST;
			this.add( labelFiller, c );
			
		}

		this.revalidate();
		this.repaint();
	}

	@Override
	public ElementOperationAdapter getElementOperation() {
		
		//ADD_STORED_TO_PARAMETERS
		if( comboOperationList.getSelectedIndex() ==  E.ADD_STORED_TO_PARAMETERS.getIndex() ){
			return new ScriptElementAddStoreToParametersOperation( fieldBaseElementSelector.getSelectedDataModel() );
			
		//ADD_CONSTANT_TO_PARAMETERS
		}else if(comboOperationList.getSelectedIndex() ==  E.ADD_CONSTANT_TO_PARAMETERS.getIndex() ){
			return new ScriptElementAddConstantToParametersOperation( fieldConstantSelector.getSelectedDataModel() );
			
		//ADD_STRING_TO_PARAMETERS
		}else if( comboOperationList.getSelectedIndex() ==  E.ADD_STRING_TO_PARAMETERS.getIndex() ){
			return new ScriptElementAddStringToParametersOperation( fieldString.getText() );
			
		//CLEAR_PARAMETERS
		}else if( comboOperationList.getSelectedIndex() ==  E.CLEAR_PARAMETERS.getIndex() ){
			return new ScriptElementClearParametersOperation();
			
		//EXECUTE_SCRIPT
		}else if( comboOperationList.getSelectedIndex() ==  E.EXECUTE_SCRIPT.getIndex() ){
			return new ScriptElementExecuteOperation();

		}
	
		return null;
	}
	
}
