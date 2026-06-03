package com.example.riwaq.Service;

import com.example.riwaq.Api.ApiException;
import com.example.riwaq.DTO.DTOIN.SpaceDTOIn;
import com.example.riwaq.DTO.DTOOUT.SpaceDTOOut;
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

    public void updateSpace(Integer spaceId , SpaceDTOIn dto){

        Space space = spaceRepository.findBySpaceId(spaceId)
                .orElseThrow(() -> new ApiException("Space not found"));

        space.setBookId(dto.getBookId());
        space.setName(dto.getName());
        space.setDescription(dto.getDescription());

        spaceRepository.save(space);
    }

    public void deleteSpace(Integer spaceId){

        Space space = spaceRepository.findBySpaceId(spaceId)
                .orElseThrow(() -> new ApiException("Space not found"));

        spaceRepository.delete(space);
    }
}
