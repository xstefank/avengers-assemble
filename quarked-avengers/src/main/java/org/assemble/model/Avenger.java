package org.assemble.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.List;

@Entity
public class Avenger extends PanacheEntity {
    
    public String name;
    
    @Column(name = "real_name")
    public String civilName;
    
    public boolean snapped;
    
    public static List<Avenger> findUnsnapped() {
        return list("snapped", false);
    }

    public static List<Avenger> search(String searchValue) {
        return searchQuery(searchValue).list();
    }

    public static PanacheQuery<Avenger> searchQuery(String searchValue) {
        return find("name like :search or real_name like :search",
            Parameters.with("search", "%" + searchValue + "%"));
    }

    public static PanacheQuery<Avenger> searchQuery(String searchValue, Sort sort) {
        return find("name like :search or real_name like :search", sort, 
            Parameters.with("search", "%" + searchValue + "%"));
    }
}
