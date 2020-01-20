package com.app.listaprzebojow.mapper;

import com.app.listaprzebojow.dto.RoleDTO;
import com.app.listaprzebojow.dto.VoteDTO;
import com.app.listaprzebojow.model.Role;
import com.app.listaprzebojow.model.Vote;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoteMapper {
    VoteDTO voteToVoteDTO (Vote vote);
    Vote voteDTOToVote(VoteDTO voteDTO);
}
