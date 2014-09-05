package hu.akoel.grawit.gui.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultTreeModel;

import hu.akoel.grawit.CommonOperations;
import hu.akoel.grawit.core.treenodedatamodel.DataModelInterface;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverExplorerDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverFirefoxDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverNodeDataModel;
import hu.akoel.grawit.core.treenodedatamodel.driver.DriverRootDataModel;
import hu.akoel.grawit.enums.ActionCommand;
import hu.akoel.grawit.gui.GUIFrame;
import hu.akoel.grawit.gui.editor.DataEditor;
import hu.akoel.grawit.gui.editor.DataEditor.EditMode;
import hu.akoel.grawit.gui.editor.driver.DriverExplorerEditor;
import hu.akoel.grawit.gui.editor.driver.DriverFirefoxEditor;
import hu.akoel.grawit.gui.editor.driver.DriverNodeEditor;
import hu.akoel.grawit.gui.editor.EmptyEditor;

public class DriverTree extends Tree{

	private static final long serialVersionUID = -2576284128681697627L;
	
	private GUIFrame guiFrame;
	
	public DriverTree(GUIFrame guiFrame, DriverRootDataModel rootDataModel) {
		super(guiFrame, rootDataModel);
		this.guiFrame = guiFrame;
	}

	@Override
	public ImageIcon getIcon(DataModelInterface actualNode, boolean expanded) {

    	ImageIcon nodeIcon = CommonOperations.createImageIcon("tree/driver-root-open-icon.png");
    	ImageIcon explorerIcon = CommonOperations.createImageIcon("tree/driver-explorer-icon.png");
    	ImageIcon firefoxIcon = CommonOperations.createImageIcon("tree/driver-firefox-icon.png");
    	ImageIcon nodeClosedIcon = CommonOperations.createImageIcon("tree/driver-node-closed-icon.png");
    	ImageIcon nodeOpenIcon = CommonOperations.createImageIcon("tree/driver-node-open-icon.png");
  
    	//Iconja a NODE-nak
    	if( actualNode instanceof DriverRootDataModel){
            return nodeIcon;
    	}else if( actualNode instanceof DriverExplorerDataModel ){
            return explorerIcon;
    	}else if( actualNode instanceof DriverFirefoxDataModel ){
    		return firefoxIcon;
    	}else if( actualNode instanceof DriverNodeDataModel){
    		if( expanded ){
    			return nodeOpenIcon;
    		}else{
    			return nodeClosedIcon;
    		}
    	}
    	
    	return null;
	}

	@Override
	public void doViewWhenSelectionChanged(DataModelInterface selectedNode) {
	
		//Ha egyaltalan valamilyen egergombot benyomtam
		if( selectedNode instanceof DriverRootDataModel ){
			EmptyEditor emptyPanel = new EmptyEditor();								
			guiFrame.showEditorPanel( emptyPanel );
		
		}else if( selectedNode instanceof DriverNodeDataModel ){
			DriverNodeEditor driverNodeEditor = new DriverNodeEditor(this, (DriverNodeDataModel)selectedNode, EditMode.VIEW);
			guiFrame.showEditorPanel( driverNodeEditor);								
		
		}else if( selectedNode instanceof DriverExplorerDataModel ){
			DriverExplorerEditor driverExplorerEditor = new DriverExplorerEditor( this, (DriverExplorerDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( driverExplorerEditor);				
						
		}else if( selectedNode instanceof DriverFirefoxDataModel ){
			DriverFirefoxEditor driverFirefoxEditor = new DriverFirefoxEditor( this, (DriverFirefoxDataModel)selectedNode, EditMode.VIEW );								
			guiFrame.showEditorPanel( driverFirefoxEditor);		
								
		}			
	}

	@Override
	public void doModifyWithPopupEdit(DataModelInterface selectedNode) {
		
		if( selectedNode instanceof DriverNodeDataModel ){
							
			DriverNodeEditor driverNodeEditor = new DriverNodeEditor( this, (DriverNodeDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( driverNodeEditor);								
								
		}else if( selectedNode instanceof DriverExplorerDataModel ){
								
			DriverExplorerEditor driverExplorerEditor = new DriverExplorerEditor( this, (DriverExplorerDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( driverExplorerEditor);		
								
		}else if( selectedNode instanceof DriverFirefoxDataModel ){

			DriverFirefoxEditor driverFirefoxEditor = new DriverFirefoxEditor( this, (DriverFirefoxDataModel)selectedNode, DataEditor.EditMode.MODIFY );								
			guiFrame.showEditorPanel( driverFirefoxEditor);		
								
		}	
	}

	@Override
	public void doPopupInsert( final JPopupMenu popupMenu, final DataModelInterface selectedNode) {

		//
		// Csomopont eseten
		//
		if( selectedNode instanceof DriverNodeDataModel ){

			//Insert Node
			JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.node") );
			insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertNodeMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					DriverNodeEditor driverNodeEditor = new DriverNodeEditor( DriverTree.this, (DriverNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( driverNodeEditor);								
				
				}
			});
			popupMenu.add ( insertNodeMenu );

			//Insert Explorer
			JMenuItem insertExplorerMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.driver.explorer") );
			insertExplorerMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertExplorerMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					DriverExplorerEditor driverExplorerEditor = new DriverExplorerEditor( DriverTree.this, (DriverNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( driverExplorerEditor);								
				
				}
			});
			popupMenu.add ( insertExplorerMenu );
			
			//Insert Firefox
			JMenuItem insertFirefoxMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.driver.firefox") );
			insertFirefoxMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertFirefoxMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					DriverFirefoxEditor baseNodeEditor = new DriverFirefoxEditor( DriverTree.this, (DriverNodeDataModel)selectedNode );								
					guiFrame.showEditorPanel( baseNodeEditor);								
				
				}
			});
			popupMenu.add ( insertFirefoxMenu );
		}		
		
/*		
		//
		// Page eseten
		//
		
		if( selectedNode instanceof BasePageDataModel ){

			//Insert Element
			JMenuItem insertElementMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.base.element") );
			insertElementMenu.setActionCommand( ActionCommand.CAPTURE.name());
			insertElementMenu.addActionListener( new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					BaseElementEditor baseNodeEditor = new BaseElementEditor( DriverTree.this, (BasePageDataModel)selectedNode );								
					guiFrame.showEditorPanel( baseNodeEditor);								
				
				}
			});
			popupMenu.add ( insertElementMenu );
		
		}
*/	
		
	}

	@Override
	public void doPopupDelete( final JPopupMenu popupMenu, final DataModelInterface selectedNode, final int selectedRow, final DefaultTreeModel totalTreeModel ) {
	
		// Torles
		// Ha nincs alatta ujabb elem
		//
		if( selectedNode.getChildCount() == 0 ){
			
		
			JMenuItem deleteMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.delete") );
			deleteMenu.setActionCommand( ActionCommand.UP.name());
			deleteMenu.addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {

					//Megerosito kerdes
					Object[] options = {
							CommonOperations.getTranslation("button.no"),
							CommonOperations.getTranslation("button.yes")								
					};
					
					int n = JOptionPane.showOptionDialog(guiFrame,
							"Valóban torolni kívánod a(z) " + selectedNode.getTag().getName() + " nevü " + selectedNode.getModelNameToShow() + "-t ?",
							CommonOperations.getTranslation("editor.windowtitle.confirmation.delete"),
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							options,
							options[0]);

					if( n == 1 ){
						totalTreeModel.removeNodeFromParent( selectedNode);
						DriverTree.this.setSelectionRow(selectedRow - 1);
					}							
				}
			});
			popupMenu.add ( deleteMenu );
			
		}	
		
	}

	@Override
	public void doPopupRootInsert( JPopupMenu popupMenu, final DataModelInterface selectedNode ) {

		//Insert Node
		JMenuItem insertNodeMenu = new JMenuItem( CommonOperations.getTranslation( "tree.popupmenu.insert.node") );
		insertNodeMenu.setActionCommand( ActionCommand.CAPTURE.name());
		insertNodeMenu.addActionListener( new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
					
				DriverNodeEditor driverNodeEditor = new DriverNodeEditor( DriverTree.this, (DriverNodeDataModel)selectedNode );								
				guiFrame.showEditorPanel( driverNodeEditor);								
			
			}
		});
		popupMenu.add ( insertNodeMenu );			
		
	}

}