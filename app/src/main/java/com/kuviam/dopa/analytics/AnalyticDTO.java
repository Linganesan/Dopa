package com.kuviam.dopa.analytics;

import java.util.Date;

/**
 * Created by linganesan on 9/1/15.
 */
class AnalyticsDTO {

    String disciplineName;
    String locusName;
    long assignedRecallTime;
    long assignedPracticeTime;
   // long practiceTime;
   // long recallTime;
    int noOfItems;
   // boolean status;
   // Date timeStamp = null;

    public AnalyticsDTO(String disciplineName,
                       String locusName,
                       long assignedRecallTime,
                       long assignedPracticeTime,
                      // long practiceTime,
                      // long recallTime,

                      // boolean status,
                      // Date timeStamp,
                       int noOfItems
                      ) {
        super();
        this.disciplineName = disciplineName;
        this.locusName = locusName;
        this.assignedRecallTime = assignedRecallTime;
        this.assignedPracticeTime = assignedPracticeTime;
      //  this.practiceTime = practiceTime;
      //  this.recallTime = recallTime;
        this.noOfItems = noOfItems;
      //  this.status = status;
      //  this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "AnalyticsDTO [disciplineName=" + disciplineName + ",locusName=" + locusName + ", assignedRecallTime="
                + assignedRecallTime + ", assignedPracticeTime=" + assignedPracticeTime +
               // ", practiceTime=" + practiceTime + ",recallTime=" + recallTime +
                ", noOfItems="  + noOfItems +
                //", status=" + status + ", timeStamp=" + timeStamp +
                "]";
    }


}
