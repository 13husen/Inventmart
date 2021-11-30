package com.inventmart.model.dto;

public class InvoiceReportDTO {
	
	private String code;
	private String poCode;
	private String terms;
	private String note;
	private String timestamp;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPoCode() {
            return poCode;
        }

        public void setPoCode(String poCode) {
            this.poCode = poCode;
        }

        public String getTerms() {
            return terms;
        }

        public void setTerms(String terms) {
            this.terms = terms;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
	
	
}
