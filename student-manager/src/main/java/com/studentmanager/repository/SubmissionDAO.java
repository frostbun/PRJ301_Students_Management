
package com.studentmanager.repository;

import com.studentmanager.dto.CreateSubmissionDTO;
import com.studentmanager.model.Submission;


public class SubmissionDAO {

    public boolean readByHomeworkID;

    public boolean create(Submission _submission) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object readHomeworkIDByUsername(int homeworkID, String username) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public boolean create(Class<? extends CreateSubmissionDTO> aClass, int homeworkID, String username) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public boolean updateSubmissionInformation(int homeworkID, String username, String fileLink, int mark) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }



    
}
