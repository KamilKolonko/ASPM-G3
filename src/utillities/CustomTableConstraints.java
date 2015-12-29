package utillities;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class CustomTableConstraints extends GridBagConstraints {
    
    private static final long serialVersionUID = 1L;

    public CustomTableConstraints(int gridx, int gridy, int anchor) {
	super();
	this.gridx = gridx;
	this.gridy = gridy;
	this.anchor = anchor;
	this.insets = new Insets(0, 0, 5, 0);
	this.fill = GridBagConstraints.NONE;
    }
    
    public CustomTableConstraints(int gridx, int gridy, int anchor, int fill) {
	super();
	this.gridx = gridx;
	this.gridy = gridy;
	this.anchor = anchor;
	this.insets = new Insets(0, 0, 5, 0);
	this.fill = fill;
    }
}
