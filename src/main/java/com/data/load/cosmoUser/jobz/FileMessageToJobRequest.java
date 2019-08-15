package com.data.load.cosmoUser.jobz;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileMessageToJobRequest {
    private Job job;
    private String fileParameterName;

    public void setFileParameterName(String fileParameterName) {
        this.fileParameterName = fileParameterName;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Transformer
    public JobLaunchRequest toRequest(Message<File> message) {
        JobParametersBuilder jobParametersBuilder =
                new JobParametersBuilder();

        jobParametersBuilder.addString(fileParameterName, moveFileToProcessing(message.getPayload()));

        return new JobLaunchRequest(job, jobParametersBuilder.toJobParameters());
    }

    private String moveFileToProcessing(File file) {
        String newpaths = null;
        try {
            Path newPath = Files.move(Paths.get(file.getAbsolutePath()), Paths.get("dropfolder/processing/" + file.getName()), StandardCopyOption.REPLACE_EXISTING);
            newpaths = newPath.toAbsolutePath().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newpaths;
    }
}

