package ru.mirea.fantasyfootballengine.entity.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.UUID;

@Document(indexName = "footballers")
@Setting(settingPath = "elasticsearch/find-player-by-name-settings.json")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class FootballerDocument {
    @Id
    @Field(type = FieldType.Keyword)
    private UUID id;

    @Field(type = FieldType.Text, analyzer = "custom_analyzer", searchAnalyzer = "custom_analyzer")
    private String firstName;

    @Field(type = FieldType.Text, analyzer = "custom_analyzer", searchAnalyzer = "custom_analyzer")
    private String lastName;

    private String position;
    private short goals;
    private short assists;
    private short ownGoals;
    private short yellowCards;
    private short redCards;
    private short minutesPlayed;
    private short cleanSheets;
    private short bestPlayerAwards;
    private short cost;
}
