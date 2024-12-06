package net.shahid.microservices.myservice.domain.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.shahid.microservices.myservice.domain.enums.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "Products")
public class Product implements Serializable {
    @Id
    String id;

    @NotNull
    Localization title;

    @CreatedDate
    @Field(name = "created_at", targetType = FieldType.DATE_TIME, write = Field.Write.NON_NULL)
    @Setter(AccessLevel.NONE)
    LocalDateTime createdAt;

    @LastModifiedDate
    @Field(name = "updated_at", targetType = FieldType.DATE_TIME, write = Field.Write.ALWAYS)
    LocalDateTime updatedAt;

    @NotNull
    Type type;

    boolean active;

    public Product() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        active = true;
    }

    public Product(Localization title, Type type) {
        this.title = title;
        this.type = type;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        active = true;
    }

    public String getTitle(String language) {
        return switch (language) {
            case "en" -> title.getEn();
            case "fr" -> title.getFr() == null ? title.getEn() : title.getFr();
            default -> title.getAr();
        };
    }
}
