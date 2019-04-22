package org.assemble;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import org.assemble.model.Avenger;
import org.assemble.model.DataTable;

import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/avenger")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AvengersResource {

    @GET
    public List<Avenger> getAvengers() {
        return Avenger.listAll();
    }

    @POST
    @Transactional
    public Avenger addAvenger(JsonObject json) {
        Avenger avenger = new Avenger();

        avenger.name = json.getString("name");
        avenger.civilName = json.getString("civilName");
        avenger.snapped = json.getBoolean("snapped");

        avenger.persist();

        return avenger;
    }

    @GET
    @Path("/{id}")
    public Avenger findById(@PathParam("id") long id) {
        return Avenger.findById(id);
    }

    @GET
    @Path("/unsnapped")
    public List<Avenger> findUnsnapped() {
        return Avenger.findUnsnapped();
    }

    @GET
    @Path("/search")
    public List<Avenger> search(@QueryParam("searchValue") String searchValue) {
        return Avenger.search(searchValue);
    }

    @GET
    @Path("/ordered")
    public List<Avenger> getOrdered() {
        return Avenger.list("snapped", Sort.by("name"), false);
    }

    @GET
    @Path("/space")
    public List<Avenger> withSpace() {
        return Avenger.<Avenger>streamAll()
            .filter(a -> a.name.contains(" "))
            .limit(1)
            .collect(Collectors.toList());
    }

    @GET
    @Path("/datatable")
    @Produces(MediaType.APPLICATION_JSON)
    public DataTable datatable(
        @QueryParam("draw") int draw,
        @QueryParam("start") int start,
        @QueryParam("length") int length,
        @QueryParam("search[value]") String searchVal,
        @QueryParam("order[0][column]") int orderColumn,
        @QueryParam("order[0][dir]") String orderDirection) {

        DataTable result = new DataTable();
        result.setDraw(draw);

        String sortColumn = getColumnName(orderColumn);

        Sort.Direction direction = orderDirection.equals("asc") ? Sort.Direction.Ascending : Sort.Direction.Descending;
        Sort sort = sortColumn != null ? Sort.by(sortColumn, direction) : null;
        
        PanacheQuery<Avenger> filteredAvengers;

        if (searchVal != null && !searchVal.isEmpty()) {
            filteredAvengers = sort == null ? Avenger.searchQuery(searchVal) : Avenger.searchQuery(searchVal, sort);
        } else {
            filteredAvengers = sort == null ? Avenger.findAll() : Avenger.findAll(sort);
        }

        int pageNum = start / length;
        filteredAvengers.page(pageNum, length);
        
        result.setRecordsFiltered(filteredAvengers.count());
        result.setData(filteredAvengers.list());
        result.setRecordsTotal(Avenger.count());

        return result;
    }

    private String getColumnName(@QueryParam("order[0][column]") int orderColumn) {
        String sortColumn;
        switch (orderColumn) {
            case 0:
                sortColumn = "name";
                break;
            case 1:
                sortColumn = "real_name";
                break;
            case 2:
                sortColumn = "snapped";
                break;
            default:
                sortColumn = null;
        }
        return sortColumn;
    }
}
