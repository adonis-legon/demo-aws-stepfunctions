package com.example.demoactivitylogger;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.model.GetActivityTaskRequest;
import com.amazonaws.services.stepfunctions.model.GetActivityTaskResult;
import com.amazonaws.services.stepfunctions.model.SendTaskFailureRequest;
import com.amazonaws.services.stepfunctions.model.SendTaskSuccessRequest;
import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WorkflowLogger {
    @Value("${app.aws.region}")
    private String awsRegion;

    @Value("${app.aws.step-functions.activity-arn}")
    private String activityArn;

    private AWSStepFunctions client;

    @Scheduled(fixedDelayString = "${app.poll-interval}")
    public void processTasks() throws InterruptedException {
        while (true) {
            GetActivityTaskResult getActivityTaskResult = client
                    .getActivityTask(new GetActivityTaskRequest().withActivityArn(activityArn));

            if (getActivityTaskResult.getTaskToken() != null) {
                try {
                    JsonNode json = Jackson.jsonNodeOf(getActivityTaskResult.getInput());

                    processTask(json);

                    client.sendTaskSuccess(new SendTaskSuccessRequest().withOutput(json.toString())
                            .withTaskToken(getActivityTaskResult.getTaskToken()));
                } catch (Exception e) {
                    client.sendTaskFailure(
                            new SendTaskFailureRequest().withTaskToken(getActivityTaskResult.getTaskToken()));
                }
            } else {
                Thread.sleep(100);
            }
        }
    }

    private void processTask(JsonNode taskInput) {
        log.info(taskInput.toPrettyString());
    }

    @PostConstruct
    private void init() {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setSocketTimeout((int) TimeUnit.SECONDS.toMillis(70));

        this.client = AWSStepFunctionsClientBuilder.standard().withRegion(Regions.fromName(awsRegion))
                .withCredentials(new EnvironmentVariableCredentialsProvider())
                .withClientConfiguration(clientConfiguration).build();
    }
}
