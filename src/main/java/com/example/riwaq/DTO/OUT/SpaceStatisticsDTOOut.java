package com.example.riwaq.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceStatisticsDTOOut {

    private Integer spaceId;
    private String spaceName;

    private Integer bookId;
    private String bookTitle;

    private Integer creatorId;

    private Integer membersCount;
    private Integer completedMembers;
    private Integer readingMembers;
    private Integer notStartedMembers;

    private Integer averageProgressPercentage;

    private Integer topUserId;
    private String topUsername;
    private Integer highestProgressPercentage;
}
