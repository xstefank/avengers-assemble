package org.assemble.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface AvengerRepository extends JpaRepository<Avenger, Long> {

    List<Avenger> findBySnapped(boolean snapped);

    @Query(value = "select * from avenger where name like %?1% or real_name like %?1%", nativeQuery = true)
    List<Avenger> search(String searchValue);

    @Query(value = "select * from avenger where name like %?1% or real_name like %?1%", nativeQuery = true)
    Page<Avenger> search(String searchValue, Pageable pageable);
    
    List<Avenger> findByOrderByNameAsc();

    @Query("select a from Avenger a")
    Stream<Avenger> streamAll();
}

 
