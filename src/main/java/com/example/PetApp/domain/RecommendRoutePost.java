//package com.example.PetApp.domain;
//
//import com.example.PetApp.domain.embedded.Location;
//import com.example.PetApp.domain.embedded.Content;
//import com.example.PetApp.domain.superclass.BaseTimeEntity;
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Getter
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Builder
//public class RecommendRoutePost extends BaseTimeEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long recommendRouteId;
//
//    @Setter
//    @Embedded
//    private Content content;
//
//    @Embedded
//    private Location location;
//
//    @OneToMany(mappedBy = "recommendRoutePost",cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<WalkingTogetherMatch> walkingTogetherMatch;
//
//    @OneToMany(mappedBy = "recommendRoutePost", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<LikeT> likeTs;
//
//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    @OneToMany(mappedBy = "recommendRoutePost", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Comment> comments;
//
//}
