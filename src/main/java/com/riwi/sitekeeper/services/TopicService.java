package com.riwi.sitekeeper.services;

import com.riwi.sitekeeper.dtos.requests.TopicReq;
import com.riwi.sitekeeper.dtos.responses.TopicRes;
import com.riwi.sitekeeper.entitites.TopicEntity;
import com.riwi.sitekeeper.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public List<TopicRes> getAllTopics() {
        List<TopicEntity> topics = topicRepository.findAll();
        return topics.stream()
                .map(this::convertToTopicRes)
                .collect(Collectors.toList());
    }

    public Optional<TopicRes> getTopicById(Long id) {
        return topicRepository.findById(id)
                .map(this::convertToTopicRes);
    }

    public TopicRes createTopic(TopicReq topicReq) {
        TopicEntity newTopic = convertToTopicEntity(topicReq);
        TopicEntity savedTopic = topicRepository.save(newTopic);
        return convertToTopicRes(savedTopic);
    }

    public TopicRes updateTopic(Long id, TopicReq updatedTopicReq) {
        Optional<TopicEntity> existingTopicOptional = topicRepository.findById(id);

        if (existingTopicOptional.isPresent()) {
            TopicEntity existingTopic = existingTopicOptional.get();
            updateTopicEntity(existingTopic, updatedTopicReq);
            TopicEntity savedTopic = topicRepository.save(existingTopic);
            return convertToTopicRes(savedTopic);
        } else {
            throw new RuntimeException("Topic not found with id: " + id);
        }
    }

    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }

    private TopicEntity convertToTopicEntity(TopicReq topicReq) {
        return TopicEntity.builder()
                .name(topicReq.getName())
                .icon(topicReq.getIcon())
                .build();
    }

    private TopicRes convertToTopicRes(TopicEntity topicEntity) {
        return TopicRes.builder()
                .id(topicEntity.getId())
                .name(topicEntity.getName())
                .icon(topicEntity.getIcon())
                .build();
    }

    private void updateTopicEntity(TopicEntity existingTopic, TopicReq updatedTopicReq) {
        existingTopic.setName(updatedTopicReq.getName());
        existingTopic.setIcon(updatedTopicReq.getIcon());
    }
}
