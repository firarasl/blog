package com.example.portfolio.service;

import com.example.portfolio.dto.SubfiteDto;
import com.example.portfolio.exception.FiteNotFoundException;
import com.example.portfolio.mapper.SubfiteMapper;
import com.example.portfolio.model.Subfite;
import com.example.portfolio.model.User;
import com.example.portfolio.repository.SubfiteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubfiteService {
    private final SubfiteRepository subfiteRepository;
    private final SubfiteMapper subfiteMapper;
    private final AuthService authService;


    @Transactional
    public SubfiteDto save(SubfiteDto subfiteDto){
//        Subfite save = subfiteRepository.save(subfiteMapper.mapDtoToSubfite(subfiteDto));
        Subfite save = subfiteMapper.mapDtoToSubfite(subfiteDto);
        User user = authService.getCurrentUser();
        save.setUser(user);
        save.setCreatedDate(Instant.now());
        subfiteRepository.save(save);
        subfiteDto.setId(save.getId());
        return subfiteDto;
    }

    @Transactional(readOnly = true)
    public List<SubfiteDto> getAll(){
        return subfiteRepository.findAll()
                .stream()
                .map(subfiteMapper::mapSubfiteToDto)
                .collect(Collectors.toList());
    }

    public SubfiteDto getSubfite(Long id){
        Subfite subfite = subfiteRepository.findById(id)
                .orElseThrow(() -> new FiteNotFoundException("No subfite found with id "+ id));
        return subfiteMapper.mapSubfiteToDto(subfite);
    }
}
