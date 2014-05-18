package edu.citybike.model.view;

import edu.citybike.model.Fee;

public class FeeManagerView {
	
	private Fee fee;
	private boolean checked;
	private boolean newRow;

	public FeeManagerView(Fee fee){
		this.fee = fee;
		checked = false;
		newRow = false;
	}

	public FeeManagerView(Fee fee, boolean checked, boolean newRow) {
		this.fee = fee;
		this.checked = checked;
		this.newRow = newRow;
	}

	public Fee getFee() {
		return fee;
	}

	public void setFee(Fee fee) {
		this.fee = fee;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isNewRow() {
		return newRow;
	}

	public void setNewRow(boolean newRow) {
		this.newRow = newRow;
	}
}
