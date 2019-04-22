package org.assemble;

import org.assemble.model.Avenger;
import org.assemble.model.AvengerNameGenerator;
import org.assemble.model.AvengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StartupBean {

    @Autowired
    private AvengerRepository avengerRepository;
    
    @PostConstruct
    public void onStartup() {
        for (int i = 0; i < 10000; i++) {
            String name = AvengerNameGenerator.generateName();

            Avenger avenger = new Avenger();
            avenger.setName(name);
            avenger.setCivilName("Fake");
            avenger.setSnapped(true);

            avengerRepository.save(avenger);
        }
    }
}
