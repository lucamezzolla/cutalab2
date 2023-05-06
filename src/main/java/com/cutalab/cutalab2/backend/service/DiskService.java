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
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

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

    public List<DiskDTO> findDisks(Integer offset, String title, String author, DiskGenreDTO diskGenreDTO, DiskStyleDTO diskStyleDTO, UserDTO userDTO) {
        List<DiskEntity> list1;
        if (diskGenreDTO == null && diskStyleDTO == null) {
            list1 = diskRepository.search(offset, title, author, userDTO.getId());
        } else if (diskGenreDTO != null && diskStyleDTO == null) {
            DiskGenreEntity diskGenreEntity = new DiskGenreEntity();
            BeanUtils.copyProperties(diskGenreDTO, diskGenreEntity);
            list1 = diskRepository.search(offset,title, author, diskGenreEntity, userDTO.getId());
        } else if (diskGenreDTO == null) {
            DiskStyleEntity diskStyleEntity = new DiskStyleEntity();
            BeanUtils.copyProperties(diskStyleDTO, diskStyleEntity);
            list1 = diskRepository.search(offset,title, author, diskStyleEntity, userDTO.getId());
        } else {
            DiskGenreEntity diskGenreEntity = new DiskGenreEntity();
            DiskStyleEntity diskStyleEntity = new DiskStyleEntity();
            BeanUtils.copyProperties(diskGenreDTO, diskGenreEntity);
            BeanUtils.copyProperties(diskStyleDTO, diskStyleEntity);
            list1 = diskRepository.search(offset,title, author, diskGenreEntity, diskStyleEntity, userDTO.getId());
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
            for (DiskGenreEntity diskGenreEntity : d.getDiskGenreList()) {
                DiskGenreDTO dto = new DiskGenreDTO();
                BeanUtils.copyProperties(diskGenreEntity, dto);
                listGenreDTO.add(dto);
            }
            for (DiskStyleEntity diskStyleEntity : d.getDiskStyleList()) {
                DiskStyleDTO dto = new DiskStyleDTO();
                BeanUtils.copyProperties(diskStyleEntity, dto);
                listStyleDTO.add(dto);
            }
            diskDTO.setDiskStatus(statusDTO1);
            diskDTO.setCoverStatus(statusDTO2);
            diskDTO.setDiskGenreList(listGenreDTO);
            diskDTO.setDiskStyleList(listStyleDTO);
            if(diskDTO.getCover() == null || (diskDTO.getCover() != null && diskDTO.getCover().isEmpty())) {
                String cover = getCoverImage(diskDTO);
                if(cover != null && !cover.isEmpty()) {
                    DiskEntity diskEntity = diskRepository.getReferenceById(diskDTO.getId());
                    diskEntity.setCover(cover);
                    diskRepository.saveAndFlush(diskEntity);
                    diskDTO.setCover(cover);
                }
            }
            list2.add(diskDTO);
        }
        return list2;
    }

    public List<DiskDTO> findAllDisks(Integer userId) {
        List<DiskEntity> list1 = diskRepository.findAll(userId);
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
            for (DiskGenreEntity diskGenreEntity : d.getDiskGenreList()) {
                DiskGenreDTO dto = new DiskGenreDTO();
                BeanUtils.copyProperties(diskGenreEntity, dto);
                listGenreDTO.add(dto);
            }
            for (DiskStyleEntity diskStyleEntity : d.getDiskStyleList()) {
                DiskStyleDTO dto = new DiskStyleDTO();
                BeanUtils.copyProperties(diskStyleEntity, dto);
                listStyleDTO.add(dto);
            }
            diskDTO.setDiskStatus(statusDTO1);
            diskDTO.setCoverStatus(statusDTO2);
            diskDTO.setDiskGenreList(listGenreDTO);
            diskDTO.setDiskStyleList(listStyleDTO);
            if(diskDTO.getCover() == null || (diskDTO.getCover() != null && diskDTO.getCover().isEmpty())) {
                String cover = getCoverImage(diskDTO);
                if(cover != null && !cover.isEmpty()) {
                    DiskEntity diskEntity = diskRepository.getReferenceById(diskDTO.getId());
                    diskEntity.setCover(cover);
                    diskRepository.saveAndFlush(diskEntity);
                    diskDTO.setCover(cover);
                }
            }
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
        for (DiskGenreEntity diskGenreEntity : diskEntity.getDiskGenreList()) {
            DiskGenreDTO dto = new DiskGenreDTO();
            BeanUtils.copyProperties(diskGenreEntity, dto);
            listGenreDTO.add(dto);
        }
        for (DiskStyleEntity diskStyleEntity : diskEntity.getDiskStyleList()) {
            DiskStyleDTO dto = new DiskStyleDTO();
            BeanUtils.copyProperties(diskStyleEntity, dto);
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

    public void insert(DiskDTO diskDTO) {
        DiskEntity diskEntity = new DiskEntity();
        diskEntity.setTitle(diskDTO.getTitle());
        diskEntity.setAuthor(diskDTO.getAuthor());
        diskEntity.setCover(diskDTO.getCover());
        diskEntity.setLabel(diskDTO.getLabel());
        diskEntity.setNote(diskDTO.getNote());
        diskEntity.setOpenable(diskDTO.isOpenable());
        diskEntity.setPresumedValue(diskDTO.getPresumedValue());
        diskEntity.setReprint(diskDTO.getReprint());
        diskEntity.setYear(diskDTO.getYear());
        diskEntity.setUserId(diskDTO.getUserId());
        StatusEntity status1 = new StatusEntity();
        StatusEntity status2 = new StatusEntity();
        BeanUtils.copyProperties(diskDTO.getDiskStatus(), status1);
        BeanUtils.copyProperties(diskDTO.getCoverStatus(), status2);
        diskEntity.setDiskStatus(status1);
        diskEntity.setCoverStatus(status2);
        for(DiskGenreDTO diskGenreDTO : diskDTO.getDiskGenreList()) {
            DiskGenreEntity diskGenreEntity = diskGenreRepository.getReferenceById(diskGenreDTO.getId());
            diskEntity.addGenre(diskGenreEntity);
        }
        for(DiskStyleDTO diskStyleDTO : diskDTO.getDiskStyleList()) {
            DiskStyleEntity diskStyleEntity = diskStyleRepository.getReferenceById(diskStyleDTO.getId());
            diskEntity.addStyle(diskStyleEntity);
        }
        diskRepository.saveAndFlush(diskEntity);
    }
    public void update(DiskDTO diskDTO) {

        DiskEntity diskEntity = diskRepository.getReferenceById(diskDTO.getId());

        Iterator<DiskGenreEntity> it = diskEntity.getDiskGenreList().iterator();
        outerloop:
        while(it.hasNext()) {
            DiskGenreEntity d = it.next();
            for(DiskGenreDTO diskGenreDTO : diskDTO.getDiskGenreList()) {
                if(diskGenreDTO.getId().equals(d.getId())) {
                    break outerloop;
                }
            }
            it.remove();
            d.setDisksList(null);
        }
        Iterator<DiskStyleEntity> it2 = diskEntity.getDiskStyleList().iterator();
        outerloop:
        while(it2.hasNext()) {
            DiskStyleEntity d = it2.next();
            for(DiskStyleDTO diskStyleDTO : diskDTO.getDiskStyleList()) {
                if(diskStyleDTO.getId().equals(d.getId())) {
                    break outerloop;
                }
            }
            it2.remove();
            d.setDisksList(null);
        }
        diskRepository.saveAndFlush(diskEntity);

        diskEntity.setTitle(diskDTO.getTitle());
        diskEntity.setAuthor(diskDTO.getAuthor());
        diskEntity.setCover(diskDTO.getCover());
        diskEntity.setLabel(diskDTO.getLabel());
        diskEntity.setNote(diskDTO.getNote());
        diskEntity.setOpenable(diskDTO.isOpenable());
        diskEntity.setPresumedValue(diskDTO.getPresumedValue());
        diskEntity.setReprint(diskDTO.getReprint());
        diskEntity.setYear(diskDTO.getYear());

        StatusEntity status1 = new StatusEntity();
        StatusEntity status2 = new StatusEntity();
        BeanUtils.copyProperties(diskDTO.getDiskStatus(), status1);
        BeanUtils.copyProperties(diskDTO.getCoverStatus(), status2);

        diskEntity.setDiskStatus(status1);
        diskEntity.setCoverStatus(status2);

        for(DiskGenreDTO diskGenreDTO : diskDTO.getDiskGenreList()) {
            DiskGenreEntity diskGenreEntity = diskGenreRepository.getReferenceById(diskGenreDTO.getId());
            diskEntity.addGenre(diskGenreEntity);
        }
        for(DiskStyleDTO diskStyleDTO : diskDTO.getDiskStyleList()) {
            DiskStyleEntity diskStyleEntity = diskStyleRepository.getReferenceById(diskStyleDTO.getId());
            System.out.println(diskStyleEntity.getName());
            diskEntity.addStyle(diskStyleEntity);
        }
        diskRepository.saveAndFlush(diskEntity);
    }

    public void remove(DiskDTO diskDTO) {
        diskRepository.deleteById(diskDTO.getId());
    }

    private String getCoverImage(DiskDTO DiskDTO) {
        String retval = "";
        String titolo = DiskDTO.getTitle();
        String autore = DiskDTO.getAuthor();
        try {
            URL url = new URL("https://api.discogs.com/database/search?q=" + titolo + " " + autore);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("Authorization", "Discogs token=jAIYdVZGKWQkarWixrFhWhCLXLblfgtmmTYngsMz");
            InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            String response = s.hasNext() ? s.next() : "";
            System.out.println(response);
            String[] split = response.split("\"cover_image\": \"");
            String[] split2 = split[1].split("\"");
            retval = split2[0];
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retval;
    }

    public Integer searchCount(String title, String author, DiskGenreDTO diskGenreDTO, DiskStyleDTO diskStyleDTO, UserDTO userDTO) {
        if (diskGenreDTO == null && diskStyleDTO == null) {
            return diskRepository.searchCount(title, author,userDTO.getId());
        } else if (diskGenreDTO != null && diskStyleDTO == null) {
            DiskGenreEntity diskGenreEntity = new DiskGenreEntity();
            BeanUtils.copyProperties(diskGenreDTO, diskGenreEntity);
            return diskRepository.searchCount(title, author, diskGenreEntity, userDTO.getId());
        } else if (diskGenreDTO == null) {
            DiskStyleEntity diskStyleEntity = new DiskStyleEntity();
            BeanUtils.copyProperties(diskStyleDTO, diskStyleEntity);
            return diskRepository.searchCount(title, author, diskStyleEntity, userDTO.getId());
        } else {
            DiskGenreEntity diskGenreEntity = new DiskGenreEntity();
            DiskStyleEntity diskStyleEntity = new DiskStyleEntity();
            BeanUtils.copyProperties(diskGenreDTO, diskGenreEntity);
            BeanUtils.copyProperties(diskStyleDTO, diskStyleEntity);
            return diskRepository.searchCount(title, author, diskGenreEntity, diskStyleEntity, userDTO.getId());
        }
    }

}
