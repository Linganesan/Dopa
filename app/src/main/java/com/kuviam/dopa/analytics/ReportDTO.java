package com.kuviam.dopa.analytics;

public class ReportDTO {

	String rule;

	public ReportDTO() {

	}

	public ReportDTO(String rule) {
		this.rule = rule;
	}

	@Override
	public String toString() {
		return "ReportDTO [rule=" + rule + "]";
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}
}
