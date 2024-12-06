package net.shahid.microservices.myservice.utils;

import lombok.extern.java.Log;
import net.shahid.microservices.myservice.domain.entity.Product;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Log
public class DatabaseUpdater {
    private final MongoTemplate mongoTemplate;

    public DatabaseUpdater(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateAllEntries() {
        Query query = new Query();
        Update update = new Update();
        update.set("updatedAt", LocalDateTime.now());
        mongoTemplate.updateMulti(query, update, Product.class);
        log.info("Updated all entries in database");
    }
}
