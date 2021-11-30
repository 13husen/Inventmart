package com.inventmart.model.dto;

public class PurchaseOrderReportDTO {
	
	private String code;
	private String qty;
	private String note;
	private String status;
	private String timestamp;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
	
}
