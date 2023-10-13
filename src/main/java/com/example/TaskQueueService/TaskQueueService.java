package com.example.TaskQueueService;

import software.amazon.awssdk.services.sqs.*;
import software.amazon.awssdk.services.sqs.model.*;

public class TaskQueueService {
    private final SqsClient sqsClient;
    private final String queueUrl;

    public TaskQueueService(String awsRegion, String queueName) {
        sqsClient = SqsClient.builder()
                .region(Region.of(awsRegion))
                .build();

        GetQueueUrlResponse getQueueUrlResponse = sqsClient.getQueueUrl(GetQueueUrlRequest.builder()
                .queueName(queueName)
                .build());
        queueUrl = getQueueUrlResponse.queueUrl();
    }

    public void sendMessage(String messageBody) {
        SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .build();
        sqsClient.sendMessage(sendMsgRequest);
    }

    public ReceiveMessageResponse receiveMessages(int maxMessages) {
        ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(maxMessages)
                .build();
        return sqsClient.receiveMessage(receiveRequest);
    }

    public void deleteMessage(String receiptHandle) {
        DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(receiptHandle)
                .build();
        sqsClient.deleteMessage(deleteRequest);
    }

//    public class Main {
//        public static void main(String[] args) {
//            String awsRegion = "us-east-1"; // Replace with your desired AWS region
//            String queueName = "my-task-queue"; // Replace with your queue name
//
//            TaskQueueService taskQueueService = new TaskQueueService(awsRegion, queueName);
//
//            // Sending a task
//            String task = "Process this task";
//            taskQueueService.sendMessage(task);
//
//            // Receiving tasks
//            int maxMessagesToReceive = 10;
//            ReceiveMessageResponse messages = taskQueueService.receiveMessages(maxMessagesToReceive);
//
//            for (Message message : messages.messages()) {
//                // Process the received message (task)
//                String taskMessageBody = message.body();
//                System.out.println("Received task: " + taskMessageBody);
//
//                // Delete the message after processing
//                taskQueueService.deleteMessage(message.receiptHandle());
//            }
//        }
//    }

}
