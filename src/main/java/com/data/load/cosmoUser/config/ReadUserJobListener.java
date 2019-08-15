package com.data.load.cosmoUser.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ReadUserJobListener implements JobExecutionListener {
    private Logger LOG = LogManager.getLogger(ReadUserJobListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String filePath = (String) jobExecution.getJobParameters().getParameters().get("file_path").getValue();
        try {
            Path moved = Files.move(Paths.get(filePath), Paths.get("dropfolder/archive/" + filePath.split("/")[filePath.split("/").length - 1]), StandardCopyOption.REPLACE_EXISTING);
            LOG.info("File " + filePath + " moved to " + moved.toAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
