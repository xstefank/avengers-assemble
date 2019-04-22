package org.assemble.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Avenger {

    @Id
    @GeneratedValue(generator = "avenger_generator")
    @SequenceGenerator(
        name = "avenger_generator",
        sequenceName = "avenger_sequence",
        allocationSize = 1
    )
    private Long id;
    
    private String name;
    
    @Column(name = "real_name")
    private String civilName;
    
    private boolean snapped;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCivilName() {
        return civilName;
    }

    public void setCivilName(String civilName) {
        this.civilName = civilName;
    }

    public boolean isSnapped() {
        return snapped;
    }

    public void setSnapped(boolean snapped) {
        this.snapped = snapped;
    }
}
