package com.example.demo.infrastructure.persistence;

import com.example.demo.domain.model.Comment;
import com.example.demo.domain.model.Post;
import com.example.demo.domain.model.Status;
import com.example.demo.domain.repository.PostRepository;
import com.example.demo.interfaces.dto.PostSummary;
import com.mongodb.client.result.DeleteResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MongoPostRepository implements PostRepository {
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Flux<Post> findAll() {
        return mongoTemplate.findAll(Post.class);
    }

    @Override
    public Flux<PostSummary> findByKeyword(String keyword, int offset, int limit) {
        Query query = new Query();

        // Apply keyword filter only if provided
        if (keyword != null && !keyword.isBlank()) {
            query.addCriteria(Criteria.where("title").regex(".*" + keyword + ".*", "i"));
        }

        // Apply pagination
        query.skip(offset).limit(limit);

        return mongoTemplate
                .find(query, Post.class)
                .map(it -> new PostSummary(it.getId(), it.getTitle(), it.getCreatedDate()));
    }


    @Override
    public Mono<Long> countByKeyword(String keyword) {
        Query query = new Query();

        // Apply keyword filter only if provided
        if (keyword != null && !keyword.isBlank()) {
            query.addCriteria(Criteria.where("title").regex(".*" + keyword + ".*", "i"));
        }

        return mongoTemplate.count(query, Post.class);
    }


    @Override
    public Mono<Post> findById(String id) {
        return mongoTemplate.findById(id, Post.class);
    }

    @Override
    public Mono<Post> create(String title, String content) {
        return mongoTemplate.insert(Post.builder().title(title).content(content).build());
    }

    @Override
    public Mono<Boolean> update(String id, String title, String content) {
        return mongoTemplate.update(Post.class)
                .matching(where("id").is(id))
                .apply(Update.update("title", title).set("content", content))
                .all()
                .map(result -> result.getModifiedCount() == 1L);
    }

    @Override
    public Mono<Boolean> updateStatus(String id, Status status) {
        return mongoTemplate.update(Post.class)
                .matching(where("id").is(id))
                .apply(Update.update("status", status))
                .all()
                .map(result -> result.getModifiedCount() == 1L);
    }

    @Override
    public Mono<Boolean> deleteById(String id) {
        return mongoTemplate.remove(Post.class)
                .matching(where("id").is(id))
                .all()
                .map(result -> result.getDeletedCount() == 1L);
    }

    @Override
    public Mono<Long> deleteAll() {
        return mongoTemplate.remove(Post.class)
                .all()
                .map(DeleteResult::getDeletedCount);
    }

    @Override
    public Mono<Boolean> addComment(String id, String content) {
        return mongoTemplate.insert(Comment.builder().content(content).build()) // Insert the comment
                .map(Comment::getId) // Extract the comment ID
                .flatMap(commentId -> mongoTemplate.update(Post.class)
                        .matching(where("id").is(id))
                        .apply(new Update().push("comments", commentId)) // Push only the ID
                        .all()
                        .map(result -> result.getModifiedCount() == 1L)
                );
    }


    @Override
    public Mono<Boolean> removeComment(String id, String commentId) {
        var comment = mongoTemplate.findById(commentId, Comment.class);
        return comment.flatMap(c -> mongoTemplate.update(Post.class)
                .matching(where("id").is(id))
                .apply(new Update().pull("comments", c))
                .all()
                .map(result -> result.getModifiedCount() == 1L)
        );
    }
}
