package hu.akoel.grawit.core.treenodedatamodel.run;

import hu.akoel.grawit.core.treenodedatamodel.DataModelAdapter;
import hu.akoel.grawit.enums.Tag;


public abstract class RunDataModelAdapter extends DataModelAdapter{

	private static final long serialVersionUID = 962315514644510157L;

	public static Tag getRootTag(){
		return Tag.RUNROOT;
	}
}
