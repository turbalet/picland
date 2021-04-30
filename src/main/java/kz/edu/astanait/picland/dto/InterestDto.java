package kz.edu.astanait.picland.dto;

import lombok.Data;


@Data
public class InterestDto {

    private Long interestId;

    private String interestName;

    private String interestCover;

    private Long parentInterestId;
}
