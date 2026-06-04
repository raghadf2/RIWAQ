package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.IN.SpaceDTOIn;
import com.example.riwaq.DTO.OUT.SpaceDTOOut;
import com.example.riwaq.Model.Space;
import com.example.riwaq.Repository.SpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpaceService {

    private final SpaceRepository spaceRepository;

    public void addSpace(SpaceDTOIn dto){

        Space existing = spaceRepository.findByBookIdAndName(dto.getBookId(), dto.getName());

        if (existing != null) {
            throw new ApiException("Space with same name already exists for this book");
        }

        Space space = new Space();

        space.setBookId(dto.getBookId());
        space.setName(dto.getName());
        space.setDescription(dto.getDescription());

        spaceRepository.save(space);
    }

    public List<SpaceDTOOut> getAllSpaces(){

        List<Space> spaces = spaceRepository.findAll();

        List<SpaceDTOOut> dtoOutList = new ArrayList<>();

        for(Space space : spaces){

            SpaceDTOOut dtoOut = new SpaceDTOOut();

            dtoOut.setSpaceId(space.getSpaceId());
            dtoOut.setBookId(space.getBookId());
            dtoOut.setName(space.getName());
            dtoOut.setDescription(space.getDescription());

            dtoOutList.add(dtoOut);
        }

        return dtoOutList;
    }

    public void updateSpace(Integer spaceId, SpaceDTOIn dto) {

        Space space = spaceRepository.findSpaceBySpaceId(spaceId);

        if (space == null) {
            throw new ApiException("Space not found");
        }

        if (space.getMemberships() != null && !space.getMemberships().isEmpty()) {
            throw new ApiException("Cannot update space, it has active members");
        }

        space.setName(dto.getName());
        space.setDescription(dto.getDescription());

        if (dto.getBookId() != null) {
            space.setBookId(dto.getBookId());
        }

        spaceRepository.save(space);
    }

    public void deleteSpace(Integer spaceId) {

        Space space = spaceRepository.findSpaceBySpaceId(spaceId);

        if (space == null) {
            throw new ApiException("Space not found");
        }

        if (space.getMemberships() != null && !space.getMemberships().isEmpty()) {
            throw new ApiException("Cannot delete space with active members");
        }

        spaceRepository.delete(space);
    }
}
