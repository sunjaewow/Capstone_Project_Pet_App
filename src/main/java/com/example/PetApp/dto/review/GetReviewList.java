package com.example.PetApp.dto.review;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetReviewList {

    private Long reviewId;

    private Long userId;

    private String userName;

    private String userImageUrl;

    private String title;

    private Integer rating;

    private LocalDateTime reviewTime;

    private boolean isOwner;

}
