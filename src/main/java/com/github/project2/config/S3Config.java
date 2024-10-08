package com.github.project2.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class S3Config {

        @Value("${CLOUD_AWS_CREDENTIALS_ACCESS_KEY}")
        private String accessKey;
        @Value("${CLOUD_AWS_CREDENTIALS_SECRET_KEY}")
        private String secretKey;
        @Value("${cloud.aws.region.static}")
        private String region;

        @Bean
        public AmazonS3Client amazonS3Client() {
            BasicAWSCredentials awsCredentials= new BasicAWSCredentials(accessKey, secretKey);
            return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                    .withRegion(region)
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .build();
        }
}
