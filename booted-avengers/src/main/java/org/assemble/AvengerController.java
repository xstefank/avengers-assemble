package org.assemble;

import com.fasterxml.jackson.databind.JsonNode;
import org.assemble.model.Avenger;
import org.assemble.model.AvengerRepository;
import org.assemble.model.DataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/avenger")
public class AvengerController {

    @Autowired
    private AvengerRepository avengerRepository;

    @GetMapping("")
    public List<Avenger> getAvengers() {
        return avengerRepository.findAll();
    }

    @PostMapping("")
    public Avenger createAvenger(@RequestBody JsonNode json) {
        Avenger avenger = new Avenger();

        avenger.setName(json.get("name").asText());
        avenger.setCivilName(json.get("civilName").asText());
        avenger.setSnapped(json.get("snapped").asBoolean());

        avengerRepository.save(avenger);
        return avenger;
    }

    @GetMapping("/{id}")
    public Avenger findById(@PathVariable Long id) {
        return avengerRepository.findById(id).orElse(null);
    }

    @GetMapping("/unsnapped")
    public List<Avenger> findUnsnapped() {
        return avengerRepository.findBySnapped(false);
    }

    @GetMapping("/search")
    public List<Avenger> search(@RequestParam("searchValue") String searchValue) {
        return avengerRepository.search(searchValue);
    }

    @GetMapping("/ordered")
    public List<Avenger> ordered() {
        return avengerRepository.findByOrderByNameAsc();
    }

    @GetMapping("/space")
    @Transactional
    public List<Avenger> space() {
        return avengerRepository.streamAll()
            .filter(a -> a.getName().contains(" "))
            .collect(Collectors.toList());
    }

    @GetMapping("/datatable")
    public DataTable dataTable(
        @RequestParam("draw") int draw,
        @RequestParam("start") int start,
        @RequestParam("length") int length,
        @RequestParam(value = "search[value]", required = false) String searchVal,
        @RequestParam("order[0][column]") int orderColumn,
        @RequestParam("order[0][dir]") String orderDirection) {

        DataTable result = new DataTable();
        result.setDraw(draw);

        String sortColumn = getColumnName(orderColumn);

        Sort.Direction direction = orderDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = sortColumn != null ? Sort.by(direction, sortColumn) : null;

        int pageNum = start / length;
        Pageable pageable = sort != null ? 
            PageRequest.of(pageNum, length, sort) : PageRequest.of(pageNum, length);

        Page<Avenger> page;

        if (searchVal != null && !searchVal.isEmpty()) {
            page = avengerRepository.search(searchVal, pageable);
        } else {
            page = avengerRepository.findAll(pageable);
        }
        
        List<Avenger> avengers = page.get().collect(Collectors.toList());
        
        result.setRecordsFiltered(page.getTotalElements());
        result.setData(avengers);
        result.setRecordsTotal(avengerRepository.count());

        return result;
    }

    private String getColumnName(int orderColumn) {
        switch (orderColumn) {
            case 0:
                return "name";
            case 1:
                return "real_name";
            case 2:
                return "snapped";
            default:
                return null;
        }
    }

}
