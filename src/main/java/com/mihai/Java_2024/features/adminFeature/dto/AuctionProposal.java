package com.mihai.Java_2024.features.adminFeature.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class AuctionProposal {
    public Integer auctionId;
    public Boolean approval;
    public Optional<String> reason;
}
