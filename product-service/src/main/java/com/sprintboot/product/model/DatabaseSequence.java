package com.sprintboot.product.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@ToString
@Document(collection = "database_sequences")
public class DatabaseSequence {
    @Id
    private String id;
    private Integer seq;
}
