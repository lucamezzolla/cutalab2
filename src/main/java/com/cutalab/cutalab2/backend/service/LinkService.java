package com.cutalab.cutalab2.backend.service;

import com.cutalab.cutalab2.backend.dto.LinkDTO;
import com.cutalab.cutalab2.backend.entity.AreaLinkEntity;
import com.cutalab.cutalab2.backend.entity.LinkEntity;
import com.cutalab.cutalab2.backend.repository.LinkRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    public List<LinkDTO> findAll() {
        List<LinkDTO> list1 = new ArrayList<>();
        List<LinkEntity> list2 = linkRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
        for(LinkEntity a : list2) {
            LinkDTO linkDTO = new LinkDTO();
            BeanUtils.copyProperties(a, linkDTO);
            list1.add(linkDTO);
        }
        return list1;
    }

    public List<LinkDTO> findAllByArea(AreaLinkEntity areaLinkEntity) {
        List<LinkDTO> list1 = new ArrayList<>();
        List<LinkEntity> list2 = linkRepository.findAllByAreaLinkEntity(areaLinkEntity, Sort.by(Sort.Direction.ASC, "title"));
        for(LinkEntity a : list2) {
            LinkDTO linkDTO = new LinkDTO();
            BeanUtils.copyProperties(a, linkDTO);
            list1.add(linkDTO);
        }
        return list1;
    }

    public LinkEntity create(LinkEntity linkEntity) {
        return linkRepository.saveAndFlush(linkEntity);
    }

    public LinkEntity update(LinkDTO linkDTO) {
        LinkEntity linkEntity = new LinkEntity();
        BeanUtils.copyProperties(linkDTO, linkEntity);
        return linkRepository.saveAndFlush(linkEntity);
    }

    public void remove(LinkDTO linkDTO) {
        linkRepository.deleteById(linkDTO.getId());
    }

}