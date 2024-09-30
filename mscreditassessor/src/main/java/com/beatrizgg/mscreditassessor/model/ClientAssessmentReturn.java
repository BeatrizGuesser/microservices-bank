package com.beatrizgg.mscreditassessor.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ClientAssessmentReturn {

    private List<ApprovedCard> cards;
}
