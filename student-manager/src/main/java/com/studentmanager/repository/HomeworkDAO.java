
package com.studentmanager.repository;

import com.studentmanager.model.Homework;
import java.time.LocalDateTime;

public class HomeworkDAO {

    public static Object readHomeworkByID(int homeworkID) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public boolean create(int homeworkID, int classID, String creator, String fileLink, String decription) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean updateHomeworkInformation(int homeworkID, int classID, String fileLink, String decription, LocalDateTime deadline) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    
}
