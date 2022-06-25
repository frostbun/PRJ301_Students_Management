
package com.studentmanager.service;

import com.studentmanager.dto.CreateSubmissionDTO;
import com.studentmanager.repository.SubmissionDAO;
import org.springframework.beans.factory.annotation.Autowired;


public class SubmissionService {
    @Autowired
    private SubmissionDAO submissionDAO;
    
    public String createSubmission(CreateSubmissionDTO dto){
        if(submissionDAO.readHomeworkIDByUsername(dto.getHomeworkID(), dto.getUsername()) != null){
            return "Submission existed";
        }
        if(!submissionDAO.create(
                dto.getClass(),
                dto.getHomeworkID(),
                dto.getUsername())){
            return "Something went wrong while creating submission";
        }
        return null;
    }
    
    public String changeSubmissionInformation(int homeworkID, String username, CreateSubmissionDTO dto){
        if(submissionDAO.readHomeworkIDByUsername(dto.getHomeworkID(), dto.getUsername()) == null){
            return "Submission does not exist";
        }
        if(!submissionDAO.updateSubmissionInformation(
                homeworkID,
                username,
                dto.getFileLink(),
                dto.getMark())){
            return "Something went wrong while changing information";
        }
        return null;
    }
    
    
    
    
}
