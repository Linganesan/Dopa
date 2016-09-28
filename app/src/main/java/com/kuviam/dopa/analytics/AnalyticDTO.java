package com.kuviam.dopa.analytics;

public class AnalyticDTO {

	String disciplineName;
	String locusName;
	long assignedRecallTime;
	long assignedPracticeTime;
	int noOfItems;

	// boolean status;
	// Date timeStamp = null;
	// long practiceTime;
	// long recallTime;

	public AnalyticDTO() {

	}

	public AnalyticDTO(String disciplineName, String locusName,
			long assignedRecallTime, long assignedPracticeTime,
			// long practiceTime,
			// long recallTime,

			// boolean status,
			// Date timeStamp,
			int noOfItems) {
		super();
		this.disciplineName = disciplineName;
		this.locusName = locusName;
		this.assignedRecallTime = assignedRecallTime;
		this.assignedPracticeTime = assignedPracticeTime;
		// this.practiceTime = practiceTime;
		// this.recallTime = recallTime;
		this.noOfItems = noOfItems;
		// this.status = status;
		// this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return "AnalyticsDTO [disciplineName=" + disciplineName + ",locusName="
				+ locusName + ", assignedRecallTime=" + assignedRecallTime
				+ ", assignedPracticeTime=" + assignedPracticeTime +
				// ", practiceTime=" + practiceTime + ",recallTime=" +
				// recallTime +
				", noOfItems=" + noOfItems +
				// ", status=" + status + ", timeStamp=" + timeStamp +
				"]";
	}

	public String getDisciplineName() {
		return disciplineName;
	}

	public void setDisciplineName(String disciplineName) {
		this.disciplineName = disciplineName;
	}

	public String getLocusName() {
		return locusName;
	}

	public void setLocusName(String locusName) {
		this.locusName = locusName;
	}

	public long getAssignedRecallTime() {
		return assignedRecallTime;
	}

	public void setAssignedRecallTime(long assignedRecallTime) {
		this.assignedRecallTime = assignedRecallTime;
	}

	public long getAssignedPracticeTime() {
		return assignedPracticeTime;
	}

	public void setAssignedPracticeTime(long assignedPracticeTime) {
		this.assignedPracticeTime = assignedPracticeTime;
	}

	public int getNoOfItems() {
		return noOfItems;
	}

	public void setNoOfItems(int noOfItems) {
		this.noOfItems = noOfItems;
	}

}
