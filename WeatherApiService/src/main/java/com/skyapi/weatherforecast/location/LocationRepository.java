package com.skyapi.weatherforecast.location;

import com.skyapi.weatherforecast.common.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, String> {

    @Query("select l from Location l where l.trashed = false")
    public List<Location> findUntrashed();

    @Query("select l from Location l where l.trashed = false and l.code = ?1")
    public Optional<Location> findLocationByCode(String code);

    List<Location> code(String code);
}
