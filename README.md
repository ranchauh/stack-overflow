# My Stack Overflow
Designing and implementation of a competitor of [Stack Overflow](https://stackoverflow.com/).

## Use cases

1) Top questions are to be shown in the home page

2) Users can create a profile

3) Users can post a question, tag a question

4) Users can answer to a question. Rich media content (photos/videos) can be added as an answer.

5) Users can answer to an answer.

6) Users can vote to an answer or question

7) User can search the tags and browse the questions by tags

8) Users can search questions/answers by text.

## Assumptions

1) Top questions are sorted by latest update and are limited to top 50 questions.

2) No provision for authentication and authorization.

3) Tags are created only while posting question.

4) Only tag names are supported not tag description.

5) User searches are not supported.

6) Only image upload is supported fow now under size 100 MB.

7) Front end application not available as of now.

## High Level Design

![Stack Overflow High Level Design](resources/stack-overflow-high-level-design-updated.png)

## Micro Services

### manage-so-user-service
* Manages user registration and other user admin flow via REST endpoints.
* Stores the user profile details in database.

### update-so-service
* Exposes REST endpoints to post, update and delete questions and answers.
* Exposes REST endpoints to upload images and videos for answers.
* Persists the questions and answers in database.
* Persists the images and videos to AWS S3 bucket.
* Publishes message to kafka topics for any change in questions and answers.

### update-es-service
* Consumes messages from kafka topics published by update-so-service.
* Updates the elasticsearch indexes for questions and answers.

### search-so-service
* Searches questions in elasticsearch index based on:
   * Question id.
   * Tags.
   * Question title and description text.
* Searches answers in elasticsearch index based on:
   * Answer id.
   * Parent answer id.
   * Answer text.
* Searches tags based on tag text.   
* Accesses AWS S3 bucket to fetch the images an videos for the answers.

### front-end-service
* Provides user interface to:
  * Create user profiles.
  * Post questions and answers.
  * Search questions and answers by text.
  * Search tags.
  * Search questions by tags.
  * Vote for question and answers.
  * Upload images and videos for the answers.
  
# Technology Stack

## Spring Boot
To create MicroServices in Spring.

## RDBMS
To persist questions and answers.

## Apache Kafka
To publish and consume updates for questions and answers to update them in elasticsearch.

## Elasticsearch
To create search indexes for questions and answers and fast and efficient search results.

## AWS S3
To store rich media contents such as images and videos for answers.

## nginx
To configure a proxy server and enable load balancing all the back-end MicroServices.

## ReactJS (Not finalized)
To create user interface.

# Datastore choice - RDBMS - 
Chose RDBMS as data store because:
* The data is relational. For example, answers are related to questions and other answers.
* Since elasticsearch is used for the search. Less reads will be performed on DB. Hence, read performance is not a concern.
* To solve the scalability issue, the database size can be considered large enough to accommodate the desired data.

# Datastore Design
## User
```text
CREATE TABLE SO_USER(
    USER_ID VARCHAR(50) PRIMARY KEY,
    EMAIL_ID VARCHAR(50) NOT NULL,
    PASSWORD VARCHAR(50) NOT NULL,
    DISPLAY_NAME VARCHAR(100),
    UNIQUE KEY (EMAIL_ID)
);
```

## Question
```text
CREATE TABLE QUESTION (
    QUESTION_ID NUMERIC IDENTITY PRIMARY KEY,
    QUESTION_TITLE VARCHAR(1000) NOT NULL,
    QUESTION_DESCRIPTION CLOB,
    VOTE_COUNT NUMERIC,
    TAGS VARCHAR(1000),
    CREATE_TIMESTAMP TIMESTAMP,
    UPDATE_TIMESTAMP TIMESTAMP,
    POSTED_BY VARCHAR(50) NOT NULL,
    FOREIGN KEY (POSTED_BY) REFERENCES SO_USER(USER_ID)
);
```
## Tag
```text
CREATE TABLE TAG (
    TAG_NAME VARCHAR(50) PRIMARY KEY,
    TAG_DESCRIPTION VARCHAR(1000),
    CREATE_TIMESTAMP TIMESTAMP,
    UPDATE_TIMESTAMP TIMESTAMP,
    CREATED_BY VARCHAR(50) NOT NULL,
    FOREIGN KEY (CREATED_BY) REFERENCES SO_USER(USER_ID)
);
```
## QuestionTag
```text
CREATE TABLE QUESTION_TAG (
    QUESTION_ID NUMERIC,
    TAG_NAME VARCHAR(50),
    CREATE_TIMESTAMP TIMESTAMP,
    UPDATE_TIMESTAMP TIMESTAMP,
    PRIMARY KEY (QUESTION_ID, TAG_NAME)
);
```

## Answer
```text
CREATE TABLE ANSWER (
    ANSWER_ID NUMERIC IDENTITY PRIMARY KEY,
    ANSWER_TEXT CLOB,
    QUESTION_ID NUMERIC,
    PARENT_ANSWER_ID NUMERIC,
    VOTE_COUNT NUMERIC,
    CREATE_TIMESTAMP TIMESTAMP,
    UPDATE_TIMESTAMP TIMESTAMP,
    POSTED_BY VARCHAR(50) NOT NULL,
    FOREIGN KEY (POSTED_BY) REFERENCES SO_USER(USER_ID),
    FOREIGN KEY (PARENT_ANSWER_ID) REFERENCES ANSWER(ANSWER_ID),
    FOREIGN KEY (QUESTION_ID) REFERENCES QUESTION(QUESTION_ID)
);
```
## RichContent
```text
CREATE TABLE RICH_CONTENT (
    CONTENT_ID VARCHAR(100) PRIMARY KEY,
    CONTENT_NAME VARCHAR(100) NOT NULL,
    CONTENT_LOCATION VARCHAR(1000) NOT NULL,
    ANSWER_ID NUMERIC NOT NULL,
    CONTENT_TYPE VARCHAR(10) NOT NULL,
    CREATE_TIMESTAMP TIMESTAMP,
    UPDATE_TIMESTAMP TIMESTAMP,
    FOREIGN KEY (ANSWER_ID) REFERENCES ANSWER(ANSWER_ID)
);
```

# Critical APIs

* API to search questions and answers by text.
* API to search questions by tag.
* API to search tags. 