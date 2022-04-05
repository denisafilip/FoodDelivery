package com.example.secondassignment.model.mappers;

import com.example.secondassignment.model.DTO.ZoneDTO;
import com.example.secondassignment.model.Zone;

public class ZoneMapper implements Mapper<Zone, ZoneDTO> {

    private static ZoneMapper zoneMapper = null;

    private ZoneMapper() {

    }

    public static ZoneMapper getInstance() {
        if (zoneMapper == null) {
            zoneMapper = new ZoneMapper();
        }
        return zoneMapper;
    }

    @Override
    public Zone convertFromDTO(ZoneDTO zoneDTO) {
        //Optional<Zone> zone = zoneRepository.findByName(zoneDTO.getName());
        //return zone.orElse(null);
        return null;
    }

    @Override
    public ZoneDTO convertToDTO(Zone zone) {
        return ZoneDTO.builder()
                .name(zone.getName())
                .build();
    }
}
