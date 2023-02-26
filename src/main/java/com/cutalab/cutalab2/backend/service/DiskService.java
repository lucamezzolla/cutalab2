package com.cutalab.cutalab2.backend.service;

import com.cutalab.cutalab2.backend.dto.UserDTO;
import com.cutalab.cutalab2.backend.dto.dashboards.disks.DiskDTO;
import com.cutalab.cutalab2.backend.dto.dashboards.disks.DiskGenreDTO;
import com.cutalab.cutalab2.backend.dto.dashboards.disks.DiskStyleDTO;
import com.cutalab.cutalab2.backend.dto.dashboards.disks.StatusDTO;
import com.cutalab.cutalab2.backend.entity.dashboards.disks.DiskEntity;
import com.cutalab.cutalab2.backend.entity.dashboards.disks.DiskGenreEntity;
import com.cutalab.cutalab2.backend.entity.dashboards.disks.DiskStyleEntity;
import com.cutalab.cutalab2.backend.entity.dashboards.disks.StatusEntity;
import com.cutalab.cutalab2.backend.repository.DiskGenreRepository;
import com.cutalab.cutalab2.backend.repository.DiskRepository;
import com.cutalab.cutalab2.backend.repository.DiskStyleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class DiskService {

    @Autowired
    private DiskGenreRepository diskGenreRepository;

    @Autowired
    private DiskStyleRepository diskStyleRepository;

    @Autowired
    private DiskRepository diskRepository;

    public List<DiskGenreDTO> findAllGenres() {
        List<DiskGenreEntity> list1 = diskGenreRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        List<DiskGenreDTO> list2 = new ArrayList<>();
        for(DiskGenreEntity d : list1) {
            DiskGenreDTO diskGenreDTO = new DiskGenreDTO();
            BeanUtils.copyProperties(d, diskGenreDTO);
            list2.add(diskGenreDTO);
        }
        return list2;
    }

    public List<DiskStyleDTO> findAllStyles() {
        List<DiskStyleEntity> list1 = diskStyleRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        List<DiskStyleDTO> list2 = new ArrayList<>();
        for(DiskStyleEntity d : list1) {
            DiskStyleDTO diskStyleDTO = new DiskStyleDTO();
            BeanUtils.copyProperties(d, diskStyleDTO);
            list2.add(diskStyleDTO);
        }
        return list2;
    }

    public List<DiskDTO> findDisks(String title, String author, DiskGenreDTO diskGenreDTO, DiskStyleDTO diskStyleDTO, UserDTO userDTO) {
        List<DiskEntity> list1 = new ArrayList<>();
        if (diskGenreDTO == null && diskStyleDTO == null) {
            list1 = diskRepository.search(title, author, userDTO.getId());
        } else if (diskGenreDTO != null && diskStyleDTO == null) {
            DiskGenreEntity diskGenreEntity = new DiskGenreEntity();
            BeanUtils.copyProperties(diskGenreDTO, diskGenreEntity);
            list1 = diskRepository.search(title, author, diskGenreEntity, userDTO.getId());
        } else if (diskGenreDTO == null && diskStyleDTO != null) {
            DiskStyleEntity diskStyleEntity = new DiskStyleEntity();
            BeanUtils.copyProperties(diskStyleDTO, diskStyleEntity);
            list1 = diskRepository.search(title, author, diskStyleEntity, userDTO.getId());
        } else if (diskGenreDTO != null && diskStyleDTO != null) {
            DiskGenreEntity diskGenreEntity = new DiskGenreEntity();
            DiskStyleEntity diskStyleEntity = new DiskStyleEntity();
            BeanUtils.copyProperties(diskGenreDTO, diskGenreEntity);
            BeanUtils.copyProperties(diskStyleDTO, diskStyleEntity);
            list1 = diskRepository.search(title, author, diskGenreEntity, diskStyleEntity, userDTO.getId());
        }
        List<DiskDTO> list2 = new ArrayList<>();
        for (DiskEntity d : list1) {
            DiskDTO diskDTO = new DiskDTO();
            BeanUtils.copyProperties(d, diskDTO);
            StatusDTO statusDTO1 = new StatusDTO();
            StatusDTO statusDTO2 = new StatusDTO();
            List<DiskGenreDTO> listGenreDTO = new ArrayList<>();
            List<DiskStyleDTO> listStyleDTO = new ArrayList<>();
            BeanUtils.copyProperties(d.getDiskStatus(), statusDTO1);
            BeanUtils.copyProperties(d.getCoverStatus(), statusDTO2);
            Iterator iterator1 = d.getDiskGenreList().iterator();
            while(iterator1.hasNext()) {
                DiskGenreDTO dto = new DiskGenreDTO();
                BeanUtils.copyProperties(iterator1.next(), dto);
                listGenreDTO.add(dto);
            }
            Iterator iterator2 = d.getDiskStyleList().iterator();
            while(iterator2.hasNext()) {
                DiskStyleDTO dto = new DiskStyleDTO();
                BeanUtils.copyProperties(iterator2.next(), dto);
                listStyleDTO.add(dto);
            }
            diskDTO.setDiskStatus(statusDTO1);
            diskDTO.setCoverStatus(statusDTO2);
            diskDTO.setDiskGenreList(listGenreDTO);
            diskDTO.setDiskStyleList(listStyleDTO);
            list2.add(diskDTO);
        }
        return list2;
    }

    public DiskDTO findByID(Integer id) {
        DiskDTO diskDTO = new DiskDTO();
        DiskEntity diskEntity = diskRepository.getReferenceById(id);
        BeanUtils.copyProperties(diskEntity, diskDTO);
        StatusDTO statusDTO1 = new StatusDTO();
        StatusDTO statusDTO2 = new StatusDTO();
        List<DiskGenreDTO> listGenreDTO = new ArrayList<>();
        List<DiskStyleDTO> listStyleDTO = new ArrayList<>();
        BeanUtils.copyProperties(diskEntity.getDiskStatus(), statusDTO1);
        BeanUtils.copyProperties(diskEntity.getCoverStatus(), statusDTO2);
        Iterator iterator1 = diskEntity.getDiskGenreList().iterator();
        while(iterator1.hasNext()) {
            DiskGenreDTO dto = new DiskGenreDTO();
            BeanUtils.copyProperties(iterator1.next(), dto);
            listGenreDTO.add(dto);
        }
        Iterator iterator2 = diskEntity.getDiskStyleList().iterator();
        while(iterator2.hasNext()) {
            DiskStyleDTO dto = new DiskStyleDTO();
            BeanUtils.copyProperties(iterator2.next(), dto);
            listStyleDTO.add(dto);
        }
        diskDTO.setDiskStatus(statusDTO1);
        diskDTO.setCoverStatus(statusDTO2);
        diskDTO.setDiskGenreList(listGenreDTO);
        diskDTO.setDiskStyleList(listStyleDTO);
        return diskDTO;
    }

    public BigDecimal totalValue(UserDTO userDTO) {
        return diskRepository.totalValue(userDTO.getId());
    }

    public Integer count(UserDTO userDTO) {
        return diskRepository.count(userDTO.getId());
    }

    public void update(DiskDTO diskDTO) {
        DiskEntity diskEntity = new DiskEntity();
        BeanUtils.copyProperties(diskDTO, diskEntity);
        StatusEntity status1 = new StatusEntity();
        StatusEntity status2 = new StatusEntity();
        List<DiskGenreEntity> listGenreEntity = new ArrayList<>();
        List<DiskStyleEntity> listStyleEntity = new ArrayList<>();
        BeanUtils.copyProperties(diskDTO.getDiskStatus(), status1);
        BeanUtils.copyProperties(diskDTO.getCoverStatus(), status2);
        Iterator iterator1 = diskDTO.getDiskGenreList().iterator();
        while(iterator1.hasNext()) {
            DiskGenreEntity genreEntity = new DiskGenreEntity();
            BeanUtils.copyProperties(iterator1.next(), genreEntity);
            listGenreEntity.add(genreEntity);
        }
        Iterator iterator2 = diskDTO.getDiskStyleList().iterator();
        while(iterator2.hasNext()) {
            DiskStyleEntity styleEntity = new DiskStyleEntity();
            BeanUtils.copyProperties(iterator2.next(), styleEntity);
            listStyleEntity.add(styleEntity);
        }
        diskEntity.setDiskStatus(status1);
        diskEntity.setCoverStatus(status2);
        diskEntity.setDiskGenreList(listGenreEntity);
        diskEntity.setDiskStyleList(listStyleEntity);
        System.out.println("********************************************* ENTITY: "+diskEntity);
        diskRepository.saveAndFlush(diskEntity);
    }

    public void remove(DiskDTO diskDTO) {
        diskRepository.deleteById(diskDTO.getId());
    }

}
