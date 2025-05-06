package ru.mirea.fantasyfootballengine.util.id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FootballerTeamRelationId implements Serializable {
    private UUID footballerId;
    private UUID teamId;
}
