
package com.studentmanager.service;
import com.studentmanager.dto.CreateHomeworkDTO;
import com.studentmanager.repository.HomeworkDAO;
import org.springframework.beans.factory.annotation.Autowired;

public class HomeworkService {
    @Autowired
    private HomeworkDAO homeworkDAO;
    
    public String createHomework(CreateHomeworkDTO dto){
        if(HomeworkDAO.readHomeworkByID(dto.getHomeworkID()) != null){
            return "Homework existed";
        }
        if(!homeworkDAO.create(
                dto.getHomeworkID(),
                dto.getClassID(),
                dto.getCreator(),
                dto.getFileLink(),
                dto.getDecription())){
            return "Something went wrong while creating homework";
        }
        return null;
    }
    
    public String changeHomeworkInformation(int homeworkID, CreateHomeworkDTO dto){
        if(HomeworkDAO.readHomeworkByID(dto.getHomeworkID()) == null){
            return "Homework does not exist";
        }
        if(!homeworkDAO.updateHomeworkInformation(
                homeworkID,
                dto.getClassID(),
                //creator
                dto.getFileLink(),
                dto.getDecription(),
                dto.getDeadline())){
            return "Something went wrong while update information";
        }
        return null;
    }
    

}
