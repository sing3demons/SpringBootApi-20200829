package com.sing3demons.springbootapi.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sing3demons.springbootapi.entity.Social;
import com.sing3demons.springbootapi.entity.User;
import com.sing3demons.springbootapi.repository.SocialRepository;

@Service
public class SocialService {
    private SocialRepository socialRepository;

    public SocialService(SocialRepository socialRepository) {
        this.socialRepository = socialRepository;
    }

    public Optional<Social> findByUser(User user) {
        return socialRepository.findByUser(user);
    }

    public Social create(User user, String facebook, String line, String instagram, String tiktok) {
        Social entity = new Social();
        entity.setUser(user);
        entity.setFacebook(facebook);
        entity.setLine(line);
        entity.setInstagram(instagram);
        entity.setTiktok(tiktok);

        return socialRepository.save(entity);
    }
}
